 package kalagato.userAPI;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import files.payload;
import io.restassured.path.json.JsonPath;
import utility.ExcelUtility;

public class CreateUser {
	
	public String createUserResponse;
	public String createUserMessage;
	ExcelUtility ExcelUtility= new ExcelUtility();
	Login Login = new Login();
	
	public void createUser(String SNo) throws IOException {
		System.out.println("SnoUrinalysisTrigger: "+SNo);
		XSSFSheet sheet =  ExcelUtility.ReadXSSFsheet(System.getProperty("user.dir") + "\\src\\main\\java\\kalagato\\TestData\\TestData.xlsx","user");
		int rowNo = ExcelUtility.findRow(sheet, SNo);
		int columns;
		XSSFRow rowIterator;
		XSSFRow row;
		boolean dateField=true;
		columns = sheet.getRow(0).getPhysicalNumberOfCells();
		rowIterator = sheet.getRow(0);
		row = sheet.getRow(rowNo);
		int columnIterator=1;
		String firstname = null;
		String lastname = null;
		String email = null;
		String newpassword = null;
	
	
		
		do{
			String FieldValue=rowIterator.getCell(columnIterator).getStringCellValue();
			if(FieldValue.contains("firstname")){
				firstname=row.getCell(columnIterator).getStringCellValue();
				++columnIterator;
			}	

			else if(FieldValue.contains("lastname")){
				lastname=row.getCell(columnIterator).getStringCellValue();
				++columnIterator;
			}
			else if(FieldValue.contains("email")){
				email=row.getCell(columnIterator).getStringCellValue();
				++columnIterator;
			}
			else if(FieldValue.contains("newpassword")){
				newpassword=row.getCell(columnIterator).getStringCellValue();
				++columnIterator;
			}else{
				++columnIterator;
			}
		}while((columns-1)>columnIterator);
		
		Login.login(SNo);
	
		 createUserResponse=given().log().all().header("Content-Type","application/json").header("Authorization","bearer "+kalagato.userAPI.Login.access_Token)
		.body(payload.createUserBody(firstname, lastname, email, newpassword))
		.when().post("/api/v1/users").then().assertThat().statusCode(201).extract().response().asString();
		
		
	}
}
