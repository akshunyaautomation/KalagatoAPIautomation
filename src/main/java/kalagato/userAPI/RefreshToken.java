package kalagato.userAPI;
import static io.restassured.RestAssured.*;

import files.payload;
import io.restassured.path.json.JsonPath;

public class RefreshToken {
	
	Login Login=new Login();
	
	public String refreshTokenResponse;
	
	public String refreshToken(String refresh_Token) {
		//String username, String password
		//Login.login(username, password);
		
		refreshTokenResponse=given().log().all().headers("Content-Type","application/json").body(payload.refreshTokenBody(kalagato.userAPI.Login.refresh_Token))
		.when().post("/api/v1/login").then().assertThat().statusCode(200).extract().response().asString();
		
		System.out.print(refreshTokenResponse);
		
//		JsonPath js= new JsonPath(refreshTokenResponse);
//		access_Token=js.getString("body.access_token");
//		System.out.println("Access Token: "+access_Token);
//		
//		refresh_Token=js.getString("body.refresh_token");
//		System.out.println("Refresh Token: "+ refresh_Token);
		
		
		
		return refreshTokenResponse;
		
	}

}
