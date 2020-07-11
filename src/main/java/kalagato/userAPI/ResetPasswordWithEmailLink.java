package kalagato.userAPI;

import static io.restassured.RestAssured.given;

import files.payload;
import io.restassured.path.json.JsonPath;
import kalagato.TestBase.TestBase;
import utility.HttpStatusCode;


public class ResetPasswordWithEmailLink {
	
	public String resetPasswordWithEmailLinkResponse;
	public String resetPasswordWithEmailLinkMessage;
		
		public void reset_password_with_email_link() {
			
			resetPasswordWithEmailLinkResponse=given().log().all().header("Content-Type","application/json")
					.body(payload.resetPasswordWithEmailBody())
					.when().post(TestBase.prop.getProperty("resetPasswordURI")).then()
					.assertThat().statusCode(HttpStatusCode.OK.getCode()).extract().response().asString();
			

			JsonPath js1= new JsonPath(resetPasswordWithEmailLinkResponse);  //for parsing jason
			resetPasswordWithEmailLinkMessage=js1.getString("message");
			
			
		}

}
