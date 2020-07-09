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

import kalagato.TestBase.TestBase;
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

public class MyTest {
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
	ExcelUtility ExcelUtility= new ExcelUtility();


	public MyTest() {	
		super();
	}
	
	//public static String TESTDATA_SHEET_PATH="C:\\Users\\Akshunya Jugran\\eclipse-workspace\\KalagatoAPI\\src\\main\\java\\kalagato\\TestData\\TestData.xlsx";
	@DataProvider(name = "DataProviderforTrigger")
	public Iterator<Object[]> DataProviderforTrigger(Method m) throws IOException {
		String methodName = m.getName();
		XSSFSheet sheet = null;
		if(methodName.contains("admin")){
			sheet = ExcelUtility.ReadXSSFsheet("C:\\Users\\Akshunya Jugran\\eclipse-workspace"
					+ "\\KalagatoAPI\\src\\main\\java\\kalagato\\TestData\\TestData.xlsx","admin");
		
		}else {
			sheet = 	ExcelUtility.ReadXSSFsheet("C:\\Users\\Akshunya Jugran\\eclipse-workspace"
					+ "\\KalagatoAPI\\src\\main\\java\\kalagato\\TestData\\TestData.xlsx","user");
		}
		
		int count = sheet.getPhysicalNumberOfRows();
		ArrayList<String> arr = new ArrayList<String>();
		XSSFRow row;
		String cellVal;
		int FlagColno = ExcelUtility.findCol(sheet,"Flag");

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
	
//	@DataProvider(name="loginAsAdmin")
//	public Object [][] getAdminLoginData() {
//		Object data[][]=ExcelUtility.getTestData("admin");
//		return data;
//		
//	}

	
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

	}
	
	 //Verify login & Logout is working as expected
	@Test(enabled=true,priority=1,dataProvider="DataProviderforTrigger")
	public void adminUserLogin(String username, String password) {
		Login.login(username,password);
		boolean flag= kalagato.userAPI.Login.refresh_Token.isEmpty();
		Assert.assertFalse(flag, "refresh_Token is empty");
		Logout.logout();
	}
	
	//Verify refresh_token api is working as expected
	@Test(enabled=true,priority=2,dataProvider="DataProviderforTrigger")
	public void adminverifyRefreshToken(String username, String password) {
		Login.login(username,password);
		RefreshToken.refreshToken(kalagato.userAPI.Login.refresh_Token);
		boolean flag= RefreshToken.refreshTokenResponse.isEmpty();
		Assert.assertFalse(flag, "refresh_Token is empty");
		  //GetMasterFilter.get_master_filter(accessToken);
		  
		//  CreateUser.createUser(firstname, lastname, email, newpassword);
			
			Logout.logout();
		
	} 
	
	
	//Verify get_master_filter api is working as expected 
	
	@Test(enabled=false,priority=3,dataProvider="loginAsAdmin")
	public void verifyGetMasterFilter(String username, String password) {
		String accessToken=Login.login(username,password);
		GetMasterFilter.get_master_filter(accessToken);
		boolean flag= GetMasterFilter.getMasterFilterResponse.isEmpty();
		Assert.assertFalse(flag, "get_master_filter is empty");
		
			Logout.logout();
		
	} 
	
	//Verify get_user_segments api is working as expected 
	
		@Test(enabled=false,priority=4,dataProvider="loginAsAdmin")
		public void verifyGetUserSegments(String username, String password) {
			String accessToken=Login.login(username,password);
			GetUserSegments.get_user_segments(accessToken);
			boolean flag= GetUserSegments.getUserSegmentsResponse.isEmpty();
			Assert.assertFalse(flag, "get_user_segments is empty");
			
				Logout.logout();
			
		} 
		
		//Verify get_user_segment api is working as expected 
		
			@Test(enabled=false,priority=5,dataProvider="loginAsAdmin")
			public void verifyGetUserSegment(String username, String password) {
				String accessToken=Login.login(username,password);
				GetUserSegment.get_user_segment(accessToken);
				boolean flag= GetUserSegment.getUserSegmentResponse.isEmpty();
				Assert.assertFalse(flag, "get_user_segment is empty");
				
					Logout.logout();
			} 
			
			
			//Verify create_user_segment api is working as expected 
			
			@Test(enabled=false,priority=6,dataProvider="loginAsAdmin")
			public void verifyCreateUserSegment(String username, String password) {
				String accessToken=Login.login(username,password);
				CreateUserSegment.create_user_segment(accessToken);
					Logout.logout();
			} 
			

			//Verify update_user_segment api is working as expected 
			
			@Test(enabled=false,priority=7,dataProvider="loginAsAdmin")
			public void verifyUpdateUserSegment(String username, String password) {
				String accessToken=Login.login(username,password);
				UpdateUserSegment.update_user_segment(accessToken);
					Logout.logout();
			} 
			
//Verify delete_user_segment api is working as expected 
			
			@Test(enabled=false,priority=8,dataProvider="loginAsAdmin")
			public void verifyDeleteUserSegment(String username, String password) {
				String accessToken=Login.login(username,password);
				CreateUserSegment.create_user_segment(accessToken);
				DeleteUserSegment.delete_user_segment(accessToken);
					Logout.logout();
			} 
			
			//Verify that password has changed using change_password_from_app api
			
			@Test(enabled=false,priority=9,dataProvider="loginAsAdmin")
			public void verifyChangePasswordFromApp(String username, String password) {
				Login.login(username,password);
				ChangePasswordFromApp.change_password_from_app();
				Assert.assertEquals(kalagato.userAPI.ChangePasswordFromApp.changePasswordFromAppMessage, "Your password has been changed successfully!", "Message not correct");
					Logout.logout();
			} 
			
//Verify that forgot_password_request api is working
			
			@Test(enabled=false,priority=10,dataProvider="loginAsAdmin")
			public void verifyForgetPasswordRequest(String username, String password) {
				Login.login(username,password);
				ForgetPasswordRequest.forget_password_request();
				Assert.assertEquals(kalagato.userAPI.ForgetPasswordRequest.forgetPasswordRequestMessage, "Reset password link has been sent to your email!", "Message not correct");
					Logout.logout();
			}  
			
//Verify that reset+password_with_email_link api is working
			
			@Test(enabled=false,priority=11,dataProvider="loginAsAdmin")
			public void verifyResetPasswordWithEmailLink(String username, String password) {
				Login.login(username,password);
				ResetPasswordWithEmailLink.reset_password_with_email_link();
				
				Assert.assertEquals(kalagato.userAPI.ResetPasswordWithEmailLink.resetPasswordWithEmailLinkMessage, "Reset password link has been sent to your email!", "Message not correct");
					Logout.logout();
			}  
			
			
		
	
	
//	@AfterMethod
//	public void UserLogout(){
//			Logout=new Logout();
//			Logout.logout();
//			
//	}
	
}
