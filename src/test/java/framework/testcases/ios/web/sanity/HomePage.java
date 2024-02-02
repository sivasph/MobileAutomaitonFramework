package framework.testcases.ios.web.sanity;

import framework.base.TestBase;
import framework.pageobjects.ios.web.HomePageObjects;
import org.testng.annotations.Test;

import java.io.IOException;

public class HomePage extends TestBase {

    @Test(priority = 1)
    public void VerifyHomePageLogo() throws IOException {
        HomePageObjects homePageObjects = new HomePageObjects();
        homePageObjects.VerifyHomePageLogo();
    }

    @Test (priority = 2)
    public void VerifyLoginFunctionality() throws Exception {
        HomePageObjects homePageObjects = new HomePageObjects();
        homePageObjects.VerifyLoginFunctionality();
    }
}
