package kalagato.user_segment;

import static io.restassured.RestAssured.*;

import files.payload;
import kalagato.TestBase.TestBase;
import utility.HttpStatusCode;

public class CreateUserSegment {

	public String createUserSegmentResponse;

	public void create_user_segment(String accessToken) throws InterruptedException {

		createUserSegmentResponse=given().log().all().header("Authorization","bearer "+accessToken).header("Content-Type","application/json")
				.body(payload.createUserSegmentBody())
				.when().post(TestBase.prop.getProperty("createUserSegmentURI")).then()
				.assertThat().statusCode(HttpStatusCode.CREATED.getCode()).extract().response().asString();

		Thread.sleep(5000);


	}

}
