package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utilities.BrowserUtils;
import utilities.ConfigurationReader;

import java.util.ArrayList;
import java.util.List;

public class DashboardPage {

    @FindBy(css = "i.icon-arrow-right.location-slider-next")
    public WebElement rightArrowButton;

    @FindBy(xpath = "//div[starts-with(@class, 'job-title')]")
    public List <WebElement> jobTitles;

    @FindBy(css = "div.location-info")
    public List <WebElement> locationInfo;

    @FindBy(xpath = "//div[@data-id='fe38935']")
    public WebElement block;

    private WebDriver driver;

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    public void navigatePage(String url){
        driver.get(url);
    }

    public void verifyOpenHomePage(){
        Assert.assertEquals(driver.getTitle(), "#1 Leader in Individualized, Cross-Channel CX — Insider", "verify page title");
        Assert.assertEquals(driver.getCurrentUrl(), ConfigurationReader.getProperty("url"), "verify the url");
    }

    public void verifyOpenCareersPage(){
        Assert.assertEquals(driver.getTitle(), "Ready to disrupt? | Insider Careers", "verify page title");
        Assert.assertEquals(driver.getCurrentUrl(), "https://useinsider.com/careers/", "verify the url");


        Assert.assertTrue(jobTitles.size() > 0, "verify there are job titles");

        BrowserUtils.clickWithJS(rightArrowButton);
        List<String> expectedLocations = List.of(
                "New York US",
                "Sao Paulo Brazil",
                "London United Kingdom",
                "Paris France",
                "Amsterdam Netherlands"
        );

        List<String> actualLocations = new ArrayList<>();

        for (WebElement element : locationInfo) {
            String[] lines = element.getText().split("\\n");
            String combinedText = String.join(" ", lines);
            actualLocations.add(combinedText);
        }

        for (String expected : expectedLocations) {
            Assert.assertTrue(actualLocations.contains(expected),
                    "Expected location not found: " + expected);
        }


        BrowserUtils.verifyElementDisplayed(block);

        List<WebElement> elements1 = driver.findElements(By.cssSelector("h2"));
        List<String> h2Title = new ArrayList<>();

        for (WebElement element1 : elements1) {
            h2Title.add(element1.getText());
        }
        Assert.assertTrue(h2Title.contains("Life at Insider"));

    }

    public void selectHeaderDropdown(String dropdown, String subDropdown){
        driver.findElement(By.xpath("//a[contains(text(), '" + dropdown + "')]")).click();
        driver.findElement(By.xpath("//a[contains(text(), '" + subDropdown + "')]")).click();
    }


}
