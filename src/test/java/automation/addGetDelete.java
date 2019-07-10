package automation;

import static io.restassured.RestAssured.given;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.testng.annotations.Test;
import automation.Conversion;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import resources.dataDriven;

public class addGetDelete {
	
	String bookID ="";
	
	@Test(priority=1)
	public void addBook() throws IOException
	{
		dataDriven dataFromExcel=new dataDriven();
		ArrayList data=dataFromExcel.getData("RESTAPI","Addbook");
		
		//putting the data from excel to test by using hash map
		HashMap<String, Object>  bookDetails = new HashMap<>();
		bookDetails.put("name", data.get(1));
		bookDetails.put("isbn", data.get(2));
		bookDetails.put("aisle", data.get(3));
		bookDetails.put("author", data.get(4));
		
		RestAssured.baseURI="http://216.10.245.166";
	
		Response response=given().
				header("Content-Type","application/json").
		body(bookDetails).
		when().
		post("/Library/Addbook.php").
		then().assertThat().statusCode(200). //validating the status code in response
		extract().response();
		 JsonPath jsPath= Conversion.rawToJson(response);
		 //saving the bookID to use in further test
		   bookID=jsPath.get("ID");
		   System.out.println("ID of the new book is:  "+bookID);
			
	}
	
	@Test(priority=2)
	public void SearchBook()
	{
		RestAssured.baseURI="http://216.10.245.166";
		Response response=given().
				param("ID",bookID).
		when().
		get("/Library/GetBook.php").
		then().assertThat().statusCode(200).and().//validating the status code in response
		contentType(ContentType.JSON).//validating the content type in response
		extract().response();
		//to display the body of response containing the book details
		JsonPath jsPath= Conversion.rawToJson(response);
				
	}
	
	@Test(priority=3)
	public void DeleteBook()
	{
		//using hashmap for keeping bookID to delete
		HashMap<String, Object>  delBook = new HashMap<>();
		delBook.put("ID", bookID);
		
		RestAssured.baseURI="http://216.10.245.166";
		Response response=given().
				header("Content-Type","application/json").
		body(delBook).
		when().
		delete("/Library/DeleteBook.php").
		then().assertThat().statusCode(200).//validating the status code in response
		extract().response();
		 JsonPath jsPath= Conversion.rawToJson(response);
		 String message = jsPath.get("msg");
		 System.out.println(message);
		
		
	}
	
	
	

}
