package kalagato.user_segment;

import static io.restassured.RestAssured.*;

import kalagato.TestBase.TestBase;
import utility.HttpStatusCode;

public class DeleteUserSegment {

	public String deleteUserSegmentResponse;

	public void delete_user_segment(String accessToken) {

		deleteUserSegmentResponse=given().log().all().header("Authorization","bearer "+accessToken).header("Content-Type","application/json")
				.when().delete(TestBase.prop.getProperty("deleteUserSegmentURI")).then()
				.assertThat().statusCode(HttpStatusCode.ACCEPTED.getCode()).extract().response().asString();


	}
}