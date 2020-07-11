package kalagato.user_segment;

import static io.restassured.RestAssured.*;

import kalagato.TestBase.TestBase;
import utility.HttpStatusCode;

public class GetUserSegments {
	public String getUserSegmentsResponse;
	
	public void get_user_segments(String accessToken) {
		
		getUserSegmentsResponse=given().log().all().header("Authorization","bearer "+accessToken)
		.when().get(TestBase.prop.getProperty("getUserSegmentsURI")).then().assertThat().statusCode(HttpStatusCode.OK.getCode())
		.extract().response().asString();
		
		System.out.println(getUserSegmentsResponse);
	}

}
