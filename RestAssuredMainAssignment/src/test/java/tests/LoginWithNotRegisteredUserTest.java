package tests;
import io.restassured.RestAssured;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.*;
import java.util.Properties;

import static  io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class LoginWithNotRegisteredUserTest {

    String propertyFilePath = "C://Users//omcv//IdeaProjects//RestAssuredMainAssignment//src//test//resources//config.properties";
    String LoginBasePath;
    String ContentType;
    String Baseuri;
    ;
    @BeforeClass
    public void setup() throws IOException {
        FileInputStream propertyFile  = new FileInputStream(propertyFilePath);

        Properties property = new Properties();
        property.load(propertyFile);

        Baseuri = (String) property.get("baseUri");
        ContentType = property.getProperty("content-type");
        LoginBasePath = property.getProperty("LoginBasePath");

    }

    Response response;
    @Test
    public void userLoginOfNotRegistered() throws IOException {

        JSONObject bodyParameters = new JSONObject();
        bodyParameters.put("password", "pass123");
        bodyParameters.put("email", "omcv@123");

        RestAssured.baseURI = Baseuri;

        response = given().
                header("Content-Type", ContentType).
                body(bodyParameters.toString()).
                when().
                post(LoginBasePath).
                then().assertThat().statusCode(400).and().
                log().all().
                extract().
                response();
    }

    @Test
    public void validateContentType(){
        assertThat(ContentType, equalTo("application/json"));
    }

    @Test
    public void validateError(){
        String jsonString = response.getBody().asString();
        //System.out.println(jsonString);
        assertThat(jsonString, is(equalTo("\"Unable to login\"")));
    }

}
