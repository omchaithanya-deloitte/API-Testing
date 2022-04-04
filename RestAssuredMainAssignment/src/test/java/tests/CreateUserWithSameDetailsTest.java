package tests;

import io.restassured.response.Response;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class CreateUserWithSameDetailsTest {
    String propertyFilePath = "C://Users//omcv//IdeaProjects//RestAssuredMainAssignment//src//test//resources//config.properties";
    String DataFilePath = "C://Users//omcv//IdeaProjects//RestAssuredMainAssignment//src//test//resources//CreateUserData.xlsx";
    String email; String password; String name;String age;
    String RegisterBasePath;
    String ContentType;
    String Baseuri;

    XSSFSheet sheet;
    @BeforeClass
    public void setup() throws IOException {
        FileInputStream propertyFile  = new FileInputStream(propertyFilePath);
        File dataFile = new File(DataFilePath);
        FileInputStream DataFile = new FileInputStream(dataFile);

        Properties property = new Properties();
        property.load(propertyFile);

        Baseuri = (String) property.get("baseUri");
        RegisterBasePath = property.getProperty("RegisterBasePath");
        ContentType = property.getProperty("content-type");

        XSSFWorkbook workbook = new XSSFWorkbook(DataFile);
        sheet = workbook.getSheetAt(0);

        name = sheet.getRow(1).getCell(0).getStringCellValue();
        email = sheet.getRow(1).getCell(1).getStringCellValue();
        password = sheet.getRow(1).getCell(2).getStringCellValue();
        age =  String.valueOf(sheet.getRow(1).getCell(3).getNumericCellValue());
    }
    Response response;
    @Test
    public void createUserWithSameDetails() {

        JSONObject bodyParameters = new JSONObject();
        bodyParameters.put("name", name);
        bodyParameters.put("email", email);
        bodyParameters.put("password", password);
        bodyParameters.put("age", age);

        response = given()
                .baseUri(Baseuri)
                .header("Content-Type", ContentType)
                .body(bodyParameters.toString())

                .when().post(RegisterBasePath)
                .then().assertThat().statusCode(400).log().all().extract().response();
    }

    @Test
    public void validateContentType(){
        assertThat(ContentType, equalTo("application/json"));
    }

    @Test
    public void validateError(){
        String jsonString = response.getBody().asString();
        //System.out.println(jsonString);
        assertThat(jsonString, is(equalTo("\"E11000 duplicate key error collection: todo-list.users index: email_1 dup key: { email: \\\""+email+"\\\" }\"")));
    }
}
