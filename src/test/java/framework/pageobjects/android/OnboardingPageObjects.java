package framework.pageobjects.android;

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
    @FindBy(xpath="//*[@text='AGREE']")
    private WebElement agree;
    @FindBy(xpath="//*[@text='Allow']")
    private WebElement allow;
    @FindBy(xpath="//*[@text='Skip']")
    private WebElement skip;
    @FindBy(xpath="//*[@text='Continue and log in later']")
    private WebElement loginLater;
    @FindBy(xpath="//*[@text='Subscribe now']")
    private WebElement subscribeNowOnboardingPage;
    @FindBy(xpath="//*[@text='Log in now']")
    private WebElement OnboardLogin;
    @FindBy(id="close_button")
    private WebElement close_login_form;
    @FindBy(xpath="//*[@text='Got it!']")
    private WebElement gotit;
    @FindBy(xpath="//*[@text='NO, THANKS']")
    private WebElement optionContinue;
    @FindBy(xpath="//*[@contentDescription='Dismiss update dialogue']")
    private WebElement dismissUpdate;
    @FindBy(id="splash_sph_logo")
    private WebElement sphmedialogo;

    //endregion Web Elements

    //region Page methods
    public void SplashPage_VerifySPHMediaLogoIcon() throws IOException {
        try {
            Assert.assertTrue(sphmedialogo.isDisplayed(), "WebElement is not displayed.");
            ReportHelper.logTestStepWithScreenshot_Base64(Status.PASS, " Splash Page :Ensure Launcher icon display as SPH media");
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            ReportHelper.logTestStepWithScreenshot_Base64(Status.FAIL, e.getMessage());
        }
    }

    public void  VerifySplashPageOptions() throws Exception {
        try{
            clickElement(agree,"Agree");
            clickElement(skip,"Skip");
            Assert.assertTrue(OnboardLogin.isDisplayed());
            Assert.assertTrue(subscribeNowOnboardingPage.isDisplayed());
            Assert.assertTrue(loginLater.isDisplayed());
            ReportHelper.logTestStepWithScreenshot_Base64(Status.PASS, "Splash Page shows option to Login Now, Subscribe Now and Continue to Logon");
            clickElement(OnboardLogin,"Login now");
            Thread.sleep(2000);
            Assert.assertTrue(close_login_form.isDisplayed());
            ReportHelper.logTestStepWithScreenshot_Base64(Status.PASS, "Login now takes to mySPH login page");
            clickElement(close_login_form,"Back");

        }catch(AssertionError e) {
            System.out.println(e.getMessage());
            ReportHelper.logTestStepWithScreenshot_Base64(Status.FAIL, e.getMessage());
            clickElement(close_login_form,"Back");
        }
    }
    //endregion Page methods
}
