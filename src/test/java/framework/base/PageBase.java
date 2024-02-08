package framework.base;

import com.aventstack.extentreports.Status;
import com.google.common.collect.ImmutableList;
import framework.utilities.DriverFactory;
import framework.utilities.ReportHelper;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class PageBase {

    protected AppiumDriver driver;
    WebDriverWait wait;

    public PageBase() {
        this.driver = DriverFactory.getAppiumDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void clickElement(WebElement element, String elementName) throws IOException, IOException {
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
        TouchAction touchAction = new TouchAction((PerformsTouchActions) driver);
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

        TouchAction touchAction = new TouchAction((PerformsTouchActions) driver);
        touchAction.press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500))) // Optional delay
                .moveTo(PointOption.point(startX, endY))
                .release()
                .perform();
    }

    public void scrollToElement_MobileWeb(String pageDirection, double scrollRation, WebElement element, String elementName) throws Exception {
        int maxScrolls = 15; // Adjust the maximum number of scrolls as needed
        int scrollCount = 1;
        System.out.println("Scroll to element: " + elementName);

        while (scrollCount < maxScrolls) {
            if (isPresent(element)) {
                // Element found and displayed, exit the loop
                break;
            }
            // Perform the scroll
            scroll(pageDirection,scrollRation);
            System.out.println("Scrolling for "+scrollCount+" Time");
            scrollCount++;

            // Log and take a screenshot at specific scroll intervals if needed
            if (scrollCount == 2 || scrollCount == 6) {
                ReportHelper.logTestStepWithScreenshot_Base64(Status.INFO, "Scrolling to element: " + elementName);
            }
        }
        if (scrollCount == maxScrolls) {
            // Element was not found after maximum scrolls
            ReportHelper.logTestStepWithScreenshot_Base64(Status.INFO, "Element not found after scrolling for "+ maxScrolls+" times" + elementName);
        }
        Thread.sleep(2000);
    }

    public void scrollDownOneArticle() {
        Dimension size = driver.manage().window().getSize();
        int startX = size.getWidth() / 2;
        int startY = (int) (size.getHeight() * 0.6);
        int endY = (int) (size.getHeight() * 0.3);

        TouchAction touchAction = new TouchAction((PerformsTouchActions) driver);
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

    private static final String ALPHANUMERIC_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final String ALPHA_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateAlphanumericString(int length) {
        StringBuilder alphanumericString = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = RANDOM.nextInt(ALPHANUMERIC_CHARACTERS.length());
            char randomChar = ALPHANUMERIC_CHARACTERS.charAt(randomIndex);
            alphanumericString.append(randomChar);
        }

        return alphanumericString.toString();
    }
    public static String generateAlphaRandomString(int length) {
        StringBuilder alphanumericString = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = RANDOM.nextInt(ALPHA_CHARACTERS.length());
            char randomChar = ALPHA_CHARACTERS.charAt(randomIndex);
            alphanumericString.append(randomChar);
        }

        return alphanumericString.toString();
    }

    public void swipe(Point start, Point end, Duration duration) {
        PointerInput input = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence sequence = new Sequence(input, 0);
        sequence.addAction(input.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), start.x, start.y));
        sequence.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        sequence.addAction(input.createPointerMove(duration, PointerInput.Origin.viewport(), end.x, end.y));
        sequence.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        DriverFactory.getAppiumDriver().perform(ImmutableList.of(sequence));
    }


    /*
     *
     * If scrollRatio = 0.8 then page will scroll more
     * If scrollRatio = 0.2 then page will scroll very less
     *
     * If user want to scroll page in DOWN direction
     * Then scroll mobile screen starting from Bottom to Top
     *
     * If user want to scroll page in RIGHT direction
     * Then scroll mobile screen starting from Right to Left
     *
     * Assume Screen size = 50(x value) by 100(y value)
     * midpoint of screen will be 50*0.5 & 100*0.5 i.e. (25,50)
     *
     */
    public void scroll(String pageDirection, double scrollRatio) {
        Duration SCROLL_DUR = Duration.ofMillis(200);
        if (scrollRatio < 0 || scrollRatio > 1) {
            throw new Error("Scroll Ratio must be between 0 and 1");
        }

        Dimension size = DriverFactory.getAppiumDriver().manage().window().getSize();
        System.out.println("Screen Size = " + size);
        Point midPoint = new Point((int) (size.width * 0.5), (int) (size.height * 0.5));
        int bottom = midPoint.y + (int) (midPoint.y * scrollRatio); // 50 + 25
        int top = midPoint.y - (int) (midPoint.y * scrollRatio); // 50 - 25
        int left = midPoint.x - (int) (midPoint.x * scrollRatio); // 25 - 12.5
        int right = midPoint.x + (int) (midPoint.x * scrollRatio); // 25 + 12.5


        if (pageDirection.equalsIgnoreCase("UP")) {
            //Swipe Top to bottom, Page will go UP
            swipe(new Point(midPoint.x, top), new Point(midPoint.x, bottom), SCROLL_DUR);
        } else if (pageDirection.equalsIgnoreCase("DOWN")) {
            swipe(new Point(midPoint.x, bottom), new Point(midPoint.x, top), SCROLL_DUR);
        } else if (pageDirection.equalsIgnoreCase("LEFT")) {
            swipe(new Point(left, midPoint.y), new Point(right, midPoint.y), SCROLL_DUR);
        } else {
            //RIGHT
            swipe(new Point(right, midPoint.y), new Point(left, midPoint.y), SCROLL_DUR);
        }
    }

    /*
     * finger : unique id, can be any name
     *
     *
     */
    public void longPress(WebElement ele) {
        Point location = ele.getLocation();
        PointerInput input = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence sequence = new Sequence(input, 0);
        sequence.addAction(input.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), location.x, location.y));
        sequence.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        sequence.addAction(input.createPointerMove(Duration.ofSeconds(1), PointerInput.Origin.viewport(), location.x, location.y));
        sequence.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        DriverFactory.getAppiumDriver().perform(ImmutableList.of(sequence));
    }

    public void swipeBasedOnElementSize(WebElement element, String direction) {
        Dimension size = element.getSize();
        Point location = element.getLocation();

        int startX = location.getX() + size.getWidth() /2 ;
        int startY = location.getY() + size.getHeight() / 2;
        System.out.println("startX"+startX);
        System.out.println("startY"+startY);
        int endX = startX/3;
        int endY = startY/3;

        switch (direction.toUpperCase()) {
            case "UP":
                endY -= size.getHeight() / 2;
                System.out.println("endY - "+endY);
                break;
            case "DOWN":
                endY += size.getHeight() / 2;
                System.out.println("endY + "+endY);
                break;
            case "LEFT":
                endX -= size.getWidth() / 2;
                System.out.println("endX - "+endX);
                break;
            case "RIGHT":
                endX += size.getWidth() / 2;
                System.out.println("endX + "+endX);
                break;
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }

        new TouchAction((PerformsTouchActions) driver)
                .longPress(LongPressOptions.longPressOptions().withPosition(PointOption.point(startX, startY)).withDuration(Duration.ofMillis(500)))
                .moveTo(PointOption.point(endX, endY))
                .release()
                .perform();
    }
}
