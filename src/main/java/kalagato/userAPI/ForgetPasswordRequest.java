package kalagato.userAPI;

import static io.restassured.RestAssured.given;

import files.payload;
import io.restassured.path.json.JsonPath;
import kalagato.TestBase.TestBase;
import utility.HttpStatusCode;


public class ForgetPasswordRequest {
	
public String forgetPasswordRequestResponse;
public String forgetPasswordRequestMessage;
	
	public void forget_password_request() {
		
		forgetPasswordRequestResponse=given().log().all().header("Content-Type","application/json")
				.body(payload.forgetPasswordRequestBody())
				.when().post(TestBase.prop.getProperty("forgotPasswordURI")).then()
				.assertThat().statusCode(HttpStatusCode.OK.getCode()).extract().response().asString();
		

		JsonPath js1= new JsonPath(forgetPasswordRequestResponse);  //for parsing jason
		forgetPasswordRequestMessage=js1.getString("message");
		
		
	}
}
