package kalagato.userAPI;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import files.payload;
import io.restassured.response.Response;
import utility.ExcelUtility;
import utility.Sheets;

public class CreateUser {

	static String fileName = System.getProperty("user.dir") + "\\src\\main\\java\\kalagato\\TestData\\TestData.xlsx";
	ExcelUtility ExcelUtility= new ExcelUtility();
	Login Login = new Login();

	public void createUser(String SNo) throws IOException {
		createUser(SNo, Sheets.ADMIN);
	}

	public Response createUser(String SNo, Sheets UserType) throws IOException {
		System.out.println("SnoUrinalysisTrigger: "+SNo);
		XSSFSheet sheet =  ExcelUtility.ReadXSSFsheet(fileName, UserType.getSheetValue());
		int rowNo = ExcelUtility.findRow(sheet, SNo);
		int columns;
		XSSFRow rowIterator;
		XSSFRow row;
		columns = sheet.getRow(0).getPhysicalNumberOfCells();
		rowIterator = sheet.getRow(0);
		row = sheet.getRow(rowNo);
		int columnIterator=1;
		String firstname = null;
		String lastname = null;
		String email = null;
		String newpassword = null;
		String username = null;
		String password = null;



		do{
			String FieldValue=rowIterator.getCell(columnIterator).getStringCellValue();
			if(FieldValue.contains("firstname")){
				firstname=row.getCell(columnIterator).getStringCellValue();				
			}	
			else if(FieldValue.contains("lastname")){
				lastname=row.getCell(columnIterator).getStringCellValue();				
			}
			else if(FieldValue.contains("email")){
				email=row.getCell(columnIterator).getStringCellValue();				
			}
			else if(FieldValue.contains("newpassword")){
				newpassword=row.getCell(columnIterator).getStringCellValue();				
			}else if(FieldValue.contains("username")){
				username=row.getCell(columnIterator).getStringCellValue();				
			}
			else if(FieldValue.contains("password")){
				password=row.getCell(columnIterator).getStringCellValue();			
			}
			++columnIterator;
		}while((columns-1)>columnIterator);

		Login.loginWithUserNamePassword(username, password);

		return given().log().all().header("Content-Type","application/json").header("Authorization","bearer "+kalagato.userAPI.Login.access_Token)
				.body(payload.createUserBody(firstname, lastname, email, newpassword))
				.when().post("/api/v1/users");

		//.then().assertThat().statusCode(401).extract().response().asString();

	}
}
