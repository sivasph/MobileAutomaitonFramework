package framework.pageobjects.ios;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import framework.base.PageBase;
import framework.utilities.ReportHelper;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.io.IOException;

public class OnboardingPageObjects extends PageBase {

    public OnboardingPageObjects() {
        super();
        PageFactory.initElements(driver, this);
    }

    //region Web Elements
    @FindBy(xpath = "(//*[@text='Agree'])[1]")
    public WebElement agree;
    @FindBy(xpath = "//*[@text='Allow']")
    public WebElement allow;
    @FindBy(xpath = "(//*[@text='Skip'])[1]")
    public WebElement skip;
    @FindBy(xpath = "//*[@text='Log in now']")
    public WebElement login_now;
    @FindBy(xpath = "//*[@text='Subscribe now']")
    public WebElement subscribe_now;
    @FindBy(xpath = "//*[@text='Continue and log in later']")
    public WebElement continue_login;

    //endregion Web Elements

    //region Page methods

    public void onBoarding_Agree_Allow_Skip_AreDisplayed() throws IOException {
        try {
            Assert.assertTrue(agree.isDisplayed());
            clickElement(agree, "Agree");
            Assert.assertTrue(allow.isDisplayed());
            Assert.assertTrue(skip.isDisplayed());
            clickElement(skip, "Skip");
            ReportHelper.logTestStepWithScreenshot_Base64(Status.PASS, "Onboarding screen : Agree, allow/skip are displayed");

        } catch (Exception e) {
            ReportHelper.logTestStepWithScreenshot_Base64(Status.FAIL, "Test Failed: " + e.getMessage());

        }
    }
    public void onBoarding_VerifyLogInNow_SubscribeNow_ContinueAndLogInLaterAreDisplayed() throws IOException {
        try {
            Assert.assertTrue(login_now.isDisplayed());
            Assert.assertTrue(subscribe_now.isDisplayed());
            Assert.assertTrue(continue_login.isDisplayed());
            ReportHelper.logTestStepWithScreenshot_Base64(Status.PASS, "Onboarding screen : Agree, allow/skip, Log in now, Subscribe now, Continue and log in later are displayed");

        } catch (Exception e) {
            ReportHelper.logTestStepWithScreenshot_Base64(Status.FAIL, "Test Failed: " + e.getMessage());
        }
    }
    //endregion Page methods
}
