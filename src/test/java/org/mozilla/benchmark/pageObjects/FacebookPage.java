package org.mozilla.benchmark.pageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mozilla.benchmark.utils.BasePage;
import org.mozilla.benchmark.utils.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.File;

/**
 * Created by andrei.filip on 10/30/2017.
 */
public class FacebookPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(FacebookPage.class.getName());
    private int runs;

    private By USER_NAME = By.id("email");
    private By USER_PASSWORD = By.id("pass");
    private By LOGIN_BUTTON = By.id("loginbutton");
    private By GROUP_AUTOMATION = By.className("_5afe");
    private By HOME_BUTTON = By.cssSelector("a[href*='https://www.facebook.com/?ref=tn_tnmn']");
    private By FALLBACK_FEED = By.id("fallback_feed");

    public FacebookPage(int runs) {
        this.runs = runs;
    }

    public void navigateToHomePage() {
        logger.info("Accessing Facebook ...");
        navigateToURL(Constants.PageObjects.FACEBOOK_URL);
    }

    public void login() {
        captureElementScreenshot(USER_NAME, Constants.Paths.PATTERNS_PATH + File.separator + "facebook" + File.separator + "zero2.png");
        captureElementScreenshot(USER_PASSWORD, Constants.Paths.PATTERNS_PATH + File.separator + "facebook" + File.separator + "zero3.png");
        sendKeys(USER_NAME, Constants.PageObjects.FACEBOOK_USER_NAME);
        sendKeys(USER_PASSWORD, Constants.PageObjects.FACEBOOK_PASS);
        click(LOGIN_BUTTON);
    }


    public void accessGroup() {
        WebElement group = getElements(GROUP_AUTOMATION).get(3);
        captureElementScreenshot(group, Constants.Paths.PATTERNS_PATH + File.separator + "facebook" + File.separator + "zero4.png");
        click(group);
    }

    public void homeLink() {
        captureElementScreenshot(HOME_BUTTON, Constants.Paths.PATTERNS_PATH + File.separator + "facebook" + File.separator + "zero5.png");
        click(HOME_BUTTON);
    }

    public void accessUser() {
        WebElement feed = getElement(FALLBACK_FEED);
        captureElementScreenshot(feed, Constants.Paths.PATTERNS_PATH + File.separator + "facebook" + File.separator + "zero6.png");
    }

    public void runAllScenarios() {
        navigateToHomePage();
        login();
        accessGroup();
        homeLink();
        accessUser();
    }

    public int getRuns() {
        return this.runs;
    }

    @Override
    public void run() {
        for (int i = 0; i < getRuns(); i++) {
            runAllScenarios();
        }
    }
}




