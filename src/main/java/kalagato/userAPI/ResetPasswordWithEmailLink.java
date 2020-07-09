package kalagato.userAPI;

import static io.restassured.RestAssured.given;

import files.payload;
import io.restassured.path.json.JsonPath;


public class ResetPasswordWithEmailLink {
	
	public String resetPasswordWithEmailLinkResponse;
	public static String resetPasswordWithEmailLinkMessage;
		
		public void reset_password_with_email_link() {
			
			resetPasswordWithEmailLinkResponse=given().log().all().header("Content-Type","application/json")
					.body(payload.resetPasswordWithEmailBody())
					.when().post("/api/v1/user/password/reset/dcebc19e-d0b9-4c83-b0f1-696cb2178fad").then()
					.assertThat().statusCode(200).extract().response().asString();
			

			JsonPath js1= new JsonPath(resetPasswordWithEmailLinkResponse);  //for parsing jason
			resetPasswordWithEmailLinkMessage=js1.getString("message");
			
			
		}

}
