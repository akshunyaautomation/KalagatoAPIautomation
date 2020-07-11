package kalagato.userAPI;

import static io.restassured.RestAssured.given;

import files.payload;
import io.restassured.path.json.JsonPath;
import kalagato.TestBase.TestBase;
import utility.HttpStatusCode;

public class ChangePasswordFromApp {
	
	public String changePasswordFromAppMessage;
	public String changePasswordFromAppResponse;
	
	public void change_password_from_app() {
		
		changePasswordFromAppResponse=given().log().all().header("Content-Type","application/json")
				.body(payload.changePasswordFromAppBody()).when().post(TestBase.prop.getProperty("changePasswordURI")).then()
				.assertThat().statusCode(HttpStatusCode.OK.getCode()).extract().response().asString();
		
		JsonPath js1= new JsonPath(changePasswordFromAppResponse);  //for parsing jason
		changePasswordFromAppMessage=js1.getString("message");
		
	}

}
