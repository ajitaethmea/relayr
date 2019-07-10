package automation;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Conversion {
	
	public static JsonPath rawToJson(Response resp)
	{ 
		String respone=resp.asString();
		JsonPath jsPath=new JsonPath(respone);
		System.out.println(respone);
		return jsPath;
	}
	

}
