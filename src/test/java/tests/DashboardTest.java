package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CareersPage;
import pages.DashboardPage;
import utilities.BrowserUtils;

public class DashboardTest extends BaseTest {

    private DashboardPage dashboardPage;
    private CareersPage careersPage;

    @BeforeMethod
    public void setUpPage() {
        dashboardPage = new DashboardPage(driver);
        careersPage = new CareersPage(driver);
    }

    @Test
    public void insiderTest() {
        dashboardPage.verifyOpenHomePage();
        dashboardPage.selectHeaderDropdown("Company", "Careers");
        dashboardPage.verifyOpenCareersPage();
        dashboardPage.navigatePage("https://useinsider.com/careers/quality-assurance/");
        Assert.assertEquals(driver.getTitle(), "Insider quality assurance job opportunities", "verify page title");
        Assert.assertEquals(careersPage.allDepartmentsButton.getText(), "See all QA jobs", "verify the button text");
        careersPage.allDepartmentsButton.click();

        BrowserUtils.scrollToSize(0,100);
        BrowserUtils.waitForClickablility(careersPage.jobContainer, 5);

        BrowserUtils.selectLocation(careersPage.filterByLocation, "Istanbul, Turkey");
        BrowserUtils.selectLocation(careersPage.filterByDepartment, "Quality Assurance");
        careersPage.verifyDepartment("Quality Assurance");
        careersPage.verifyLocation("Istanbul, Turkey");

        careersPage.clickViewRole(0);
        BrowserUtils.switchWindowTab(2);

        BrowserUtils.waitForPageToLoad(5);

        Assert.assertTrue(driver.getCurrentUrl().contains("jobs.lever.co"), "verify the url");
        BrowserUtils.verifyElementDisplayed(careersPage.applyForThisJob);
    }


}
