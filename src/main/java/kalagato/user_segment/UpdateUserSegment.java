package kalagato.user_segment;

import static io.restassured.RestAssured.*;

import files.payload;
import kalagato.TestBase.TestBase;
import utility.HttpStatusCode;

public class UpdateUserSegment {
	
public String updateUserSegmentResponse;
	
	public void update_user_segment(String accessToken) {
		
		updateUserSegmentResponse=given().log().all().header("Authorization","bearer "+accessToken).header("Content-Type","application/json")
				.body(payload.updateUserSegmentBody())
				.when().put(TestBase.prop.getProperty("updateUserSegmentURI")+GetUserSegments.getUserSegmentsID)
				.then().assertThat().statusCode(HttpStatusCode.NO_CONTENT.getCode())
				.extract().response().asString();

}
}