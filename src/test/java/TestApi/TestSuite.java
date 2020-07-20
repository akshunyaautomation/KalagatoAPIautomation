package TestApi;


import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import kalagato.TestBase.TestBase;
import kalagato.analytics.LogError;
import kalagato.master_filter.GetMasterFilter;
import kalagato.userAPI.ChangePasswordFromApp;
import kalagato.userAPI.CreateUser;
import kalagato.userAPI.ForgetPasswordRequest;
import kalagato.userAPI.Login;
import kalagato.userAPI.Logout;
import kalagato.userAPI.RefreshToken;
import kalagato.userAPI.ResetPasswordWithEmailLink;
import kalagato.user_segment.CreateUserSegment;
import kalagato.user_segment.DeleteUserSegment;
import kalagato.user_segment.GetUserSegment;
import kalagato.user_segment.GetUserSegments;
import kalagato.user_segment.UpdateUserSegment;
import utility.ExcelUtility;
import utility.HttpStatusCode;
import utility.Sheets;

public class TestSuite {
	Login Login;
	Logout Logout;

	TestSuite(){
		TestBase.baseUri();
	}

	//public static String TESTDATA_SHEET_PATH="C:\\Users\\Akshunya Jugran\\eclipse-workspace\\KalagatoAPI\\src\\main\\java\\kalagato\\TestData\\TestData.xlsx";
	@DataProvider(name = "DataProviderforTrigger")
	public Iterator<Object[]> DataProviderforTrigger(Method m) throws IOException {			
		ExcelUtility excelUtility= new ExcelUtility();
		XSSFSheet sheet = getSheet(m.getName(), excelUtility); 
		XSSFRow row;
		String cellVal;
		int flagColno = excelUtility.findCol(sheet,TestBase.prop.getProperty("flag"));

		List<Object[]> dataToBeReturned = new ArrayList<Object[]>();
		for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) { 
			row = sheet.getRow(i);
			cellVal = row.getCell(flagColno).getStringCellValue();
			if (cellVal.equalsIgnoreCase("Y")) {
				dataToBeReturned.add( new Object[] { excelUtility.getCellValueAsString(row.getCell(0)) } );
			}
		}

		return dataToBeReturned.iterator();
	}


	private XSSFSheet getSheet(String methodName, ExcelUtility excelUtility) throws IOException {
		String fileName = System.getProperty("user.dir") + TestBase.prop.getProperty("fileName");
		XSSFSheet sheet = null;
		if(methodName.contains(Sheets.NONADMIN.getSheetValue())){
			sheet = excelUtility.ReadXSSFsheet(fileName ,Sheets.NONADMIN.getSheetValue());
		}else if(methodName.contains(Sheets.ADMIN.getSheetValue()) && 
				!methodName.contains(Sheets.NONADMIN.getSheetValue())){
			sheet = excelUtility.ReadXSSFsheet(fileName,Sheets.ADMIN.getSheetValue());	
		}else if(methodName.contains(Sheets.USER.getSheetValue())){
			sheet = excelUtility.ReadXSSFsheet(fileName,Sheets.USER.getSheetValue());
		}
		return sheet;
	}


	@BeforeMethod
	public void setup() {
		Login=new Login();		
	}

	@AfterMethod
	public void UserLogout(){
		Logout=new Logout();
		Logout.logout();
	}

		//Verify login & Logout is working as expected
		@Test(enabled=true,priority=1,dataProvider="DataProviderforTrigger")
		public void adminUserLogin(String SNo) throws IOException {
			Login.login(SNo);
			boolean flag= kalagato.userAPI.Login.refresh_Token.isEmpty();
			Assert.assertFalse(flag, "refresh_Token is empty");
		}
	
		//Verify refresh_token api is working as expected
		@Test(enabled=true,priority=2,dataProvider="DataProviderforTrigger")
		public void adminVerifyRefreshToken(String SNo) throws IOException {
			Login.login(SNo);
			RefreshToken refreshToken = new RefreshToken();
			refreshToken.refreshToken(kalagato.userAPI.Login.refresh_Token);
			boolean flag= refreshToken.refreshTokenResponse.isEmpty();
			Assert.assertFalse(flag, "refresh_Token is empty");
		} 
	
	
		//Verify get_master_filter api is working as expected 
	
		@Test(enabled=true,priority=3,dataProvider="DataProviderforTrigger")
		public void adminVerifyGetMasterFilter(String SNo) throws IOException {
			String accessToken=Login.login(SNo);
			GetMasterFilter getMasterFilter= new GetMasterFilter();
			getMasterFilter.get_master_filter(accessToken);
			boolean flag= getMasterFilter.getMasterFilterResponse.isEmpty();
			Assert.assertFalse(flag, "get_master_filter is empty");
		} 
	
	
		//Verify create_user_segment api is working as expected 
	
		@Test(enabled=true,priority=4,dataProvider="DataProviderforTrigger")
		public void adminVerifyCreateUserSegment(String SNo) throws IOException, InterruptedException {
			String accessToken=Login.login(SNo);
			CreateUserSegment createUserSegment= new CreateUserSegment();
			createUserSegment.create_user_segment(accessToken);
		} 
	

		//Verify get_user_segments api is working as expected 
	
		@Test(enabled=true,priority=5,dataProvider="DataProviderforTrigger")
		public void adminVerifyGetUserSegments(String SNo) throws IOException, InterruptedException {
			String accessToken=Login.login(SNo);
			CreateUserSegment createUserSegment= new CreateUserSegment();
			createUserSegment.create_user_segment(accessToken);
			GetUserSegments getUserSegments = new GetUserSegments();
			getUserSegments.get_user_segments(accessToken);
			Assert.assertEquals(GetUserSegments.statusCode, HttpStatusCode.OK.getCode());
			Assert.assertEquals(GetUserSegments.getUserSegmentsName, "Create By automation new", "Error: Mismatch");
	
		} 
	
	
		//Verify get_user_segment api is working as expected 
	
		@Test(enabled=true,priority=6,dataProvider="DataProviderforTrigger")
		public void adminVerifyGetUserSegment(String SNo) throws IOException, InterruptedException {
			String accessToken=Login.login(SNo);
			GetUserSegment getUserSegment= new GetUserSegment();
			getUserSegment.get_user_segment(accessToken);
			Assert.assertEquals(GetUserSegment.statusCode, HttpStatusCode.OK.getCode());
			Assert.assertEquals(GetUserSegment.getUserSegmentName,GetUserSegments.getUserSegmentsName, "Error: Mismatch");
		} 


	//Verify update_user_segment api is working as expected 

	@Test(enabled=true,priority=7,dataProvider="DataProviderforTrigger")
	public void adminVerifyUpdateUserSegment(String SNo) throws IOException, InterruptedException {
		String accessToken=Login.login(SNo);
		GetUserSegments getUserSegments = new GetUserSegments();
		getUserSegments.get_user_segments(accessToken);
		UpdateUserSegment updateUserSegment= new UpdateUserSegment();
		updateUserSegment.update_user_segment(accessToken);
		GetUserSegment getUserSegment= new GetUserSegment();
		getUserSegment.get_user_segment(accessToken);
		Assert.assertEquals(GetUserSegment.statusCode, HttpStatusCode.OK.getCode());
		Assert.assertEquals(GetUserSegment.getUserSegmentName,"Update from automation new", "Error: Mismatch");

	} 

	
		//Verify delete_user_segment api is working as expected 
	
		@Test(enabled=true,priority=8,dataProvider="DataProviderforTrigger")
		public void adminVerifyDeleteUserSegment(String SNo) throws IOException, InterruptedException {
			String accessToken=Login.login(SNo);
			GetUserSegments getUserSegments = new GetUserSegments();
			getUserSegments.get_user_segments(accessToken);
			DeleteUserSegment deleteUserSegment = new DeleteUserSegment();
			deleteUserSegment.delete_user_segment(accessToken);
		} 
	
		//Verify that password has changed using change_password_from_app api
	
		@Test(enabled=true,priority=9,dataProvider="DataProviderforTrigger")
		public void adminVerifyChangePasswordFromApp(String SNo) throws IOException {
			Login.login(SNo);
			ChangePasswordFromApp changePasswordFromApp= new ChangePasswordFromApp();
			changePasswordFromApp.change_password_from_app();
			Assert.assertEquals(changePasswordFromApp.changePasswordFromAppMessage, "Your password has been changed successfully!", "Message not correct");
		} 
	
		//Verify that forgot_password_request api is working
	
		@Test(enabled=true,priority=10,dataProvider="DataProviderforTrigger")
		public void adminVerifyForgetPasswordRequest(String SNo) throws IOException {
			Login.login(SNo);
			ForgetPasswordRequest forgetPasswordRequest= new ForgetPasswordRequest();
			forgetPasswordRequest.forget_password_request();
			Assert.assertEquals(forgetPasswordRequest.forgetPasswordRequestMessage, "Reset password link has been sent to your email!", "Message not correct");
		}  
	
		//Verify that resetpassword_with_email_link api is working
	
		@Test(enabled=false,priority=11,dataProvider="DataProviderforTrigger")
		public void adminVerifyResetPasswordWithEmailLink(String SNo) throws IOException {
			Login.login(SNo);
			ResetPasswordWithEmailLink resetPasswordWithEmailLink= new ResetPasswordWithEmailLink();
			resetPasswordWithEmailLink.reset_password_with_email_link();
			Assert.assertEquals(resetPasswordWithEmailLink.resetPasswordWithEmailLinkMessage, "New password was successfully updated.", "Message not correct");
		}  
	
		//verify create user api
		@Test(enabled=true,priority=12,dataProvider="DataProviderforTrigger")
		public void userVerifyCreateUser(String SNo) throws IOException {
			CreateUser _createUser = new CreateUser();
			Response response = _createUser.createUser(SNo, Sheets.USER);
			int statusCode = response.getStatusCode();	
			boolean flag= response.asString().isEmpty();
			Assert.assertTrue(flag, "createUser is empty");
			Assert.assertEquals(statusCode, HttpStatusCode.CREATED.getCode());
		}  
			
	
		//Verify User is not created by non-admin
		@Test(enabled=true,priority=13,dataProvider="DataProviderforTrigger")
		public void nonadminVerifyCreateUser(String SNo) throws IOException {
			CreateUser _createUser = new CreateUser();
			Response response = _createUser.createUser(SNo,Sheets.NONADMIN);
			int statusCode = response.getStatusCode();		
			boolean flag= response.asString().isEmpty();
			Assert.assertFalse(flag, "createUser is empty");
			Assert.assertEquals(statusCode, HttpStatusCode.FORBIDDEN.getCode());
		}  
	
		//verify log_error api
		@Test(enabled=true,priority=14,dataProvider="DataProviderforTrigger")
		public void adminVerifyLogError(String SNo) throws IOException {
			String accessToken=Login.login(SNo);
			LogError logError = new LogError();
			logError.log_error(accessToken);
		}  	

}
