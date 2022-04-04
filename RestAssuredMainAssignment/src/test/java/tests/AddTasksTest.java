package tests;

import io.restassured.response.Response;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.*;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class AddTasksTest {

    String TokenFilePath = "C://Users//omcv//IdeaProjects//RestAssuredMainAssignment//src//test//resources//tokenFile.txt";
    String token = "";

    XSSFSheet sheet;

    String AddTaskBasePath;
    String Baseuri;
    String ContentType;
    @BeforeClass
    public void setup() throws IOException {
        // read token from a text file and store it in a string
        File tokenFile = new File(TokenFilePath);
        FileReader filereader = new FileReader(tokenFile);
        int ch;
        while((ch = filereader.read())!=-1){
            token = token+(char)ch;
        }
        filereader.close();
        // read data from properties file
        FileInputStream propertyFile  = new FileInputStream("C://Users//omcv//IdeaProjects//RestAssuredMainAssignment//src//test//resources//config.properties");
        Properties property = new Properties();
        property.load(propertyFile);
        AddTaskBasePath = property.getProperty("AddTaskBasePath");
        Baseuri = (String) property.get("baseUri");
        ContentType = property.getProperty("content-type");
        // read user details from a excel file
        File dataFile = new File("C://Users//omcv//IdeaProjects//RestAssuredMainAssignment//src//test//resources//tasks.xlsx");
        FileInputStream DataFile = new FileInputStream(dataFile);
        XSSFWorkbook workbook = new XSSFWorkbook(DataFile);
        sheet = workbook.getSheetAt(0);
    }
    @Test
    public void addTasks(){
        XSSFRow row = null;
        XSSFCell cell = null;
        String task;
        // get each task from excel file and add it using post call
        for (int row_ = 1; row_ <= sheet.getLastRowNum(); row_++) {
            row = sheet.getRow(row_);
            for (int col = 0; col <= row.getLastCellNum(); col++) {
                cell = row.getCell(col);
                if (col == 0) {
                    task = cell.getStringCellValue();

                    JSONObject bodyParameters = new JSONObject();
                    bodyParameters.put("description", task);
                    //System.out.println(bodyParameters);
                    Response response = given().
                            baseUri(Baseuri).
                            header("Content-Type", ContentType).
                            header("Authorization", "Bearer " +token).
                            body(bodyParameters.toString()).
                            when().
                            post(AddTaskBasePath).
                            then().assertThat().
                            statusCode(201).and().
                            log().all().
                            extract().
                            response();
                    // validate all the inputs
                    assertThat(response.path("success"), is(equalTo(true)));
                    assertThat(response.path("data.description"), is(equalTo(task)));
                    System.out.println(task+" validated");
                    System.out.println();
                }
            }
        }
        System.out.println("All tasks are validated");
    }

    @Test
    public void validateContentType(){    // verify the content type
        assertThat(ContentType, equalTo("application/json"));
    }
}
