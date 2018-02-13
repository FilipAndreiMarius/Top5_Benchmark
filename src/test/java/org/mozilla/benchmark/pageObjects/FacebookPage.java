package org.mozilla.benchmark.pageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mozilla.benchmark.objects.ImageSearchTypes;
import org.mozilla.benchmark.objects.PageNavigationTypes;
import org.mozilla.benchmark.utils.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.awt.*;

/**
 * Created by andrei.filip on 10/30/2017.
 */
public class FacebookPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(FacebookPage.class.getName());
    private int runs;
    private PageNavigationTypes navigationType;
    private static final String TEST_NAME = "facebook";

    private By USER_NAME = By.id("email");
    private By USER_PASSWORD = By.id("pass");
    private By LOGIN_BUTTON = By.id("loginbutton");
    private By GROUP_AUTOMATION = By.className("_5afe");
    private By HOME_BUTTON = By.cssSelector("a[href*='https://www.facebook.com/?ref=tn_tnmn']");
    private By FALLBACK_FEED = By.id("fallback_feed");

    public FacebookPage(int runs, PageNavigationTypes navigationType) {
        this.runs = runs;
        this.navigationType = navigationType;
    }

    public void navigateToHomePage() {
        logger.info("Accessing Facebook ...");
        navigateToUrl(Constants.PageObjects.FACEBOOK_URL);
    }

    public void login() {
        addPattern(USER_NAME, "zero", ImageSearchTypes.POSITIVE);
        addPattern(USER_PASSWORD, "zero", ImageSearchTypes.POSITIVE);
        addPattern(Constants.Paths.LOAD_PENDING_PATH, "zero", ImageSearchTypes.NEGATIVE);
        sendKeys(USER_NAME, Constants.PageObjects.FACEBOOK_USER_NAME);
        sendKeys(USER_PASSWORD, Constants.PageObjects.FACEBOOK_PASS);
        click(LOGIN_BUTTON);
    }

    public void accessGroup() {
        WebElement group = getElements(GROUP_AUTOMATION).get(3);
        addPattern(group, "zero", ImageSearchTypes.POSITIVE);
        click(group);
    }

    public void homeLink() {
        addPattern(HOME_BUTTON, "zero", ImageSearchTypes.POSITIVE);
        click(HOME_BUTTON);
    }

    public void accessUser() {
        WebElement feed = getElement(FALLBACK_FEED);
        addPattern(feed, "zero", ImageSearchTypes.POSITIVE);
    }

    public void runAllScenarios() {
        navigateToHomePage();
        login();
        accessGroup();
        homeLink();
        //accessUser();
    }

    public int getRuns() {
        return this.runs;
    }

    public PageNavigationTypes getNavigationType() {
        return this.navigationType;
    }

    private void addPattern(String source, String elementName, ImageSearchTypes searchType) {
        addPattern(source, elementName, TEST_NAME, getNavigationType(), searchType, PropertiesManager.getDefaultSimilarity());
    }

    private void addPattern(By selector, String elementName, ImageSearchTypes searchType) {
        addPattern(selector, elementName, TEST_NAME, getNavigationType(), searchType, PropertiesManager.getDefaultSimilarity());
    }

    private void addPattern(Color color, String elementName, ImageSearchTypes searchType) {
        addPattern(color, elementName, TEST_NAME, getNavigationType(), searchType, PropertiesManager.getDefaultSimilarity());
    }

    private void addPattern(WebElement webElement, String elementName, ImageSearchTypes searchType) {
        addPattern(webElement, elementName, TEST_NAME, getNavigationType(), searchType, PropertiesManager.getDefaultSimilarity());
    }

    @Override
    public void run() {
        for (int i = 0; i < getRuns(); i++) {
            runAllScenarios();
        }
        DriverUtils.closeWebBrowser();
    }
}




