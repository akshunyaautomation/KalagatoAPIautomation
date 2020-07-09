package kalagato.master_filter;


import static io.restassured.RestAssured.*;
public class GetMasterFilter {
	
	public String getMasterFilterResponse;
	

	public void get_master_filter(String accessToken) {
	
		
	 getMasterFilterResponse=given().log().all().header("Authorization","bearer "+accessToken)
		.when().get("/api/v1/master-filter")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		System.out.print(getMasterFilterResponse);
		
		
		
	}
	
}
