package qa.com.ApiTesting;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;

public class ApiTest {
	public static void main(String args[]) {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String addPlace = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(payload.AddPlace()).when().post("maps/api/place/add/json").then().assertThat().statusCode(200)
				.body("scope", equalTo("APP")).header("server", "Apache/2.4.18 (Ubuntu)").extract().response()
				.asString();
		System.out.println(addPlace);
		JsonPath jp = new JsonPath(addPlace);
		String placeId = jp.getString("place_id");
		String newAddress = "Summer Walk, Africa";

		String updatePlace = given().log().all().queryParam("key", "qaclick123")
				.header("Content-Type", "application/json")
				.body("{\r\n" + "\"place_id\":\"" + placeId + "\",\r\n" + "\"address\":\"" + newAddress + "\",\r\n"
						+ "\"key\":\"qaclick123\"\r\n" + "}")
				.when().put("maps/api/place/update/json").then().assertThat().log().all().statusCode(200)
				.body("msg", equalTo("Address successfully updated")).extract().response().asString();
		System.out.println(updatePlace);

		String getPlace = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId).when()
				.get("maps/api/place/get/json").then().assertThat().log().all().statusCode(200).extract().response()
				.asString();

		System.out.println(getPlace);
		JsonPath jpath = new JsonPath(getPlace);
		String name = jpath.getString("name");
		String expectedName = "Rahul Shetty Academy";
		Assert.assertEquals(name, expectedName);

		String deletePlace = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
				.header("Content-Type", "application/json")
				.body("{\r\n" + "    \"place_id\":\"" + placeId + "\"\r\n" + "}").when()
				.delete("maps/api/place/delete/json").then().assertThat().log().all().statusCode(200)
				.body("status", equalTo("OK")).extract().response().asString();

		System.out.println(deletePlace);
	}
}
