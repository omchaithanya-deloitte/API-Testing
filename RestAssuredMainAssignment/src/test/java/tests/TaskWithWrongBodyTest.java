package tests;

import io.restassured.response.Response;
import org.json.JSONObject;
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

public class TaskWithWrongBodyTest {

    String TokenFilePath = "C://Users//omcv//IdeaProjects//RestAssuredMainAssignment//src//test//resources//tokenFile.txt";
    String token = "";

    String AddTaskBasePath;
    String Baseuri;
    String ContentType;
    @BeforeClass
    public void setup() throws IOException {
        File tokenFile = new File(TokenFilePath);
        FileReader filereader = new FileReader(tokenFile);
        int ch;
        while((ch = filereader.read())!=-1){
            token = token+(char)ch;
        }
        filereader.close();

        FileInputStream propertyFile  = new FileInputStream("C://Users//omcv//IdeaProjects//RestAssuredMainAssignment//src//test//resources//config.properties");
        Properties property = new Properties();
        property.load(propertyFile);
        AddTaskBasePath = property.getProperty("AddTaskBasePath");
        Baseuri = (String) property.get("baseUri");
        ContentType = property.getProperty("content-type");
    }

    Response response;
    @Test
    public void addTasksWithWrongBody(){
                    // add task with wrong body and validate the error message
                    JSONObject bodyParameters = new JSONObject();
                    bodyParameters.put("descriptions", "wrong body");
                    //System.out.println(bodyParameters);

                     response = given().
                            baseUri(Baseuri).
                            header("Content-Type", ContentType).
                            header("Authorization", "Bearer " +token).
                            body(bodyParameters.toString()).
                            when().
                            post(AddTaskBasePath).
                            then().assertThat().
                            statusCode(400).and().
                            log().all().
                            extract().
                            response();
    }

    @Test
    public void validateContentType(){
        assertThat(ContentType, equalTo("application/json"));
    }

    @Test
    public void validateError(){    // validating the error message
        String jsonString = response.getBody().asString();
        //System.out.println(jsonString);
        assertThat(jsonString, is(equalTo("\"Task validation failed: description: Path `description` is required.\"")));
    }

}
