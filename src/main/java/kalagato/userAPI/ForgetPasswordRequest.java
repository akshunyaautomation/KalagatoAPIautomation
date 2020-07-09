package kalagato.userAPI;

import static io.restassured.RestAssured.given;

import files.payload;
import io.restassured.path.json.JsonPath;


public class ForgetPasswordRequest {
	
public String forgetPasswordRequestResponse;
public static String forgetPasswordRequestMessage;
	
	public void forget_password_request() {
		
		forgetPasswordRequestResponse=given().log().all().header("Content-Type","application/json")
				.body(payload.forgetPasswordRequestBody())
				.when().post("/api/v1/user/password/reset-request").then()
				.assertThat().statusCode(200).extract().response().asString();
		

		JsonPath js1= new JsonPath(forgetPasswordRequestResponse);  //for parsing jason
		forgetPasswordRequestMessage=js1.getString("message");
		
		
	}
}
