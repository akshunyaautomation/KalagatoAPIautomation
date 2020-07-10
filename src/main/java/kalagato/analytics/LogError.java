package kalagato.analytics;

import static io.restassured.RestAssured.given;

import files.payload;

public class LogError {
	public String logErrorResponse;
	
public void log_error(String accessToken) {
		
		logErrorResponse=given().log().all().header("Content-Type","application/json").header("Authorization","bearer "+kalagato.userAPI.Login.access_Token)
				.body(payload.logErrorBody()).when().post("/api/v1/analytics").then()
				.assertThat().statusCode(204).extract().response().asString();
		
	}


}
