import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class putCallTest {

    Response response;

    @Test
    public void test_put_call(){
        File jsonFile = new File("C://Users//omcv//IdeaProjects//API Testing//src//resources//putCall.json");
        given().
                baseUri("https://reqres.in/api").
                header("Content-Type", "application/json").
                body(jsonFile).

        when().
                put("/users").
        then().
                statusCode(200).
                body("name",equalTo("Arun")).
                body("job",equalTo("Manager"));


    }

}

