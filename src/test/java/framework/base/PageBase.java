package framework.base;

import com.aventstack.extentreports.Status;
import framework.utilities.DriverFactory;
import framework.utilities.ReportHelper;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageBase {

    protected AppiumDriver driver;

    public PageBase() {
        this.driver = DriverFactory.getAppiumDriver();
    }

    public void clickElement(WebElement element, String elementName) {
        element.click();
        ReportHelper.logTestStep(Status.INFO, "clicked on element: "+ elementName);
        System.out.println("clicked on element: "+ elementName);
    }

    public void enterText(WebElement element, String text, String elementName) {
        element.sendKeys(text);
        ReportHelper.logTestStep(Status.INFO, "Entered text into: "+ elementName);
        System.out.println("Entered text into: "+ elementName);
    }

}
