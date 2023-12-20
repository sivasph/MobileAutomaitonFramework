package framework.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportHelper {

    private static ThreadLocal<ExtentReports> extent = new ThreadLocal<>();
    static ThreadLocal<String> threadLocalReportFolder = new ThreadLocal<>();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static void initializeExtentReport(String reportName) throws IOException {
        TestData data = TestData.TestDataLoader.loadTestData();
        ExtentReports extentInstance = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter(getReportFilePath(reportName));
        spark.config().thumbnailForBase64();
        spark.config().setReportName(data.getProjectName()+" Automation Test Report");
        spark.config().setDocumentTitle("Automation Report");
        spark.config().setTheme(Theme.DARK);
        extentInstance.setSystemInfo("OS", "");
        extentInstance.setSystemInfo("Execution Environment", "");
        extentInstance.setSystemInfo("Device Name", "");
        extentInstance.setSystemInfo("Device UDID", "");
        extentInstance.setSystemInfo("Application Build", "");
        extentInstance.attachReporter(spark);
        extent.set(extentInstance);
    }

    private static String getReportFilePath(String reportName) throws IOException {
        SimpleDateFormat sdfDateReport = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date now = new Date();
        TestData data = TestData.TestDataLoader.loadTestData();
        String reportFolder = data.getProjectName()+"_" + sdfDateReport.format(now);
        threadLocalReportFolder.set(reportFolder);
        return new File("ReportGenerator", threadLocalReportFolder.get() + "/" + reportName + "_" + sdfDateReport.format(now) + ".html").getPath();
    }

    public static synchronized void createTest(String testName, String udid) {
        ExtentTest extentTest = extent.get().createTest(testName, udid);
        test.set(extentTest);
    }

    public static synchronized void logTestStep(Status status, String log) {
        test.get().log(status, log);
    }
    public static synchronized void logTestStepWithScreenshot(Status status, String log) throws IOException {
        test.get().log(status, log, MediaEntityBuilder.createScreenCaptureFromPath(DriverFactory.takeScreenShot()).build());
    }
    public static synchronized void logTestStepWithScreenshot_Base64(Status status, String log) throws IOException {
        test.get().log(status, log, MediaEntityBuilder.createScreenCaptureFromBase64String(DriverFactory.captureScreenshotAsBase64()).build());
    }

    public static synchronized void flushReport() {
        extent.get().flush();
    }
}