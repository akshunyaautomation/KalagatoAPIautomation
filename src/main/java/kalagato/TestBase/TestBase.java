package kalagato.TestBase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import io.restassured.RestAssured;

public class TestBase {
	
	public static Properties prop;
	
	public static void baseUri(){
		
		if(prop == null) {
			try {
				prop=new Properties();
				FileInputStream ip= new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\files\\config.properties");
				prop.load(ip);
				
			}catch(FileNotFoundException e) {
				e.printStackTrace();
			}catch(IOException e) {
				e.printStackTrace();
			}
			RestAssured.baseURI= prop.getProperty("baseURI");
		}

	}

}
