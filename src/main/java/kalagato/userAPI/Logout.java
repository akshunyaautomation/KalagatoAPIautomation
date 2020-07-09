package kalagato.userAPI;

import static io.restassured.RestAssured.given;

import files.payload;

public class Logout {
	Login Login= new Login();
	public String logoutResponse;
	
public void logout() {
		//String username, String password
	//Login.login(username, password);
		
		logoutResponse=given().log().all().header("Content-Type","application/json").body(payload.logoutBody(Login.refresh_Token))
				.when().post("/api/v1/logout").then().assertThat().statusCode(204).extract().response().asString();

				
		
	}
	

}
