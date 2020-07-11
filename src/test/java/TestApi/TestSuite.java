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

public class TestSuite {
	Login Login;
	Logout Logout;
	CreateUser CreateUser;
	GetMasterFilter GetMasterFilter;
	RefreshToken RefreshToken;
	GetUserSegments GetUserSegments;
	GetUserSegment GetUserSegment;
	CreateUserSegment CreateUserSegment;
	ChangePasswordFromApp ChangePasswordFromApp;
	UpdateUserSegment UpdateUserSegment;
	DeleteUserSegment DeleteUserSegment;
	ForgetPasswordRequest ForgetPasswordRequest;
	ResetPasswordWithEmailLink ResetPasswordWithEmailLink;
	LogError LogError;
	ExcelUtility ExcelUtility= new ExcelUtility();
	static String Admin = "admin";
	static String User = "user";
	static String NonAdmin = "nonadmin";
	static String Flag = "Flag";
	static String fileName = System.getProperty("user.dir") + "\\src\\main\\java\\kalagato\\TestData\\TestData.xlsx"; 


	public TestSuite() {	
		super();
	}

	//public static String TESTDATA_SHEET_PATH="C:\\Users\\Akshunya Jugran\\eclipse-workspace\\KalagatoAPI\\src\\main\\java\\kalagato\\TestData\\TestData.xlsx";
	@DataProvider(name = "DataProviderforTrigger")
	public Iterator<Object[]> DataProviderforTrigger(Method m) throws IOException {
		String methodName = m.getName();
		XSSFSheet sheet = null;
		if(methodName.contains(NonAdmin)){
			sheet = ExcelUtility.ReadXSSFsheet(fileName ,NonAdmin);
		}else if(methodName.contains(Admin)){
			sheet = ExcelUtility.ReadXSSFsheet(fileName,Admin);	
		}else if(methodName.contains(User)){
			sheet = ExcelUtility.ReadXSSFsheet(fileName,User);
		} 

		int count = sheet.getPhysicalNumberOfRows();
		ArrayList<String> arr = new ArrayList<String>();
		XSSFRow row;
		String cellVal;
		int FlagColno = ExcelUtility.findCol(sheet,Flag);

		List<Object[]> dataToBeReturned = new ArrayList<Object[]>();

		for (int i = 1; i < count; i++) { 
			row = sheet.getRow(i);
			cellVal = row.getCell(FlagColno).getStringCellValue();
			if (cellVal.equalsIgnoreCase("Y")) {
				arr.add(ExcelUtility.getCellValueAsString(row.getCell(0)));
			}
		}
		for (String userData : arr) {
			dataToBeReturned.add(new Object[] { userData });
		}
		return dataToBeReturned.iterator();
	}


	@BeforeMethod
	public void setup() {
		TestBase.baseUri();
		Login=new Login();
		Logout=new Logout();
		CreateUser = new CreateUser();
		GetMasterFilter= new GetMasterFilter();
		RefreshToken = new RefreshToken();
		GetUserSegments = new GetUserSegments();
		GetUserSegment = new GetUserSegment();
		CreateUserSegment = new CreateUserSegment();
		ChangePasswordFromApp = new ChangePasswordFromApp();
		UpdateUserSegment = new UpdateUserSegment();
		DeleteUserSegment = new DeleteUserSegment();
		ForgetPasswordRequest = new ForgetPasswordRequest();
		ResetPasswordWithEmailLink = new ResetPasswordWithEmailLink();
		LogError = new LogError();

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
		RefreshToken.refreshToken(kalagato.userAPI.Login.refresh_Token);
		boolean flag= RefreshToken.refreshTokenResponse.isEmpty();
		Assert.assertFalse(flag, "refresh_Token is empty");
	} 


	//Verify get_master_filter api is working as expected 

	@Test(enabled=true,priority=3,dataProvider="DataProviderforTrigger")
	public void adminVerifyGetMasterFilter(String SNo) throws IOException {
		String accessToken=Login.login(SNo);
		GetMasterFilter.get_master_filter(accessToken);
		boolean flag= GetMasterFilter.getMasterFilterResponse.isEmpty();
		Assert.assertFalse(flag, "get_master_filter is empty");
	} 

	//Verify get_user_segments api is working as expected 

	@Test(enabled=true,priority=4,dataProvider="DataProviderforTrigger")
	public void adminVerifyGetUserSegments(String SNo) throws IOException {
		String accessToken=Login.login(SNo);
		GetUserSegments.get_user_segments(accessToken);
		boolean flag= GetUserSegments.getUserSegmentsResponse.isEmpty();
		Assert.assertFalse(flag, "get_user_segments is empty");
	} 

	//Verify get_user_segment api is working as expected 

	@Test(enabled=true,priority=5,dataProvider="DataProviderforTrigger")
	public void adminVerifyGetUserSegment(String SNo) throws IOException {
		String accessToken=Login.login(SNo);
		GetUserSegment.get_user_segment(accessToken);
		boolean flag= GetUserSegment.getUserSegmentResponse.isEmpty();
		Assert.assertFalse(flag, "get_user_segment is empty");
	} 


	//Verify create_user_segment api is working as expected 

	@Test(enabled=true,priority=6,dataProvider="DataProviderforTrigger")
	public void adminVerifyCreateUserSegment(String SNo) throws IOException {
		String accessToken=Login.login(SNo);
		CreateUserSegment.create_user_segment(accessToken);
	} 


	//Verify update_user_segment api is working as expected 

	@Test(enabled=true,priority=7,dataProvider="DataProviderforTrigger")
	public void adminVerifyUpdateUserSegment(String SNo) throws IOException {
		String accessToken=Login.login(SNo);
		UpdateUserSegment.update_user_segment(accessToken);
	} 

	//Verify delete_user_segment api is working as expected 

	@Test(enabled=true,priority=8,dataProvider="DataProviderforTrigger")
	public void adminVerifyDeleteUserSegment(String SNo) throws IOException {
		String accessToken=Login.login(SNo);
		CreateUserSegment.create_user_segment(accessToken);
		//DeleteUserSegment.delete_user_segment(accessToken);
	} 

	//Verify that password has changed using change_password_from_app api

	@Test(enabled=true,priority=9,dataProvider="DataProviderforTrigger")
	public void adminVerifyChangePasswordFromApp(String SNo) throws IOException {
		Login.login(SNo);
		ChangePasswordFromApp.change_password_from_app();
		Assert.assertEquals(kalagato.userAPI.ChangePasswordFromApp.changePasswordFromAppMessage, "Your password has been changed successfully!", "Message not correct");
	} 

	//Verify that forgot_password_request api is working

	@Test(enabled=true,priority=10,dataProvider="DataProviderforTrigger")
	public void adminVerifyForgetPasswordRequest(String SNo) throws IOException {
		Login.login(SNo);
		ForgetPasswordRequest.forget_password_request();
		Assert.assertEquals(kalagato.userAPI.ForgetPasswordRequest.forgetPasswordRequestMessage, "Reset password link has been sent to your email!", "Message not correct");
	}  

	//Verify that reset+password_with_email_link api is working

	@Test(enabled=false,priority=11,dataProvider="DataProviderforTrigger")
	public void adminVerifyResetPasswordWithEmailLink(String SNo) throws IOException {
		Login.login(SNo);
		ResetPasswordWithEmailLink.reset_password_with_email_link();

		Assert.assertEquals(kalagato.userAPI.ResetPasswordWithEmailLink.resetPasswordWithEmailLinkMessage, "New password was successfully updated.", "Message not correct");
	}  

	//verify create user api
	@Test(enabled=true,priority=12,dataProvider="DataProviderforTrigger")
	public void userVerifyCreateUser(String SNo) throws IOException {
		Response response = CreateUser.createUser(SNo, User);
		int statusCode = response.getStatusCode();	
		boolean flag= response.asString().isEmpty();
		Assert.assertTrue(flag, "get_master_filter is empty");
		Assert.assertEquals(statusCode, 201);
	}  
	//			

	//Verify User is not created by non-admin
	@Test(enabled=true,priority=12,dataProvider="DataProviderforTrigger")
	public void nonadminVerifyCreateUser(String SNo) throws IOException {
		Response response = CreateUser.createUser(SNo,NonAdmin);
		int statusCode = response.getStatusCode();		
		boolean flag= response.asString().isEmpty();
		Assert.assertFalse(flag, "get_master_filter is empty");
		Assert.assertEquals(statusCode, 401);
	}  

	//verify log_error api
	@Test(enabled=true,priority=13,dataProvider="DataProviderforTrigger")
	public void adminVerifyLogError(String SNo) throws IOException {
		String accessToken=Login.login(SNo);
		LogError.log_error(accessToken);
	}  	

	@AfterMethod
	public void UserLogout(){
		Logout=new Logout();
		Logout.logout();

	}


}
