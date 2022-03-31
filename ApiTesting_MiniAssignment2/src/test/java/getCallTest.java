import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.json.JSONArray;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static  io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.AssertJUnit.assertTrue;

public class getCallTest {

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    ResponseSpecBuilder responseSpecBuilder;
    RequestSpecBuilder requestSpecBuilder;

    @BeforeClass
    public void setup(){
        requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("https://jsonplaceholder.typicode.com").
                addHeader("Content-Type", "application/json");
        requestSpecification = RestAssured.with().spec(requestSpecBuilder.build());

        responseSpecBuilder = new ResponseSpecBuilder().
                expectContentType(ContentType.JSON).expectStatusCode(200);
        responseSpecification = responseSpecBuilder.build();

    }
    Response response;
    @Test
    public void testGetCall() {
        response = requestSpecification.get("/posts");
        //assertThat(response.statusCode(), is(equalTo(200)));

        // title validation
        JSONArray ar = new JSONArray(response.asString());
        String s = "";
        for (int i = 0; i < ar.length(); i++) {
            if (ar.getJSONObject(i).get("title").getClass().getSimpleName() != s.getClass().getSimpleName()) {
                assertTrue(false);

            }
        }
    }

    @Test
    public void userIdValidation() {

        assertThat(response.path("[39].userId"), is(equalTo(4)));
    }
}
