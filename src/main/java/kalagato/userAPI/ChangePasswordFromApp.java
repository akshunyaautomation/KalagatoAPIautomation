package kalagato.userAPI;

import static io.restassured.RestAssured.given;

import files.payload;
import io.restassured.path.json.JsonPath;

public class ChangePasswordFromApp {
	
	public static String changePasswordFromAppMessage;
	public String changePasswordFromAppResponse;
	
	public void change_password_from_app() {
		
		changePasswordFromAppResponse=given().log().all().header("Content-Type","application/json")
				.body(payload.changePasswordFromAppBody()).when().post("/api/v1/user/password/change").then()
				.assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js1= new JsonPath(changePasswordFromAppResponse);  //for parsing jason
		changePasswordFromAppMessage=js1.getString("message");
		
	}

}
