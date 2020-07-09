package kalagato.TestBase;

import io.restassured.RestAssured;

public class TestBase {
	
	public static void baseUri(){
		
		RestAssured.baseURI= "http://13.233.183.137";

	}

}
