package kalagato.user_segment;

import static io.restassured.RestAssured.given;

import java.util.List;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import kalagato.TestBase.TestBase;

public class GetUserSegments {
	public Response getUserSegmentsResponse;
	public static String getUserSegmentsName;
	public static String getUserSegmentsID;
	public static int statusCode;

	public void get_user_segments(String accessToken) throws InterruptedException {

		Thread.sleep(5000);

		getUserSegmentsResponse=given().log().all().header("Authorization","bearer "+accessToken)
				.when().get(TestBase.prop.getProperty("getUserSegmentsURI")).then()
				.contentType(ContentType.JSON).extract().response();

		statusCode= getUserSegmentsResponse.getStatusCode();

		List<String> jsonResponse = getUserSegmentsResponse.jsonPath().getList("$");
		int size = jsonResponse.size();
		System.out.println(size);
		List<String> name = getUserSegmentsResponse.jsonPath().getList("name");
		getUserSegmentsName= name.get(size-1);

		List<String> id = getUserSegmentsResponse.jsonPath().getList("id");
		getUserSegmentsID= id.get(size-1);

	}

}
