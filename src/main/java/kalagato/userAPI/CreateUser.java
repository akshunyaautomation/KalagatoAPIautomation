 package kalagato.userAPI;

import static io.restassured.RestAssured.given;

import files.payload;
import io.restassured.path.json.JsonPath;

public class CreateUser {
	
	Login Login= new Login();
	
	public void createUser(String firstname, String lastname, String email,String newpassword) {
		//String username, String password
		//Login.login(username, password);
		
		String createUserResponse=given().log().all().header("Authorization","bearer "+kalagato.userAPI.Login.access_Token)
		.body(payload.createUserBody(firstname, lastname, email, newpassword))
		.when().post("/api/v1/users").then().assertThat().statusCode(400).extract().response().asString();
		
		System.out.println(createUserResponse);
		
		JsonPath js1= new JsonPath(createUserResponse);
		String createUserMessage=js1.getString("message");
		System.out.println("Create User API Response Message: "+createUserMessage);
		
	}
}
