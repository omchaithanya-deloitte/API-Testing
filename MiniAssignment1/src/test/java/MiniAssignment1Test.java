import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;

import static  io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class MiniAssignment1Test {

    @Test
    public void test_get_call(){
        Response response = (Response) given().
                baseUri("https://jsonplaceholder.typicode.com").
                header("Content-Type", "application/json").

        when().
                get("/posts").

        then().
                statusCode(200);
                //assertThat().header("Content-Type","application/json");
    }

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
                //assertThat().header("Content-Type","application/json");

    }

}
