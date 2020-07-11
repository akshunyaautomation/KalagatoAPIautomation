package kalagato.user_segment;

import static io.restassured.RestAssured.given;

import kalagato.TestBase.TestBase;
import utility.HttpStatusCode;

public class GetUserSegment {
public String getUserSegmentResponse;
	
	public void get_user_segment(String accessToken) {
		
		getUserSegmentResponse=given().log().all().header("Authorization","bearer "+accessToken)
		.when().get(TestBase.prop.getProperty("getUserSegmentURI")).then().assertThat().statusCode(HttpStatusCode.OK.getCode())
		.extract().response().asString();
		
		System.out.println(getUserSegmentResponse);
	}


}
