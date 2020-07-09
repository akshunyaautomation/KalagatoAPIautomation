package kalagato.user_segment;

import static io.restassured.RestAssured.*;

import files.payload;

public class CreateUserSegment {
	
	public String createUserSegmentResponse;
	
	public void create_user_segment(String accessToken) {
		
		createUserSegmentResponse=given().log().all().header("Authorization","bearer "+accessToken).header("Content-Type","application/json")
				.body(payload.createUserSegmentBody())
				.when().post("/api/v1/user-segments").then()
				.assertThat().statusCode(201).extract().response().asString();

		
	}

}
