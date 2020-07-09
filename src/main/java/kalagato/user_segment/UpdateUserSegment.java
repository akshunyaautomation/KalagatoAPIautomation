package kalagato.user_segment;

import static io.restassured.RestAssured.*;

import files.payload;

public class UpdateUserSegment {
	
public String updateUserSegmentResponse;
	
	public void update_user_segment(String accessToken) {
		
		updateUserSegmentResponse=given().log().all().header("Authorization","bearer "+accessToken).header("Content-Type","application/json")
				.body(payload.updateUserSegmentBody())
				.when().put("/api/v1/user-segments/13").then()
				.assertThat().statusCode(204).extract().response().asString();

}
}