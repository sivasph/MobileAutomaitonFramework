package framework.testcases.ios.app.sanity;

import framework.base.TestBase;
import framework.pageobjects.ios.app.OnboardingPageObjects;
import org.testng.annotations.Test;

import java.io.IOException;

public class OnboardingPage extends TestBase {

    @Test (priority = 1)
    public void verifyOnboardingScreenDisplayed() throws IOException {
        OnboardingPageObjects homePageObjects = new OnboardingPageObjects();
        homePageObjects.onBoarding_Agree_Allow_Skip_AreDisplayed();
    }

    @Test (priority = 2)
    public void verifyOnboardingPage_Options() throws Exception {
        OnboardingPageObjects homePageObjects = new OnboardingPageObjects();
        homePageObjects.onBoarding_VerifyLogInNow_SubscribeNow_ContinueAndLogInLaterAreDisplayed();
    }
}
