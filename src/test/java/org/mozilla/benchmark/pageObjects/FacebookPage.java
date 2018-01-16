package org.mozilla.benchmark.pageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mozilla.benchmark.utils.BasePage;
import org.mozilla.benchmark.utils.Constants;
import org.mozilla.benchmark.utils.ScenarioManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by andrei.filip on 10/30/2017.
 */
public class FacebookPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(FacebookPage.class.getName());
    private int runs;
    private static final String TEST_NAME = "facebook";

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
        makeScreenshot(USER_NAME, "zero");
        makeScreenshot(USER_PASSWORD, "zero");
        sendKeys(USER_NAME, Constants.PageObjects.FACEBOOK_USER_NAME);
        sendKeys(USER_PASSWORD, Constants.PageObjects.FACEBOOK_PASS);
        click(LOGIN_BUTTON);
    }


    public void accessGroup() {
        WebElement group = getElements(GROUP_AUTOMATION).get(3);
        makeScreenshot(group, "zero");
        click(group);
    }

    public void homeLink() {
        makeScreenshot(HOME_BUTTON, "zero");
        click(HOME_BUTTON);
    }

    public void accessUser() {
        WebElement feed = getElement(FALLBACK_FEED);
        makeScreenshot(feed, "zero");
    }

    public void runAllScenarios() {
        navigateToHomePage();
        login();
        accessGroup();
        homeLink();
        accessUser();
    }

    private void makeScreenshot(WebElement element, String elementName) {
        captureElementScreenshot(element, ScenarioManager.getPatternName(elementName, TEST_NAME));
    }

    private void makeScreenshot(By element, String elementName) {
        captureElementScreenshot(element, ScenarioManager.getPatternName(elementName, TEST_NAME));
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




