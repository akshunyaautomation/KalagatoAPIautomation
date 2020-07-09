package kalagato.user_segment;

import static io.restassured.RestAssured.*;

public class DeleteUserSegment {
	
public String deleteUserSegmentResponse;
	
	public void delete_user_segment(String accessToken) {
		
		deleteUserSegmentResponse=given().log().all().header("Authorization","bearer "+accessToken).header("Content-Type","application/json")
				.when().delete("/api/v1/user-segments/21").then()
				.assertThat().statusCode(202).extract().response().asString();


}
}