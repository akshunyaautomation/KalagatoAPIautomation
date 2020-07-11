package kalagato.userAPI;

import static io.restassured.RestAssured.given;

import files.payload;
import kalagato.TestBase.TestBase;
import utility.HttpStatusCode;

public class Logout {
	Login Login= new Login();
	public String logoutResponse;

	public void logout() {
		logoutResponse = given().log().all().header("Content-Type","application/json").body(payload.logoutBody(Login.refresh_Token))
				.when().post(TestBase.prop.getProperty("logoutURI")).then().assertThat().statusCode(HttpStatusCode.NO_CONTENT.getCode()).extract().response().asString();		
	}


}
