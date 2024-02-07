package framework.utilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileBrowserType;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Base64;
import java.util.Calendar;

public class DriverFactory {

    private static AppiumDriver appiumDriver;
    private static ThreadLocal<AppiumDriver> appiumDriverThreadLocal = new ThreadLocal<>();
    public static AppiumDriver getAppiumDriver() {
        return appiumDriverThreadLocal.get();
    }

    public static void setAppiumDriver(String udid, String locality, String url, String appType,String platformName) throws IOException {

        TestData data = TestData.TestDataLoader.loadTestData();
        System.out.println("platformName " + platformName);
        System.out.println("appType " + appType);
        String projectName = data.getProjectName();
        String installApp = data.getInstallApp();
        String appName = data.getAndroid_AppName();
        String appPackage = data.getAndroid_AppPackage();
        String appActivity = data.getAndroid_AppActivity();
        String bundleID = data.getiOS_BundleID();
        if (locality.equalsIgnoreCase("Cloud")) {
            System.out.println("Mobile Cloud Execution Started");
            System.out.println("Mobile Cloud URL --"+url);
            System.out.println("Device SN / UDID --"+udid);
            String accessKey = data.getAccessKey();
            if (platformName.equalsIgnoreCase("Android")&& (appType.equalsIgnoreCase("Native"))) {
                System.out.println("Android Cloud Device");
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setCapability("accessKey", accessKey);

                if (udid=="ANY"){ capabilities.setCapability("deviceQuery", "@os='android' and @category='PHONE'");
                }
                else{
                    capabilities.setCapability("deviceQuery", "@serialNumber='"+udid+"'");
                }
                if (installApp.equalsIgnoreCase("Y")) {
                    System.out.println("Installing the app");
                    capabilities.setCapability("appium:app","cloud:"+appName);
                }
                capabilities.setCapability("appPackage", appPackage);
                capabilities.setCapability("appActivity", appActivity);
                capabilities.setCapability("fullReset", true);
                capabilities.setCapability("accessKey", accessKey);
                capabilities.setCapability("instrumentApp", "false");
                capabilities.setCapability("testName", projectName);
                capabilities.setCapability("enforceXPath1", true);
                appiumDriver = new AndroidDriver(new URL(url), capabilities);
            }
            else if ((platformName.equalsIgnoreCase("Android")) && (appType.equalsIgnoreCase("Web"))) {
                System.out.println("Android Browser");
                DesiredCapabilities cap = new DesiredCapabilities();

                cap.setBrowserName(MobileBrowserType.CHROME);
                cap.setCapability("accessKey", accessKey);
                cap.setCapability("deviceQuery", "@serialNumber='"+udid+"'");
                cap.setCapability("enforceXPath1", true);
                cap.setCapability("testName", projectName);
                appiumDriver = new AndroidDriver(new URL(url), cap);
                appiumDriver.get(data.getURL());
            }
            else if (platformName.equalsIgnoreCase("iOS")&& (appType.equalsIgnoreCase("Native"))) {
                System.out.println("Entering into ios");
                DesiredCapabilities capabilities = new DesiredCapabilities();

                capabilities.setCapability("deviceQuery", "@serialNumber='"+udid+"'");
                if (installApp.equalsIgnoreCase("Y")) {
                    System.out.println("Installing the app");
                    capabilities.setCapability("appium:app","cloud:"+ bundleID);
                }
                capabilities.setCapability("appPackage", bundleID);
                capabilities.setCapability("fullReset", true);
                capabilities.setCapability("accessKey", accessKey);
                capabilities.setCapability("instrumentApp", "false");
                capabilities.setCapability("enforceXPath1", true);
                capabilities.setCapability("testName", projectName);
                appiumDriver = new IOSDriver(new URL(url), capabilities);
            }
            else if ((platformName.equalsIgnoreCase("iOS")) && (appType.equalsIgnoreCase("Web"))) {
                System.out.println("iOS Browser");
                DesiredCapabilities cap = new DesiredCapabilities();

                cap.setBrowserName(MobileBrowserType.SAFARI);
                cap.setCapability("accessKey", accessKey);
                cap.setCapability("deviceQuery", "@serialNumber='"+udid+"'");
                cap.setCapability("enforceXPath1", true);
                cap.setCapability("testName", projectName);
                appiumDriver = new IOSDriver(new URL(url), cap);
                appiumDriver.get(data.getURL());
                appiumDriver.manage().deleteAllCookies();
            }
            appiumDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            appiumDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));

        }
        appiumDriverThreadLocal.set(appiumDriver);
    }

    public static void quitAppiumDriver() {
        if (appiumDriverThreadLocal.get() != null) {
            appiumDriverThreadLocal.get().quit();
            appiumDriverThreadLocal.remove();
        }
    }

    public static String takeScreenShot() throws IOException {
        Calendar cal = Calendar.getInstance();
        long s = cal.getTimeInMillis();
        File screen = null;
        try {
            screen = (File) ((TakesScreenshot) appiumDriverThreadLocal.get()).getScreenshotAs(OutputType.FILE);

            FileUtils.copyFile(screen,
                    new File("ReportGenerator/" + ReportHelper.threadLocalReportFolder.get() + "/Screenshots/image" + s + ".png"));
        } catch (Exception e) {
            System.out.println(e);
        }

        return  "./Screenshots//image" + s + ".png";

    }

    // Method to capture screenshot as base64
    public static String captureScreenshotAsBase64() {
        String base64String = "";
        try {
            // Capture screenshot as byte array
            byte[] screenshot = ((TakesScreenshot) appiumDriverThreadLocal.get()).getScreenshotAs(OutputType.BYTES);

            // Convert byte array to base64
            base64String = Base64.getEncoder().encodeToString(screenshot);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return base64String;
    }
}
