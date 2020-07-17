package qa.com.ApiTesting;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NestedJsonParsing {
	@Test
	public static void SumOfCourses() {
		JsonPath js = new JsonPath(payload.getCourses());
		int totalCourses = js.getInt("courses.size");
		double courseTotalPrices =0.0;
		System.out.println(js.getInt("courses.size"));
		System.out.println(js.getDouble("dashboard.purchaseAmount"));
		System.out.println(js.getString("courses[0].title"));
		for(int i=0;i<totalCourses;i++) {
			System.out.println(js.getString("courses["+i+"].title"));
			System.out.println(js.getDouble("courses["+i+"].price"));
			courseTotalPrices= courseTotalPrices+(js.getDouble("courses["+i+"].price")*js.getInt("courses["+i+"].copies"));
			if(js.getString("courses["+i+"].title").equalsIgnoreCase("RPA")) {
				System.out.println(js.getInt("courses["+i+"].copies"));
			}
			
		}
		Assert.assertEquals(courseTotalPrices, js.getDouble("dashboard.purchaseAmount"));
		
	}

}
