package KarrosAssignmentPackge;

import io.restassured.path.json.JsonPath;
import org.json.JSONArray;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import sun.jvm.hotspot.ui.tree.SimpleTreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class helloword {
    private static WebDriver driver;

    @BeforeTest
    public void beforeTest() {
        //driver = new FirefoxDriver();
    }

    @AfterTest
    public void afterTest() {
        //driver.quit();

    }

    /*
        @Test(priority = 1)
        public void startFireFoxDriver() throws Exception{
    // Specify the base URL to the RESTful web service
            RestAssured.baseURI = "http://demo6906887.mockable.io/api/v1";

            // Get the RequestSpecification of the request that you want to sent
            // to the server. The server is specified by the BaseURI that we have
            // specified in the above step.
            RequestSpecification httpRequest = RestAssured.given();

            // Make a request to the server by specifying the method Type and the method URL.
            // This will return the Response from the server. Store the response in a variable.
            Response response = httpRequest.request(Method.GET, "/studentAccessRequests");

            // Now let us print the body of the message to see what response
            // we have recieved from the server
            String responseBody = response.getBody().asString();
            System.out.println("Response Body is =>  " + responseBody);

            int statusCode = response.getStatusCode();
            System.out.println("Response Status code is =>  " + statusCode);

        } */
    @Test(priority = 2)
    public void JsonPathUsage() throws Exception {
        RestAssured.baseURI = "http://demo6906887.mockable.io/api/v1";
        RequestSpecification httpRequest = RestAssured.given();

        // Get JSON response from API
        //Response response = httpRequest.get("/studentAccessRequests");
        Response response = httpRequest.queryParam("status", "approved").queryParam("requesterEmail", "dqlambor").get("/studentAccessRequests");

        // First get the JsonPath object instance from the Response interface
        JsonPath jsonPathEvaluator = response.jsonPath();

        // Read all the books as a List of String. Each item in the list
        // represent a book node in the REST service Response
        List<String> allStudent = jsonPathEvaluator.getList("studentAccessRequests.status");

        System.out.println("Size is: " + allStudent.size());

        // Iterate over the list and print individual book item
        for (String email : allStudent) {
            System.out.println("All Student: " + email);
        }

    }

    public int checkFilterAPI(String status, String requesterEmail, String firstName, String lastName, String studentDistrictId) {

        int iResut = 0;
        RestAssured.baseURI = "http://demo6906887.mockable.io/api/v1";
        RequestSpecification httpRequest = RestAssured.given();

        // Get JSON response from API
        Response response = httpRequest.queryParam("status", status).queryParam("requesterEmail", requesterEmail).queryParam("firstName", firstName).queryParam("lastName", lastName).queryParam("studentDistrictId", studentDistrictId).get("/studentAccessRequests");

        // get the JsonPath object instance from the Response interface
        JsonPath jsonPathEvaluator = response.jsonPath();

        // Read all the records item in JSON as a List of String. Each item in the list
        List<String> responseStatus = jsonPathEvaluator.getList("studentAccessRequests.status");
        List<String> responseRequesterEmail = jsonPathEvaluator.getList("studentAccessRequests.requesterEmail");
        List<String> responseFirstName = jsonPathEvaluator.getList("studentAccessRequests.firstName");
        List<String> responseLastName = jsonPathEvaluator.getList("studentAccessRequests.lastName");
        List<String> responseStudentDistrictId = jsonPathEvaluator.getList("studentAccessRequests.studentDistrictId");


        //compare keywords input vs returned data
        // Idea: each attribute of
        for (int i = 0; i < responseStatus.size(); i++) {
            if (responseStatus.get(i).contains(status) == true) {
                if (responseRequesterEmail.get(i).contains(requesterEmail) == true) {
                    if (responseFirstName.get(i).contains(firstName) == true) {
                        if (responseLastName.get(i).contains(lastName) == true) {
                            if (responseStudentDistrictId.get(i).contains(studentDistrictId) == true) {
                                iResut = 1;
                                System.out.println("First name is: " + responseFirstName.get(i));
                                // break; //Break to test 1st result
                            } else {
                                iResut = 0;
                                System.out.println("Response responseStudentDistrictId " + responseStudentDistrictId.get(i) + " does NOT contains studentDistrictId " + studentDistrictId);
                                break;
                            }
                        } else {
                            iResut = 0;
                            System.out.println("Response of Last Name " + responseStudentDistrictId.get(i) + " does NOT contains lastname " + lastName);
                            break;
                        }
                    } else {
                        iResut = 0;
                        System.out.println("Response of FirstName " + responseFirstName.get(i) + " does NOT contains FirstName " + firstName);
                        break;
                    }

                } else {
                    iResut = 0;
                    System.out.println("Response of requesterEmail " + responseRequesterEmail.get(i) + " does NOT contains requesterEmail " + requesterEmail);
                    break;
                }
            } else {
                iResut = 0;
                System.out.println("Response status [" + responseStatus.get(i) + "] of the item #" + i + " does NOT contains status from keyword [" + status + "]");
                break;
            }

        }
        //return 1 when all matched
        return iResut;
    }

    @Test(priority = 3)
    public void testFilter() {
        int testResult = this.checkFilterAPI("approved", "test+giapios@karrostech.com", "KIMBER", "MICHALSON", "111318");
        String strResult = String.valueOf(testResult);

        Assert.assertEquals(strResult, "1");
        System.out.println("Filter Success");
    }


    @Test(priority = 4)
    public void validateOpenFilterWindows() throws Exception {
        // validate the ability to open Filter windows.
        driver.findElement(By.xpath("//button[@class='btn btn-filter module_grid__btn_filter btn btn-default']")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='modal-content']")).isDisplayed());
        java.lang.Thread.sleep(2000);
    }

    @Test(priority = 5)
    public void validateCancelFilter() throws Exception {
        // cancel and close the Filter
        // Filter already open at previous step.
        driver.findElement(By.xpath("//button[@class='btn btn-default']")).click();
        Assert.assertFalse(driver.findElement(By.xpath("//div[@class='modal-content']")).isDisplayed());
        java.lang.Thread.sleep(3000);
    }


    //@Test(priority = 6)
    public void validateApplyFilter() throws Exception {
        // validate the ability to apply Filter ( NOT for checking result)

        driver.findElement(By.xpath("//button[@class='btn btn-filter module_grid__btn_filter btn btn-default']")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        // select status Inactive from dropdown list
        driver.findElement(By.xpath("//select[@id='formControlsSelect']/option[@value='inactive']")).click();

        ///html//input[@id='formHorizontalEmail']
        driver.findElement(By.xpath("/html//input[@id='formHorizontalEmail']")).sendKeys("quangnguyen90@gmail.com");

        // /html//input[@id='formHorizontalStudentID']
        driver.findElement(By.xpath("/html//input[@id='formHorizontalStudentID']")).sendKeys("0812408");

        // /html//input[@id='formHorizontalStudentFN']
        driver.findElement(By.xpath("/html//input[@id='formHorizontalStudentFN']")).sendKeys("Quang");

        // /html//input[@id='formHorizontalStudentLN']formHorizontalStudentLN
        driver.findElement(By.xpath("/html//input[@id='formHorizontalStudentLN']")).sendKeys("Nguyen");

        // Click to Apply Filter:
        driver.findElement(By.xpath("//button[@class='btn-filter btn btn-default']")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        //to validate all the filter conditions are applied correctly on UI
        WebElement parentElement = driver.findElement(By.xpath("//div[@class='query__filter']"));

        List<WebElement> childElements = parentElement
                .findElements(By.xpath("//a[@class='query__filter__item']"));

        String[] conditionItems = new String[]{"Inactive", "quangnguyen90@gmail.com", "0812408", "Quang", "Nguyen"};

        int iSize = childElements.size();
        int testResult = 0;
        for (int i = 0; i < iSize; i++) {
            //if childelement[i] contains string[i] =>
            if (childElements.get(i).getText().contains(conditionItems[i]) != true) {
                testResult = 0;
                break;
            } else
                testResult = 1;
        }
        Assert.assertEquals(String.valueOf(testResult), "1");
        System.out.println("Filter condition has been applied correctly.");

        java.lang.Thread.sleep(1000);
    }

    @Test(priority = 13)
    public void testArrayStringSort() throws Exception{
        String[] Month1={"VINCENT", "NATHANIAL","SAUNDRA", "MARILYN", "GAVIN", "MARILYN", "DEVIN", "DAVID", "KIMBER"};
        String[] Month2={"VINCENT", "SAUNDRA", "NATHANIAL", "MARILYN", "MARILYN", "KIMBER", "GAVIN", "DEVIN", "DAVID"};
        List<String> copyOf1 = new ArrayList<String>(Arrays.asList(Month1));
        List<String> copyOf2 = new ArrayList<String>(Arrays.asList(Month2));

        Collections.sort(copyOf1);
        Collections.reverse(copyOf1);

        if (Arrays.asList(Month2).equals(copyOf1)){
            System.out.println("Sorted");
        } else {
            System.out.println("Not sorted"); // Not sorted of course but if Month
            // was {"Add", "Siri"} it would've been true
        }
    }

    //@Test(priority = 99)
    public void xPathFinding() {
        WebElement x, y, z;
        //x = driver.findElement(By.xpath("//tr[contains(., 'Approved')/td[2]/div[1]/p and contains(text(), '10/08/2019')/td[3]/div[1]]"));

        //z = driver.findElement(By.xpath("//tr/td/div/p[contains(.,'Approved')]"));
        //z = driver.findElement(By.xpath("//tr[td/div/p[contains(.,'Approved')] and td[div='Invalid date']]"));//work
        z = driver.findElement(By.xpath("//tr[td/div/p[contains(.,'Approved')] and td[3]/div[contains(.,'2019')]]"));// WORK
        System.out.println(z.toString());

        List<WebElement> a = driver.findElements(By.xpath("//tr[td/div/p[contains(.,'Approved')] and td[3]/div[contains(.,'2019')]]"));// WORK

        //y = driver.findElement(By.xpath("//tr/td[3][div='Invalid date']")); //works.

        //table[@class="table_list"]
        // tr[td[@class="col_firstname"] = "John" and td[@class="col_lastname"] = "Wayne"]/td[@class="col_functions"]/text()

        System.out.println(a.size());
    }

}
