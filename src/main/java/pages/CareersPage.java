package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utilities.BrowserUtils;

import java.time.Duration;
import java.util.List;

public class CareersPage {

    @FindBy(xpath = "//a[contains(@href, 'https://useinsider.com/careers/open-positions/?department=')]")
    public WebElement allDepartmentsButton;

    @FindBy(css = "p.position-title")
    public List <WebElement> titlePosition;

    @FindBy(css = "span.position-department")
    public List <WebElement> positionDepartment;

    @FindBy(css = "div.position-location")
    public List <WebElement> positionLocation;

    @FindBy(css = "#filter-by-department")
    public WebElement filterByDepartment;

    @FindBy(css = "#filter-by-location")
    public WebElement filterByLocation;

    @FindBy(xpath = "//a[text()='Apply for this job']")
    public WebElement applyForThisJob;

    @FindBy(xpath = "//a[text()='View Role']")
    public WebElement viewRole;

    @FindBy(css = "#deneme")
    public WebElement jobContainer;

    private WebDriver driver;

    public CareersPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void verifyLocation(String location){
        for (WebElement element : positionLocation) {
            Assert.assertEquals(element.getText(), location);
        }
    }

    public void verifyDepartment(String department){
        for (WebElement element : positionDepartment) {
            Assert.assertEquals(element.getText(), department);
        }
    }

    public void clickViewRole(int index){
        BrowserUtils.scrollToSize(0, 500);
        BrowserUtils.waitFor(1);
        viewRole.click();
    }

}
