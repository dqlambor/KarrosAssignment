package KarrosAssignmentPackge;

import org.openqa.selenium.WebDriver;
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
    @Test
    public void startFireFoxDriver() throws Exception{
        //ChromeOptions options = new ChromeOptions();
        //options.addArguments(new String[]{"--disable-notifications"});
        //System.setProperty("webdriver.chrome.driver", "/Usr/local/bin/chromedriver");

        driver.navigate().to("https://www.google.com/");

        //driver.navigate().to("www.google.com/");// must use http"
        //java.lang.Thread.sleep(10000);
        Assert.assertTrue(driver.getTitle().startsWith("Google"));
        java.lang.Thread.sleep(2000);
    }

}
