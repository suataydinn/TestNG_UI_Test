package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import utilities.BrowserUtils;
import utilities.ConfigurationReader;
import utilities.DriverManager;

import java.io.IOException;
import java.time.Duration;

public class BaseTest {

    protected ExtentReports report;
    //this class is used to create HTML report file
    protected ExtentHtmlReporter htmlReporter;
    //this will  define a test, enables adding logs, authors, test steps
    protected ExtentTest extentLogger;

    protected WebDriver driver;

    @BeforeTest
    public void setUpTest(){

        BrowserUtils.clearScreenshots();

        report = new ExtentReports();

        String projectPath = System.getProperty("user.dir");
        String path = projectPath + "/test-output/report.html";

        htmlReporter = new ExtentHtmlReporter(path);

        report.attachReporter(htmlReporter);

        htmlReporter.config().setReportName("Smoke Test");

        report.setSystemInfo("Browser", ConfigurationReader.getProperty("browser"));

    }

    @BeforeMethod
    public void setUp() {
        driver = DriverManager.get();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        driver.get(ConfigurationReader.getProperty("url"));
        driver.manage().window().maximize();
        BrowserUtils.waitForPageToLoad(5);

     //   extentLogger = report.createTest(getClass().getSimpleName() + " - " + new Object() {}.getClass().getEnclosingMethod().getName());

      //  String testName = this.getClass().getSimpleName() + " - " + method.getName();
        String testName = this.getClass().getSimpleName() + " - " + new Object() {}.getClass().getEnclosingMethod().getName();

        extentLogger = report.createTest(testName);

        if(driver.findElements(By.cssSelector("a#wt-cli-accept-all-btn")).size()>0){
            driver.findElement(By.cssSelector("a#wt-cli-accept-all-btn")).click();
        }

        if(driver.findElements(By.cssSelector("span.ins-close-button")).size()>0){
            driver.findElement(By.cssSelector("span.ins-close-button")).click();
        }


    }


    @AfterMethod
    public void tearDown(ITestResult result) throws InterruptedException, IOException {
        try {
            if (result.getStatus() == ITestResult.FAILURE) {
                extentLogger.fail(result.getName());
                String screenShotPath = utilities.BrowserUtils.getScreenshot(result.getName());
                extentLogger.addScreenCaptureFromPath(screenShotPath);
                extentLogger.fail(result.getThrowable());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Thread.sleep(2000);
            if (driver != null) {
                DriverManager.closeDriver();
            }
        }
    }

    @AfterTest
    public void tearDownTest(){

        //this is when the report is actually created
        report.flush();
    }

}
