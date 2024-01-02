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
    WebDriverWait wait;

    public PageBase() {
        this.driver = DriverFactory.getAppiumDriver();
        wait = new WebDriverWait(driver, 10);
    }

    public void clickElement(WebElement element, String elementName) throws IOException {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
        ReportHelper.logTestStepWithScreenshot_Base64(Status.INFO, "clicked on element: "+ elementName);
        System.out.println("clicked on element: "+ elementName);
    }

    public void enterText(WebElement element, String text, String elementName) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.sendKeys(text);
        ReportHelper.logTestStep(Status.INFO, "Entered text into: "+ elementName);
        System.out.println("Entered text into: "+ elementName);
    }
    public boolean isPresent(WebElement element) {
        Boolean result = false;
        try{
            wait.until(ExpectedConditions.visibilityOf(element));
            if(element.isDisplayed()){
                result=true;
            }
        }catch (Exception e){
            System.out.println(element+" not displayed");
        }
        return result;
    }

    public void swipeDown() {
        Map<String, Object> args = new HashMap<>();
        args.put("direction", "down");
        driver.executeScript("mobile: scroll", args);
    }

    public void swipeUp() {
        Map<String, Object> args = new HashMap<>();
        args.put("direction", "up");
        driver.executeScript("mobile: scroll", args);
    }

    public void swipeLeft() {
        Map<String, Object> args = new HashMap<>();
        args.put("direction", "left");
        driver.executeScript("mobile: swipe", args);
    }

    public void swipeRight() {
        Map<String, Object> args = new HashMap<>();
        args.put("direction", "right");
        driver.executeScript("mobile: swipe", args);
    }

    public void swipeToElement(WebElement element, String elementName) throws Exception {
        int maxScrolls = 15; // Adjust the maximum number of scrolls as needed
        int scrollCount = 0;
        System.out.println("Scroll to element: " + elementName);

        while (scrollCount < maxScrolls) {
            if (isPresent(element) && element.isDisplayed()) {
                // Element found and displayed, exit the loop
                break;
            }
            // Perform the scroll
            scrollDown();
            scrollCount++;

            // Log and take a screenshot at specific scroll intervals if needed
            if (scrollCount == 2 || scrollCount == 6) {
                ReportHelper.logTestStepWithScreenshot_Base64(Status.INFO, "Scrolling to element: " + elementName);
            }
        }
        if (scrollCount == maxScrolls) {
            // Element was not found after maximum scrolls
            ReportHelper.logTestStepWithScreenshot_Base64(Status.INFO, "Element not found after scrolling: " + elementName);
        }
    }

    public void swipeToElement_iOS(WebElement element, String elementName) throws Exception {
        int maxScrolls = 15; // Adjust the maximum number of scrolls as needed
        int scrollCount = 0;
        System.out.println("Scroll to element: " + elementName);

        while (scrollCount < maxScrolls) {
            if (isPresent(element) && element.isDisplayed()) {
                // Element found and displayed, exit the loop
                swipeElementToMiddleOfTheScreen(element);
                break;
            }
            // Perform the scroll
            scrollDown();
            scrollCount++;

            // Log and take a screenshot at specific scroll intervals if needed
            if (scrollCount == 2 || scrollCount == 6) {
                ReportHelper.logTestStepWithScreenshot_Base64(Status.INFO, "Scrolling to element: " + elementName);
            }
        }
        if (scrollCount == maxScrolls) {
            // Element was not found after maximum scrolls
            ReportHelper.logTestStepWithScreenshot_Base64(Status.INFO, "Element not found after scrolling: " + elementName);
        }
        Thread.sleep(2000);
    }

    public void swipeElementToMiddleOfTheScreen(WebElement element) throws Exception {
        // Get screen dimensions
        int screenWidth = driver.manage().window().getSize().getWidth();
        System.out.println("screenWidth: "+screenWidth);
        int screenHeight = driver.manage().window().getSize().getHeight();
        System.out.println("screenHeight: "+screenHeight);

        // Calculate the start and end coordinates for the swipe action
        int startX = element.getLocation().getX();
        int endX = screenWidth / 2; // Middle of the screen
        int startY = element.getLocation().getY();
        int endY = screenHeight / 2; // Middle of the screen

        // Create a TouchAction instance and perform the swipe
        TouchAction touchAction = new TouchAction(driver);
        touchAction.longPress(PointOption.point(startX, startY))
                .moveTo(PointOption.point(endX, endY))
                .release()
                .perform();
    }

    // Helper method to scroll down
    public void scrollDown() {
        Dimension size = driver.manage().window().getSize();
        int startX = size.getWidth() / 2;
        int startY = (int) (size.getHeight() * 0.8);
        int endY = (int) (size.getHeight() * 0.2);

        TouchAction touchAction = new TouchAction(driver);
        touchAction.press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500))) // Optional delay
                .moveTo(PointOption.point(startX, endY))
                .release()
                .perform();
    }

    public void scrollDownOneArticle() {
        Dimension size = driver.manage().window().getSize();
        int startX = size.getWidth() / 2;
        int startY = (int) (size.getHeight() * 0.6);
        int endY = (int) (size.getHeight() * 0.3);

        TouchAction touchAction = new TouchAction(driver);
        touchAction.press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500))) // Optional delay
                .moveTo(PointOption.point(startX, endY))
                .release()
                .perform();
    }

    public long[] getTimeDifference(String articleTime) {
        String[] split_day_time;
        String[] split_hours_time;
        String[] split_Hour_Minute;
        String[] split_x_min_ago;
        String time = null;
        String hour;
        String minute;
        String day;
        String amOrPM;

        if(articleTime.contains("min")){
            split_day_time = articleTime.split(" . ");
            split_x_min_ago=split_day_time[0].split(" ");
            minute=split_x_min_ago[0];
            time=split_day_time[1];
        }else if(articleTime.contains("hours")){
            split_hours_time = articleTime.split(" . ");
            hour=split_hours_time[0];
            time=split_hours_time[1];
        }else if(articleTime.contains("Today")){
            split_day_time = articleTime.split(" . ");
            day=split_day_time[0];
            time=split_day_time[1];
        }else if(articleTime.contains("Yesterday")){
            split_day_time = articleTime.split(" . ");
            day=split_day_time[0];
            time=split_day_time[1];
        }else if(articleTime.contains("2023")){
            split_day_time = articleTime.split(",");
            day=split_day_time[0];
            time=split_day_time[1];
        }

        // Parse the input time string to LocalTime
        LocalTime localTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("h:mm a"));

        // Format the time in 24-hour format
        String formattedTime = localTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        split_Hour_Minute=formattedTime.split(":");
        hour=split_Hour_Minute[0];
        minute=split_Hour_Minute[1];

        // Define the start time
        LocalTime startTime = LocalTime.of(Integer.parseInt (hour), Integer.parseInt (minute));

        // Get the current time
        LocalTime currentTime = LocalTime.now();
        System.out.println("Current Time: "+currentTime);

        // Calculate the duration between the start time and current time
        Duration duration = Duration.between(startTime, currentTime);

        // Extract hours and minutes from the duration
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();

        System.out.println("Time difference: " + hours + " hours " + minutes + " minutes");
        long[] result = {hours, minutes};
        return result;
    }

}
