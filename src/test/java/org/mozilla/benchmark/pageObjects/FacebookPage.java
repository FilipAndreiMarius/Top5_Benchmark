package org.mozilla.benchmark.pageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mozilla.benchmark.objects.ImageSearchTypes;
import org.mozilla.benchmark.utils.BasePage;
import org.mozilla.benchmark.utils.Constants;
import org.mozilla.benchmark.utils.DriverUtils;
import org.mozilla.benchmark.utils.ImagePatternUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by andrei.filip on 10/30/2017.
 */
public class FacebookPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(FacebookPage.class.getName());
    private int runs;
    private Boolean takeScreenshot;
    private static final String TEST_NAME = "facebook";

    private By USER_NAME = By.id("email");
    private By USER_PASSWORD = By.id("pass");
    private By LOGIN_BUTTON = By.id("loginbutton");
    private By GROUP_AUTOMATION = By.className("_5afe");
    private By HOME_BUTTON = By.cssSelector("a[href*='https://www.facebook.com/?ref=tn_tnmn']");
    private By FALLBACK_FEED = By.id("fallback_feed");

    public FacebookPage(int runs, Boolean takeScreenshot) {
        this.runs = runs;
        this.takeScreenshot = takeScreenshot;
    }

    public void navigateToHomePage() {
        logger.info("Accessing Facebook ...");
        navigateToURL(Constants.PageObjects.FACEBOOK_URL);
    }

    public void login() {
        addPattern(USER_NAME, "zero", TEST_NAME, getTakeScreenshot(), ImageSearchTypes.POSITIVE, 0.95f);
        addPattern(USER_PASSWORD, "zero", TEST_NAME, getTakeScreenshot(), ImageSearchTypes.POSITIVE, 0.95f);
        addPattern(Constants.Paths.LOAD_PENDING_PATH, "zero", TEST_NAME, getTakeScreenshot(), ImageSearchTypes.NEGATIVE, 0.95f);
        sendKeys(USER_NAME, Constants.PageObjects.FACEBOOK_USER_NAME);
        sendKeys(USER_PASSWORD, Constants.PageObjects.FACEBOOK_PASS);
        click(LOGIN_BUTTON);
    }

    public void accessGroup() {
        WebElement group = getElements(GROUP_AUTOMATION).get(3);
        addPattern(group, "zero", TEST_NAME, getTakeScreenshot(), ImageSearchTypes.POSITIVE, 0.95f);
        click(group);
    }

    public void homeLink() {
        addPattern(HOME_BUTTON, "zero", TEST_NAME, getTakeScreenshot(), ImageSearchTypes.POSITIVE, 0.95f);
        click(HOME_BUTTON);
    }

    public void accessUser() {
        WebElement feed = getElement(FALLBACK_FEED);
        addPattern(feed, "zero", TEST_NAME, getTakeScreenshot(), ImageSearchTypes.POSITIVE, 0.95f);
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

    public Boolean getTakeScreenshot() {
        return this.takeScreenshot;
    }

    @Override
    public void run() {
        for (int i = 0; i < getRuns(); i++) {
            runAllScenarios();
        }
        DriverUtils.closeWebBrowser();
    }
}




