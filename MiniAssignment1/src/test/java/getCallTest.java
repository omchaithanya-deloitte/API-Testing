import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;

import static  io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class getCallTest {

    Response response;

    @Test
    public void test_get_call() {
        response = given().
                baseUri("https://jsonplaceholder.typicode.com").
                header("Content-Type", "application/json").

                when().
                get("/posts").

                then().extract().response();
    }

    @Test
    public void statuscodeValidating() {
        if (response.statusCode() == 200) {
            System.out.println("Status code is 200");
        }
    }

    @Test
    public void userIdValidation() {
        assertThat(response.path("[39].userId"), is(equalTo(4)));
    }

}
