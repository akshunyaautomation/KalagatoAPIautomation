package kalagato.user_segment;

import static io.restassured.RestAssured.*;

public class GetUserSegments {
	public String getUserSegmentsResponse;
	
	public void get_user_segments(String accessToken) {
		
		getUserSegmentsResponse=given().log().all().header("Authorization","bearer "+accessToken)
		.when().get("/api/v1/user-segments").then().assertThat().statusCode(200)
		.extract().response().asString();
		
		System.out.println(getUserSegmentsResponse);
	}

}
