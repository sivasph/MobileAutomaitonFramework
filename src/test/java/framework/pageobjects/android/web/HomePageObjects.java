package framework.pageobjects.android.web;

import com.aventstack.extentreports.Status;
import framework.base.PageBase;
import framework.utilities.ReportHelper;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.io.IOException;

public class HomePageObjects extends PageBase {
    public HomePageObjects() {
        super();
        PageFactory.initElements(driver, this);
    }
    //region Web Elements
    @FindBy(xpath="(//a[@title='The Business Times']/img)[2]")
    private WebElement bt_Logo;
    @FindBy(xpath="(//button[@aria-label='Search'])[1]")
    private WebElement search;
    @FindBy(xpath="(//*[contains(text(),'Login')])[2]")
    private WebElement login;
    @FindBy(xpath="//*[@class='mysphclose']")
    private WebElement close_login_form;

    //endregion Web Elements

    //region Page methods
    public void VerifyHomePageLogo() throws IOException {
        try {
            Assert.assertTrue(bt_Logo.isDisplayed(), "Logo is not displayed.");
            ReportHelper.logTestStepWithScreenshot_Base64(Status.PASS, "Logo displayed");
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            ReportHelper.logTestStepWithScreenshot_Base64(Status.FAIL, e.getMessage());
        }
    }

    public void  VerifyLoginFunctionality() throws Exception {
        try{
            clickElement(login,"login");
            Assert.assertTrue(close_login_form.isDisplayed());
            ReportHelper.logTestStepWithScreenshot_Base64(Status.PASS, "Login now takes to mySPH login page");
            clickElement(close_login_form,"Login form close");

        }catch(AssertionError e) {
            System.out.println(e.getMessage());
            ReportHelper.logTestStepWithScreenshot_Base64(Status.FAIL, e.getMessage());
            clickElement(close_login_form,"Login form close");
        }
    }
    //endregion Page methods
}
