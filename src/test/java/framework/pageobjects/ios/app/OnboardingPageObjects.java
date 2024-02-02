package framework.pageobjects.ios.app;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import framework.base.PageBase;
import framework.utilities.ReportHelper;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.io.IOException;

public class OnboardingPageObjects extends PageBase {

    SoftAssert softAssert;
    public OnboardingPageObjects() {
        super();
        PageFactory.initElements(driver, this);
        softAssert = new SoftAssert();
    }

    //region Web Elements
    @FindBy(xpath = "//*[@name='Agree']")
    public WebElement agree;
    @FindBy(xpath = "//*[@name='Allow']")
    public WebElement allow;
    @FindBy(xpath = "//*[@name='Skip']")
    public WebElement skip;
    @FindBy(xpath = "//*[@name='Log in now']")
    public WebElement login_now;
    @FindBy(xpath = "//*[@name='Subscribe now']")
    public WebElement subscribe_now;
    @FindBy(xpath = "//*[@name='Continue and log in later']")
    public WebElement continue_login;
    @FindBy(xpath = "//*[@name='close-button']")
    public WebElement adClose;

    //endregion Web Elements

    //region Page methods

    public void onBoarding_Agree_Allow_Skip_AreDisplayed() throws IOException {
        try {
            softAssert.assertTrue(agree.isDisplayed(), "Agree not displayed");
            clickElement(agree, "Agree");
            softAssert.assertTrue(allow.isDisplayed(),"Allow not displayed");
            softAssert.assertTrue(skip.isDisplayed(),"Skip not displayed");
            clickElement(skip, "Skip");
            softAssert.assertAll();
            ReportHelper.logTestStepWithScreenshot_Base64(Status.PASS, "Onboarding screen : Agree, allow/skip are displayed");

        } catch (Exception e) {
            ReportHelper.logTestStepWithScreenshot_Base64(Status.FAIL, "Test Failed: " + e.getMessage());
        }
    }
    public void onBoarding_VerifyLogInNow_SubscribeNow_ContinueAndLogInLaterAreDisplayed() throws IOException {
        try {
            if(isPresent(adClose)){
                clickElement(adClose, "Ad close");
            }
            softAssert.assertTrue(login_now.isDisplayed(), "Login now is not displayed");
            softAssert.assertTrue(subscribe_now.isDisplayed(), "Subscribe now is not displayed");
            softAssert.assertTrue(continue_login.isDisplayed(), "Continue login later is not displayed");
            softAssert.assertAll();
            ReportHelper.logTestStepWithScreenshot_Base64(Status.PASS, "Onboarding screen : Agree, allow/skip, Log in now, Subscribe now, Continue and log in later are displayed");

        } catch (Exception e) {
            ReportHelper.logTestStepWithScreenshot_Base64(Status.FAIL, "Test Failed: " + e.getMessage());
        }
    }
    //endregion Page methods
}
