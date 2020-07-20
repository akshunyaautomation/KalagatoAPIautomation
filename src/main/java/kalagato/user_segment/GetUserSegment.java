package kalagato.user_segment;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import kalagato.TestBase.TestBase;

public class GetUserSegment {
	public Response getUserSegmentResponse;
	public static String getUserSegmentName;
	public static String getUserSegmentID;
	public static int statusCode;

	public void get_user_segment(String accessToken) throws InterruptedException {

		Thread.sleep(5000);

		getUserSegmentResponse=given().log().all().header("Authorization","bearer "+accessToken)
				.when().get(TestBase.prop.getProperty("getUserSegmentURI")+GetUserSegments.getUserSegmentsID).then()
				.contentType(ContentType.JSON).extract().response();

		statusCode= getUserSegmentResponse.getStatusCode();

		getUserSegmentName = getUserSegmentResponse.jsonPath().getString("name");

		getUserSegmentID = getUserSegmentResponse.jsonPath().getString("id");


	}


}
