package testAPI;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;

public class Apicheck {
	
	@Test(priority=1)
	public void test_allgames()
	{
		given()
		.when()
		.get("http://localhost:8080/app/videogames")
		.then()
		.statusCode(200);
	}
	@Test(priority=2)
	public void addnewgame()
	{
		HashMap data = new HashMap();
		data.put("id", "120");
		data.put("name", "PUBG");
		data.put("releaseDate", "2019-01-12T12:25:33.362Z");
		data.put("reviewScore", "50");
		data.put("category", "Action");
		data.put("rating", "M");
		
		Response res= given()
		.contentType("application/json")
		.body(data)
		.when()
		.post("http://localhost:8080/app/videogames")
		.then()
		.statusCode(200)
		.log().body()
		.extract().response();
		String Jsonstring =res.asString();
		Assert.assertEquals(Jsonstring.contains("Record Added Successfully"), true);
	}

	@Test(priority=3)
	public void getame()
	{
		given()
		.when()
		.get("http://localhost:8080/app/videogames/120")
		.then()
		.statusCode(200)
		.log().body()
		.body("VideoGame.id", equalTo("120"))
		.body("VideoGame.name", equalTo("PUBG"));
	}
	@Test(priority=4)
	public void updategame()
	{
		HashMap data = new HashMap();
		data.put("id", "120");
		data.put("name", "Avengers");
		data.put("releaseDate", "2019-01-12T12:25:33.362Z");
		data.put("reviewScore", "60");
		data.put("category", "RPG");
		data.put("rating", "universal");
		
	given()
		.contentType("application/json")
		.body(data)
		.when()
		.put("http://localhost:8080/app/videogames/120")
		.then()
		.statusCode(200)
		.log().body() 
		.body("VideoGame.id", equalTo("120"))
		.body("VideoGame.name", equalTo("Avengers"));
		
	}
	@Test(priority=5)
		public void deletegame()
		{

		Response res=	given()
			.when()
			.delete("http://localhost:8080/app/videogames/120")
			.then()
			.statusCode(200)
			.log().body()
			.extract().response();
		String Jsonstring =res.asString();
		Assert.assertEquals(Jsonstring.contains("Record Deleted Successfully"), true);
		}
}
