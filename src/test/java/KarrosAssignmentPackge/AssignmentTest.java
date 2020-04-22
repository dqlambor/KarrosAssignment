package KarrosAssignmentPackge;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

import sun.jvm.hotspot.runtime.Thread;
import sun.jvm.hotspot.runtime.Threads;

import java.security.PrivateKey;
import java.util.List;
import java.util.PriorityQueue;

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
        
    }

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

    @Test(priority = 2)
    public void validateLogin() throws Exception{

        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("admin@test.com");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("test123");
        java.lang.Thread.sleep(1000);

        driver.findElement(By.xpath("//a[@class='col-login__btn']")).click();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

        Assert.assertTrue(driver.findElement(By.xpath("//a[contains(.,'Parent Portal')]")).isDisplayed());
        System.out.println("Login successfully.");
    }

    @Test(priority = 3)
    public void validateOpenFilterWindows() throws Exception {
        // validate the ability to open Filter windows.
        driver.findElement(By.xpath("//button[@class='btn btn-filter module_grid__btn_filter btn btn-default']")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='modal-content']")).isDisplayed());
        java.lang.Thread.sleep(2000);
    }

    @Test(priority = 4)
    public void validateSubmitFilter(){
        // validate the ability to submit Filter.


    }

    @Test(priority = 99)
    public void xPathFinding(){
        WebElement x,y,z;
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
