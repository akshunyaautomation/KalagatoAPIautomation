package kalagato.userAPI;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import files.payload;
import io.restassured.response.Response;
import kalagato.TestBase.TestBase;
import utility.ExcelUtility;
import utility.FieldValue;
import utility.Sheets;

public class CreateUser {

	static String fileName = System.getProperty("user.dir") + TestBase.prop.getProperty("fileName");
	ExcelUtility excelUtility= new ExcelUtility();
	Login Login = new Login();

	public void createUser(String SNo) throws IOException {
		createUser(SNo, Sheets.ADMIN);
	}

	public Response createUser(String SNo, Sheets UserType) throws IOException {
		System.out.println("SnoUrinalysisTrigger: "+SNo);
		XSSFSheet sheet =  excelUtility.ReadXSSFsheet(fileName, UserType.getSheetValue());
		int rowNo = excelUtility.findRow(sheet, SNo);
		int columns = sheet.getRow(0).getPhysicalNumberOfCells();
		XSSFRow rowIterator = sheet.getRow(0);
		XSSFRow row = sheet.getRow(rowNo);
		
		int columnIterator=1;
		String firstName = null;
		String lastName = null;
		String email = null;
		String newPassword = null;
		String username = null;
		String password = null;



		do{
			String fieldValue=rowIterator.getCell(columnIterator).getStringCellValue();
			if(fieldValue.contains(FieldValue.FIRSTNAME.getFieldValue())){
				firstName=row.getCell(columnIterator).getStringCellValue();				
			}	
			else if(fieldValue.contains(FieldValue.LASTNAME.getFieldValue())){
				lastName=row.getCell(columnIterator).getStringCellValue();				
			}
			else if(fieldValue.contains(FieldValue.EMAIL.getFieldValue())){
				email=row.getCell(columnIterator).getStringCellValue();				
			}
			else if(fieldValue.contains(FieldValue.PASSWORD.getFieldValue())){
				newPassword=row.getCell(columnIterator).getStringCellValue();				
			}else if(fieldValue.contains(FieldValue.USERNAME.getFieldValue())){
				username=row.getCell(columnIterator).getStringCellValue();				
			}
			else if(fieldValue.contains(FieldValue.PASSWORD.getFieldValue())){
				password=row.getCell(columnIterator).getStringCellValue();			
			}
			++columnIterator;
		}while((columns-1)>columnIterator);

		Login.loginWithUserNamePassword(username, password);

		return given().log().all().header("Content-Type","application/json").header("Authorization","bearer "+kalagato.userAPI.Login.access_Token)
				.body(payload.createUserBody(firstName, lastName, email, newPassword))
				.when().post(TestBase.prop.getProperty("createUserURI"));

		//.then().assertThat().statusCode(401).extract().response().asString();

	}
}
