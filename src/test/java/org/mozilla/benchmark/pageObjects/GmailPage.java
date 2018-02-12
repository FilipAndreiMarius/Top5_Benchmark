package org.mozilla.benchmark.pageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mozilla.benchmark.objects.ImageSearchTypes;
import org.mozilla.benchmark.objects.PageNavigationTypes;
import org.mozilla.benchmark.utils.BasePage;
import org.mozilla.benchmark.utils.Constants;
import org.mozilla.benchmark.utils.DriverUtils;
import org.mozilla.benchmark.utils.PropertiesManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;


/**
 * Created by andrei.filip on 10/30/2017.
 */
public class GmailPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(GmailPage.class.getName());
    private int runs;
    private PageNavigationTypes navigationType;
    private static final String TEST_NAME = "gmail";
    private static final String COOKIE_PATH = Constants.Paths.COOKIES + File.separator + TEST_NAME + File.separator + "cookies.data";

    private By USER_NAME = By.id("identifierId");
    private By USER_NAME_NEXT_BUTTON = By.id("identifierNext");
    private By USER_PASSWORD = By.name("password");
    private By USER_PASSWORD_NEXT_BUTTON = By.id("passwordNext");
    private By HYPERLINK_INSIDE_MAIL = By.className("m_-7793005015168254029m_3189775553631395212video-title-font-class");

    public GmailPage(int runs, PageNavigationTypes navigationType) {
        this.runs = runs;
        this.navigationType = navigationType;
    }

    public void navigateToHomePage() {
        if (!PageNavigationTypes.SAVE_COOKIES.equals(getNavigationType())) {
            getCookieInformation(COOKIE_PATH);
        }
        logger.info("Accessing Gmail ...");
        navigateToURL(Constants.PageObjects.GMAIL_URL);
    }

    public void login() {
        if (PageNavigationTypes.SAVE_COOKIES.equals(getNavigationType())) {
            sendKeys(USER_NAME, Constants.PageObjects.GMAIL_USER_NAME);
            click(USER_NAME_NEXT_BUTTON);
            driverSleep(500);
            sendKeys(USER_PASSWORD, Constants.PageObjects.GMAIL_PASSWORD);
            click(USER_PASSWORD_NEXT_BUTTON);
            storeCookieInformation(COOKIE_PATH);
            DriverUtils.closeWebBrowser();
        }
    }

    public void accessYoutubeLink() {
        if (!PageNavigationTypes.SAVE_COOKIES.equals(getNavigationType())) {
            navigateToURL(Constants.PageObjects.GMAIL_YOUTUBE_LINK);
            click(HYPERLINK_INSIDE_MAIL);
            driverSleep(3000);
        }
    }

    public void runAllScenarios() {
        navigateToHomePage();
        login();
        accessYoutubeLink();
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




