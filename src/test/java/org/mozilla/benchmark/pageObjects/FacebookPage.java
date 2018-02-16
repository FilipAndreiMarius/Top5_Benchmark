package org.mozilla.benchmark.pageObjects;

import org.mozilla.benchmark.constants.PathConstants;
import org.mozilla.benchmark.constants.WebPageConstants;
import org.mozilla.benchmark.objects.ImageSearchTypes;
import org.mozilla.benchmark.objects.LoggerManagerLevel;
import org.mozilla.benchmark.objects.PageNavigationTypes;
import org.mozilla.benchmark.utils.BasePage;
import org.mozilla.benchmark.utils.LoggerManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by andrei.filip on 10/30/2017.
 */
public class FacebookPage extends BasePage {

    private static final LoggerManager logger = new LoggerManager(FacebookPage.class.getName());

    private By USER_NAME = By.id("email");
    private By USER_PASSWORD = By.id("pass");
    private By LOGIN_BUTTON = By.id("loginbutton");
    private By GROUP_AUTOMATION = By.className("_5afe");
    private By HOME_BUTTON = By.cssSelector("a[href*='https://www.facebook.com/?ref=tn_tnmn']");
    private By FALLBACK_FEED = By.id("fallback_feed");

    public FacebookPage(int runs, String testName, PageNavigationTypes navigationType) {
        super(runs, testName, navigationType);
    }

    public void navigateToHomePage() {
        logger.log(LoggerManagerLevel.INFO, "Accessing Facebook ...", false);
        navigateToUrl(WebPageConstants.FACEBOOK_URL);
    }

    public void login() {
        addPattern(USER_NAME, "zero", ImageSearchTypes.POSITIVE);
        addPattern(USER_PASSWORD, "zero", ImageSearchTypes.POSITIVE);
        addPattern(PathConstants.LOAD_PENDING_PATH, "zero", ImageSearchTypes.NEGATIVE);
        sendKeys(USER_NAME, WebPageConstants.FACEBOOK_USER_NAME);
        sendKeys(USER_PASSWORD, WebPageConstants.FACEBOOK_PASS);
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
}




