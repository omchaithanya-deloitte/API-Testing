package tests;

import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class PaginationTest {
    String TokenFilePath = "C://Users//omcv//IdeaProjects//RestAssuredMainAssignment//src//test//resources//tokenFile.txt";
    String token = "";

    String Baseuri;
    String ContentType;

    @BeforeClass
    public void setup() throws IOException {
        // read token from text file and store it in a string
        File tokenFile = new File(TokenFilePath);
        FileReader filereader = new FileReader(tokenFile);
        int ch;
        while ((ch = filereader.read()) != -1) {
            token = token + (char) ch;
        }
        filereader.close();
        // read data from properties file
        FileInputStream propertyFile  = new FileInputStream("C://Users//omcv//IdeaProjects//RestAssuredMainAssignment//src//test//resources//config.properties");
        Properties property = new Properties();
        property.load(propertyFile);
        Baseuri = (String) property.get("baseUri");
        ContentType = property.getProperty("content-type");

    }

    Response response;

    @Test
    public void paginationLimit2(){   // for limit = 2
        response = given().
                baseUri(Baseuri).
                header("Content-Type", ContentType).
                header("Authorization", "Bearer "+token).
                when().
                get("/task?limit=2&skip=10").
                then().assertThat().
                statusCode(200).
                log().all().
                extract().response();

        //List<String> ResponseList = response.jsonPath().get("data._id");
        assertThat(response.path("count"), is(equalTo(2)));   // validating
    }

    @Test
    public void paginationLimit5(){  // for limit = 5
        response = given().
                baseUri(Baseuri).
                header("Content-Type", ContentType).
                header("Authorization", "Bearer "+token).
                when().
                get("/task?limit=5&skip=10").
                then().assertThat().
                statusCode(200).
                log().all().
                extract().response();

        //List<String> ResponseList = response.jsonPath().get("data._id");
        assertThat(response.path("count"), is(equalTo(5)));   // validating
    }

    @Test
    public void paginationLimit10(){   // for limit = 10
        response = given().
                baseUri(Baseuri).
                header("Content-Type", ContentType).
                header("Authorization", "Bearer "+token).
                when().
                get("/task?limit=10&skip=10").
                then().assertThat().
                statusCode(200).
                log().all().
                extract().response();

        //List<String> ResponseList = response.jsonPath().get("data._id");
        assertThat(response.path("count"), is(equalTo(10)));   // validating
    }
}
