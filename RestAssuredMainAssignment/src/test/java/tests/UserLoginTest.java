package tests;


import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UserLoginTest {

    String propertyFilePath = "C://Users//omcv//IdeaProjects//RestAssuredMainAssignment//src//test//resources//config.properties";
    String DataFilePath = "C://Users//omcv//IdeaProjects//RestAssuredMainAssignment//src//test//resources//CreateUserData.xlsx";
    String email;
    String password;
    String LoginBasePath;
    String ContentType;
    String Baseuri;
    static String token;

    XSSFSheet sheet;
    @BeforeClass
    public void setup() throws IOException {
        // read data from properties file
        FileInputStream propertyFile  = new FileInputStream(propertyFilePath);
        File dataFile = new File(DataFilePath);
        FileInputStream DataFile = new FileInputStream(dataFile);

        Properties property = new Properties();
        property.load(propertyFile);

        Baseuri = (String) property.get("baseUri");
        ContentType = property.getProperty("content-type");
        LoginBasePath = property.getProperty("LoginBasePath");
        // read excel file for user details
        XSSFWorkbook workbook = new XSSFWorkbook(DataFile);
        sheet = workbook.getSheetAt(0);
        email = sheet.getRow(1).getCell(1).getStringCellValue();
        password = sheet.getRow(1).getCell(2).getStringCellValue();
    }

    Response response;
    @Test
    public void userLogin() throws IOException {
        // pass email and password to a json object
        JSONObject bodyParameters = new JSONObject();
        bodyParameters.put("password", password);
        bodyParameters.put("email", email);

        RestAssured.baseURI = Baseuri;

        response = given().
                header("Content-Type", ContentType).
                body(bodyParameters.toString()).
                when().
                post(LoginBasePath).
                then().assertThat().statusCode(200).and().
                log().all().
                extract().
                response();
        // store the json response as a string and get token using JsonPath
        String jsonString = response.getBody().asString();
        token = JsonPath.from(jsonString).get("token");
        // write token into a text file to use it in add tasks test
        FileWriter fw = new FileWriter("C://Users//omcv//IdeaProjects//RestAssuredMainAssignment//src//test//resources//tokenFile.txt");
        fw.write(token);
        fw.close();
    }

    @Test
    public void validateContentType(){    // verify content type
        assertThat(ContentType, equalTo("application/json"));
    }


}
