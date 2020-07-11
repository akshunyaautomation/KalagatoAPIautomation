package kalagato.master_filter;


import static io.restassured.RestAssured.*;

import kalagato.TestBase.TestBase;
import utility.HttpStatusCode;
public class GetMasterFilter {
	
	public String getMasterFilterResponse;
	

	public void get_master_filter(String accessToken) {
	
		
	 getMasterFilterResponse=given().log().all().header("Authorization","bearer "+accessToken)
		.when().get(TestBase.prop.getProperty("getMasterURI"))
		.then().assertThat().statusCode(HttpStatusCode.OK.getCode()).extract().response().asString();
		
		System.out.print(getMasterFilterResponse);
		
		
		
	}
	
}
