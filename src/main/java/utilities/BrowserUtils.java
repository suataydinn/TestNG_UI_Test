package utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;


public class BrowserUtils {

    public static String getScreenshot(String name) throws IOException {

        String date = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

        TakesScreenshot ts = (TakesScreenshot) DriverManager.get();
        File source = ts.getScreenshotAs(OutputType.FILE);

        String target = System.getProperty("user.dir") + "/test-output/Screenshots/" + name + date + ".png";
        File finalDestination = new File(target);

        FileUtils.copyFile(source, finalDestination);
        return target;
    }

    public static void clearScreenshots() {
        String screenshotPath = System.getProperty("user.dir") + "/test-output/Screenshots";
        File screenshotDir = new File(screenshotPath);

        if (screenshotDir.exists() && screenshotDir.isDirectory()) {
            File[] files = screenshotDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
        }
    }

    public static void hover(WebElement element) {
        Actions actions = new Actions(DriverManager.get());
        actions.moveToElement(element).perform();
    }


    public static void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static boolean waitForVisibilityTitle(String text, int timeToWaitInSec) {
        WebDriverWait wait = new WebDriverWait(DriverManager.get(), Duration.ofSeconds(timeToWaitInSec));
        return wait.until(ExpectedConditions.titleContains(text));
    }

    public static WebElement waitForVisibility(WebElement element, int timeToWaitInSec) {
        WebDriverWait wait = new WebDriverWait(DriverManager.get(), Duration.ofSeconds(timeToWaitInSec));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForVisibility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(DriverManager.get(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }


    public static WebElement waitForClickablility(WebElement element, int timeout) {
            try {
            WebDriverWait wait = new WebDriverWait(DriverManager.get(), Duration.ofSeconds(timeout));
            return wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (TimeoutException e) {
                System.err.println("Element is not clickable within the given timeout: " + timeout + " seconds");
            }
        return element;
    }

    public static WebElement waitForClickablility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(DriverManager.get(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static void waitForPageToLoad(long timeOutInSeconds) {
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.get(), Duration.ofSeconds(timeOutInSeconds));
            wait.until(expectation);
        } catch (Throwable error) {
            error.printStackTrace();
        }
    }


    public static void clickWithJS(WebElement element) {
        ((JavascriptExecutor) DriverManager.get()).executeScript("arguments[0].scrollIntoView(true);", element);
        ((JavascriptExecutor) DriverManager.get()).executeScript("arguments[0].click();", element);
    }

    public static void scrollToElement(WebElement element) {
        ((JavascriptExecutor) DriverManager.get()).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void scrollToSize(int start, int finish) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.get();
        js.executeScript("window.scrollBy("+start+","+finish+")");
        BrowserUtils.waitFor(1);
    }


    public static void scrollToHeader() {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.get();
        js.executeScript("window.scrollTo(0, 0)");
    }


    public static void verifyElementDisplayed(WebElement element) {
        try {
            Assert.assertTrue(element.isDisplayed(), "Element not visible: " + element);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            Assert.fail("Element not found: " + element);

        }
    }


    public static void switchWindowTab (int tabNumber){
        ArrayList<String> tabs = new ArrayList<String> (DriverManager.get().getWindowHandles());
        BrowserUtils.waitFor(2);
        DriverManager.get().switchTo().window(tabs.get(tabNumber-1));
        BrowserUtils.waitFor(2);
    }

    public static void selectLocation(WebElement dropdownElement, String text) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.get();
        js.executeScript(
                "let select2Element = arguments[0]; " +
                        "let option = Array.from(select2Element.options).find(o => o.text === arguments[1]); " +
                        "if (option) { select2Element.value = option.value; select2Element.dispatchEvent(new Event('change')); }",
                dropdownElement, text
        );
        waitFor(1);
    }





}