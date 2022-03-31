import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapper;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class putCallTest {

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    @BeforeClass
    public void setup(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("https://reqres.in/api").
                addHeader("Content-Type", "application/json");

        requestSpecification = RestAssured.with().spec(requestSpecBuilder.build());

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectContentType(ContentType.JSON).expectStatusCode(200);
        responseSpecification = responseSpecBuilder.build();
    }

    Response response;
    String path = "C://Users//omcv//IdeaProjects//ApiTesting_MiniAssignment2//src//test//resources//Data.json";
    @Test
    public void testGetCall(){
        File jsonFile = new File(path);
        given().
                spec(requestSpecification).
                body(jsonFile).
                
        when().
                put("/users");
    }

}
