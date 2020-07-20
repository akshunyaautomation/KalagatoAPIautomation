package files;

public class payload {

	public static String loginBody(String username, String password) {
		return "{\r\n" + 
				"	\"user_name\": \""+username+"\",\r\n" + 
				"	\"password\": \""+password+"\",\r\n" + 
				"	\"grant_type\": \"password\"\r\n" + 
				"}";

	}

	public static String logoutBody(String token) {
		return "{\r\n" + 
				"	\"refresh_token\":\""+token+"\"\r\n" + 
				"}";
	}

	public static String refreshTokenBody(String token) {

		return "{\r\n" + 
				"	\"refresh_token\":\""+token+"\",\r\n" + 
				"	\"grant_type\":\"refresh_token\"\r\n" + 
				"}";
	}

	public static String createUserBody(String firstname, String lastname, String email,String newpassword) {
		return "[\r\n" + 
				"    {\r\n" + 
				"        \"first_name\": \""+firstname+"\",\r\n" + 
				"        \"last_name\": \""+lastname+"\",\r\n" + 
				"        \"email\": \""+email+"\",\r\n" + 
				"        \"password\": \""+newpassword+"\"\r\n" + 
				"    }\r\n" + 
				"]";
	}

	public static String createUserSegmentBody() {
		return "[\r\n" + 
				"    {\r\n" + 
				"        \"name\": \"Create By automation new\",\r\n" + 
				"        \"segment\": {\r\n" + 
				"            \"demography\": [\r\n" + 
				"                {\r\n" + 
				"                    \"UserSession.sectorLabel\": \"Messaging & Communication\",\r\n" + 
				"                    \"UserSession.sector\": \"Messaging_Communication\",\r\n" + 
				"                    \"UserSession.company\": \"BBM\"\r\n" + 
				"                }\r\n" + 
				"            ],\r\n" + 
				"            \"sectors\": [\r\n" + 
				"                {\r\n" + 
				"                    \"UserSession.sectorLabel\": \"Messaging & Communication\",\r\n" + 
				"                    \"UserSession.sector\": \"Messaging_Communication\",\r\n" + 
				"                    \"UserSession.company\": \"BBM\"\r\n" + 
				"                }\r\n" + 
				"            ]\r\n" + 
				"        },\r\n" + 
				"        \"is_selected\": true\r\n" + 
				"    }\r\n" + 
				"]";
	}

	public static String updateUserSegmentBody() {
		return "{\r\n" + 
				"    \"name\": \"Update from automation new\",\r\n" + 
				"    \"segment\": [\r\n" + 
				"        {\r\n" + 
				"            \"demography\": [\r\n" + 
				"                {\r\n" + 
				"                    \"UserSession.sectorLabel\": \"Messaging & Communication\",\r\n" + 
				"                    \"UserSession.sector\": \"Messaging_Communication\",\r\n" + 
				"                    \"UserSession.company\": \"BBM\"\r\n" + 
				"                }\r\n" + 
				"            ],\r\n" + 
				"            \"sectors\": [\r\n" + 
				"                {\r\n" + 
				"                    \"UserSession.sectorLabel\": \"Messaging & Communication\",\r\n" + 
				"                    \"UserSession.sector\": \"Messaging_Communication\",\r\n" + 
				"                    \"UserSession.company\": \"BBM\"\r\n" + 
				"                }\r\n" + 
				"            ]\r\n" + 
				"        }\r\n" + 
				"    ],\r\n" + 
				"    \"is_selected\": true\r\n" + 
				"}";
	}

	public static String changePasswordFromAppBody() {
		return "{	\r\n" + 
				"	\"email\":\"akshunya.jugran1@quovantis.com\",\r\n" + 
				"	\"password\": \"Kalagato@123\",\r\n" + 
				"	\"newPassword\":\"Kalagato@123\"\r\n" + 
				"}";
	}

	public static String forgetPasswordRequestBody() {
		return "{\r\n" + 
				"	\"email\": \"deepak.singh@quovantis.com\"\r\n" + 
				"}";
	}


	public static String resetPasswordWithEmailBody() {
		return "{	\r\n" + 
				"	\"password\": \"Kalagato@12345\"\r\n" + 
				"}";
	}

	public static String logErrorBody() {
		return "{\r\n" + 
				"    \"errorDetails\": {\r\n" + 
				"        \"stack\": {\r\n" + 
				"            \"error\": \"This is first error after reboot\",\r\n" + 
				"            \"code\": 400\r\n" + 
				"        }\r\n" + 
				"    }\r\n" + 
				"}";
	}


}
