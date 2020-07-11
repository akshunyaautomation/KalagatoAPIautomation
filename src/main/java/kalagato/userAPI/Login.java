package kalagato.userAPI;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import files.payload;
import io.restassured.path.json.JsonPath;
import kalagato.TestBase.TestBase;
import utility.ExcelUtility;
import utility.FieldValue;
import utility.HttpStatusCode;
import utility.Sheets;

public class Login {

	public static String access_Token;
	public static String refresh_Token;
	static String fileName = System.getProperty("user.dir") + TestBase.prop.getProperty("fileName"); 

	ExcelUtility ExcelUtility= new ExcelUtility();

	public String loginWithUserNamePassword(String username, String password) throws IOException {
		String loginResponse= given().log().all().header("Content-Type","application/json")
				.body(payload.loginBody(username, password)).when().post(TestBase.prop.getProperty("loginURI")).then()
				.assertThat().statusCode(HttpStatusCode.OK.getCode()).extract().response().asString();

		System.out.println(loginResponse);

		JsonPath js= new JsonPath(loginResponse);  //for parsing jason
		access_Token=js.getString("body.access_token");
		System.out.println("Access Token: "+access_Token);

		refresh_Token=js.getString("body.refresh_token");
		System.out.println("Refresh Token: "+ refresh_Token);

		return access_Token;
	}

	public String login(String SNo) throws IOException {
		return loginGenric(SNo, Sheets.ADMIN);
	}

	public String loginGenric(String SNo, Sheets userType) throws IOException {
		System.out.println("SnoUrinalysisTrigger: "+SNo);
		XSSFSheet sheet =  ExcelUtility.ReadXSSFsheet(fileName,userType.getSheetValue());
		int rowNo = ExcelUtility.findRow(sheet, SNo);
		int columns = sheet.getRow(0).getPhysicalNumberOfCells();
		XSSFRow rowIterator = sheet.getRow(0);
		XSSFRow row = sheet.getRow(rowNo);

		int columnIterator=1;
		String username = null;
		String password = null;

		do{

			String fieldValue=rowIterator.getCell(columnIterator).getStringCellValue();
			if(fieldValue.contains(FieldValue.USERNAME.getFieldValue())){
				username=row.getCell(columnIterator).getStringCellValue();			
			}	

			else if(fieldValue.contains(FieldValue.PASSWORD.getFieldValue())){
				password=row.getCell(columnIterator).getStringCellValue();
			}
			++columnIterator;
		}while((columns-1)>columnIterator);

		return loginWithUserNamePassword(username, password);


		//	
		//	public String login(String username, String password) {
		//		
		//		String loginResponse= given().log().all().header("Content-Type","application/json")
		//				.body(payload.loginBody(username, password)).when().post("/api/v1/login").then()
		//				.assertThat().statusCode(200).extract().response().asString();
		//				
		//				System.out.println(loginResponse);
		//				
		//				JsonPath js= new JsonPath(loginResponse);  //for parsing jason
		//				access_Token=js.getString("body.access_token");
		//				System.out.println("Access Token: "+access_Token);
		//				
		//				refresh_Token=js.getString("body.refresh_token");
		//				System.out.println("Refresh Token: "+ refresh_Token);
		//				
		//			return access_Token;
		//		
		//		
	}
}
