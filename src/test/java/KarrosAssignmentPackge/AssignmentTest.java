package KarrosAssignmentPackge;

import java.util.*;
import java.util.concurrent.TimeUnit;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;


/**
 * Created by lambor on 19/4/20.
 */
public class AssignmentTest {
    private static WebDriver driver;

    @BeforeTest
    public void beforeTest() {
        driver = new FirefoxDriver();
    }

    @AfterTest
    public void afterTest() {
        driver.quit();
        System.out.println("Tearing down...finish this test!!!");
    }

    // Method to start firefox driver
    @Test(priority = 1)
    public void startFireFoxDriver() {
        //open Login page
        driver.manage().window().maximize();
        driver.navigate().to("http://ktvn-test.s3-website.us-east-1.amazonaws.com/signin");
        String pageTitle = "React App";
        //driver.navigate().to("www.google.com/");// must use http"
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Assert.assertEquals(driver.getTitle().toString(), pageTitle);
        System.out.println("Open Login page successfully.");
    }

    // Method to validate Log in
    @Test(priority = 2)
    public void validateLogin() throws Exception {

        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("admin@test.com");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("test123");
        java.lang.Thread.sleep(1000);

        driver.findElement(By.xpath("//a[@class='col-login__btn']")).click();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

        Assert.assertTrue(driver.findElement(By.xpath("//a[contains(.,'Parent Portal')]")).isDisplayed());
        System.out.println("Login successfully.");
        java.lang.Thread.sleep(1000);
    }

    // Method to Verify filter Student Access Request with INACTIVE
    @Test(priority = 3)
    public void validateInactiveFilter() throws Exception {

        driver.findElement(By.xpath("//button[@class='btn btn-filter module_grid__btn_filter btn btn-default']")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        // select status Inactive from dropdown list
        driver.findElement(By.xpath("//select[@id='formControlsSelect']/option[@value='inactive']")).click();
        java.lang.Thread.sleep(1000);
        // Click to Apply Filter:
        driver.findElement(By.xpath("//button[@class='btn-filter btn btn-default']")).click();
        java.lang.Thread.sleep(1000);

        //to validate result table only contains INACTIVE item.
        // parentElement contains all results
        WebElement parentElement = driver.findElement(By.cssSelector("tbody"));

        //get list of filter results:
        List<WebElement> childElements = parentElement
                .findElements(By.xpath("//table/tbody/tr/td[2]/div/p"));

        // Checking each item in the result column
        int iSize = childElements.size();
        int testResult = 0;
        for (int i = 0; i < iSize; i++) {
            //if childElement[i] does not contain "Inactive" => fail => return 0
            if (childElements.get(i).getText().contains("Inactive") != true) {
                testResult = 0;
                System.out.println("Inactive Filter does not execute correctly. There is [" + childElements.get(i).getText() + "] item from result table.");
                break;
            } else {
                testResult = 1;
                System.out.println("Item correct ==>> " + childElements.get(i).getText());
            }

        }
        //  Assert resutl: testResult = 1 means all filter result for "Inactive" are correct.
        Assert.assertEquals(String.valueOf(testResult), "1");
        System.out.println("Filter condition has been applied correctly.");

        java.lang.Thread.sleep(3000);
    }

    // Method to validate First Name Descending sort
    @Test(priority = 5)
    public void validateFirstNameDescendingSort() throws Exception {

        // reload the page: ( must login first)
        driver.get("http://ktvn-test.s3-website.us-east-1.amazonaws.com/");

        //click to Sort FIrstName Descending:
        driver.findElement(By.xpath("//table/thead/tr/th[@data-field='firstName']")).click();
        Thread.sleep(2000);

        //get array of Firstname column
        // parentElement contains all results
        WebElement parentElement = driver.findElement(By.cssSelector("tbody"));

        //get list of filter results:
        List<WebElement> childElements = parentElement
                .findElements(By.xpath("//table/tbody/tr/td[6]")); // FIrst Name Column
        List<String> elementFirstName = new ArrayList<String>();

        for (int i = 0; i < childElements.size(); i++) {
            elementFirstName.add(childElements.get(i).getText());
        }
        //System.out.println("-> size of Array Element: " + elementFirstName.size());

        // clone a list of elementFirstName and sort it, then compare with the original list
        List<String> copyOf = new ArrayList<String>(elementFirstName);
        Collections.sort(copyOf); // Sort Ascending
        Collections.reverse(copyOf); // reverse the List because it was sorted by Ascending

        Assert.assertTrue(elementFirstName.equals(copyOf));
        System.out.println("First Name column was sorted by Descending properly."); //the list should be sorted by DESCENDING
        java.lang.Thread.sleep(2000);
    }

    // Method to validate First Name ascending sort
    @Test(priority = 6)
    public void validateFirstNameAscendingSort() throws Exception {

        // reload the page: ( must login first)
        driver.get("http://ktvn-test.s3-website.us-east-1.amazonaws.com/");

        //click to Sort FIrstName Descending:
        driver.findElement(By.xpath("//table/thead/tr/th[@data-field='firstName']")).click();
        Thread.sleep(1000);
        //click again to sort by Ascending
        driver.findElement(By.xpath("//table/thead/tr/th[@data-field='firstName']")).click();

        //get array of Firstname column
        // parentElement contains all results
        WebElement parentElement = driver.findElement(By.cssSelector("tbody"));

        //get list of filter results:
        List<WebElement> childElements = parentElement
                .findElements(By.xpath("//table/tbody/tr/td[6]")); // FIrst Name Column
        List<String> elementFirstName = new ArrayList<String>();

        for (int i = 0; i < childElements.size(); i++) {
            elementFirstName.add(childElements.get(i).getText());
        }
        //System.out.println("-> size of Array Element: " + elementFirstName.size());

        // clone a list of elementFirstName and sort it, then compare with the original list
        List<String> copyOf = new ArrayList<String>(elementFirstName);
        Collections.sort(copyOf); // Sort Ascending

        Assert.assertTrue(elementFirstName.equals(copyOf));
        System.out.println("First Name column was sorted by Ascending properly."); //the list should be sorted by DESCENDING
        java.lang.Thread.sleep(2000);
    }

    // Method for testing API endpoint
    @Test(priority = 7)
    public void validateApiEndpoint() throws Exception {
        RestAssured.baseURI = "https://my-json-server.typicode.com/typicode/demo/posts";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/1");

        // Get the status code from the Response.
        int statusCode = response.getStatusCode();

        // Assert that status code = 200 is returned.
        Assert.assertEquals(statusCode, 200);
        System.out.println("Successfully interact with the web services.");
    }

    // Method to validate filter result via API
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
        // Idea: if there is any record in returned table DOES NOT contain keyword on relevant column, then return 0 => fail.
        for (int i = 0; i < responseStatus.size(); i++) {
            if (responseStatus.get(i).contains(status) == true) {
                if (responseRequesterEmail.get(i).contains(requesterEmail) == true) {
                    if (responseFirstName.get(i).contains(firstName) == true) {
                        if (responseLastName.get(i).contains(lastName) == true) {
                            if (responseStudentDistrictId.get(i).contains(studentDistrictId) == true) {
                                iResut = 1;
                                //System.out.println("First name is: " + responseFirstName.get(i));
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
        //it should return 1 when all matched
        return iResut;
    }

    //@Test(priority = 99)
    public void validateFilterResult() {
        int testResult = this.checkFilterAPI("approved", "test+giapios@karrostech.com", "KIMBER", "MICHALSON", "111318");
        String strResult = String.valueOf(testResult);

        Assert.assertEquals(strResult, "1");
        System.out.println("Filter Success");

    }



}


