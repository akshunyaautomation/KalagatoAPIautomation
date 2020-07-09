package kalagato.userAPI;

import static io.restassured.RestAssured.given;

import files.payload;
import io.restassured.path.json.JsonPath;

public class Login {
	
	public static String access_Token;
	public static String refresh_Token;
	
	public String login(String username, String password) {
		
		String loginResponse= given().log().all().header("Content-Type","application/json")
				.body(payload.loginBody(username, password)).when().post("/api/v1/login").then()
				.assertThat().statusCode(200).extract().response().asString();
				
				System.out.println(loginResponse);
				
				JsonPath js= new JsonPath(loginResponse);  //for parsing jason
				access_Token=js.getString("body.access_token");
				System.out.println("Access Token: "+access_Token);
				
				refresh_Token=js.getString("body.refresh_token");
				System.out.println("Refresh Token: "+ refresh_Token);
				
			return access_Token;
		
		
	}
}
