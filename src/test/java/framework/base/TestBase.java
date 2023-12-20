package framework.base;

import com.aventstack.extentreports.Status;
import framework.utilities.DriverFactory;
import framework.utilities.ReportHelper;
import framework.utilities.TestData;
import io.appium.java_client.AppiumDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;

public class TestBase {
    protected AppiumDriver driver;
    String udid;

    @BeforeClass
    public void setUpClass(ITestContext context) throws IOException {
        // Initialize the driver and other setup (e.g., Extent Reports setup)
        String browser = context.getCurrentXmlTest().getParameter("browser");
        udid = context.getCurrentXmlTest().getParameter("udid");
        String toolName = context.getCurrentXmlTest().getParameter("toolName");
        String locality = context.getCurrentXmlTest().getParameter("locality");
        String executionType = context.getCurrentXmlTest().getParameter("executionType");
        String platformName = context.getCurrentXmlTest().getParameter("platformName");
        String appType = context.getCurrentXmlTest().getParameter("appType");
        String testType = context.getCurrentXmlTest().getParameter("testType");
        String testClass =context.getCurrentXmlTest().getClasses().get(0).getName();
        TestData data = TestData.TestDataLoader.loadTestData();
        String cloudUrl = data.getCloudURL();

        System.out.println("Before Suite Started");

        if(testClass.contains("android") && testType.equalsIgnoreCase("sanity")){
            ReportHelper.initializeExtentReport(data.getProjectName()+"_Android_Sanity_"+udid);
        } else if (testClass.contains("android")&& testType.equalsIgnoreCase("regression")) {
            ReportHelper.initializeExtentReport(data.getProjectName()+"_Android_Regression_"+udid);
        }
        else if (testClass.contains("ios")&& testType.equalsIgnoreCase("sanity")) {
            ReportHelper.initializeExtentReport(data.getProjectName()+"_iOS_Sanity_"+udid);
        }
        else if (testClass.contains("ios")&& testType.equalsIgnoreCase("regression")) {
            ReportHelper.initializeExtentReport(data.getProjectName()+"_iOS_Regression_"+udid);
        }else if (testClass.contains("web")&& testType.equalsIgnoreCase("sanity")) {
            ReportHelper.initializeExtentReport(data.getProjectName()+"_Web_Sanity_"+udid);
        }else if (testClass.contains("web")&& testType.equalsIgnoreCase("regression")) {
            ReportHelper.initializeExtentReport(data.getProjectName()+"_Web_Regression_"+udid);
        }
        DriverFactory.setAppiumDriver(driver, udid, locality, cloudUrl, appType, platformName);
    }
    @BeforeMethod
    public void setUpTest(ITestResult result, Method method) {
        System.out.println("Before Method Started");
        ReportHelper.createTest(result.getTestClass().getRealClass().getSimpleName()+ " >>>>>>>> "+method.getName(),udid);
        System.out.println("Class: "+result.getTestClass().getRealClass().getSimpleName()+" >> Test Method "+method.getName()+ "  is Starting ");
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        System.out.println("@ After Method Started");
        if (result.getStatus() == ITestResult.FAILURE){
            ReportHelper.logTestStep(Status.FAIL, result.getThrowable().getMessage());
        } else if (result.getStatus() == ITestResult.SKIP) {
            ReportHelper.logTestStep(Status.SKIP, "Test skipped " + result.getThrowable());
        }
    }

    @AfterClass
    public void afterClass() {
        ReportHelper.flushReport();
        DriverFactory.getAppiumDriver().closeApp();
    }

}
