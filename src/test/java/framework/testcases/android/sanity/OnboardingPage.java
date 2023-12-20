package framework.testcases.android.sanity;

import framework.base.TestBase;
import framework.pageobjects.android.OnboardingPageObjects;
import org.testng.annotations.Test;

import java.io.IOException;

public class OnboardingPage extends TestBase {

    @Test (priority = 1)
    public void verifyOnboardingPage_SPHLogo() throws IOException {
        OnboardingPageObjects homePageObjects = new OnboardingPageObjects();
        homePageObjects.SplashPage_VerifySPHMediaLogoIcon();
    }

    @Test (priority = 2)
    public void verifyOnboardingPage_Options() throws Exception {
        OnboardingPageObjects homePageObjects = new OnboardingPageObjects();
        homePageObjects.VerifySplashPageOptions();
    }
}
