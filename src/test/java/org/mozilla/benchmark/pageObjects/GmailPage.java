package org.mozilla.benchmark.pageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mozilla.benchmark.constants.PathConstants;
import org.mozilla.benchmark.constants.WebPageConstants;
import org.mozilla.benchmark.objects.ImageSearchTypes;
import org.mozilla.benchmark.objects.PageNavigationTypes;
import org.mozilla.benchmark.utils.BasePage;
import org.mozilla.benchmark.utils.DriverUtils;
import org.mozilla.benchmark.utils.PropertiesManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.awt.*;


/**
 * Created by andrei.filip on 10/30/2017.
 */
public class GmailPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(GmailPage.class.getName());
    private int runs;
    private PageNavigationTypes navigationType;
    private static final String TEST_NAME = "gmail";
    private By HYPERLINK_INSIDE_MAIL = By.className("m_-7793005015168254029m_3189775553631395212video-title-font-class");
    private By GMAIL_TEXT = By.id(":i");
    private By CHAT_IFRAME = By.className("a1j");
    private By CONVERSATION_ICON = By.cssSelector(".VBgE5b");

    public GmailPage(int runs, PageNavigationTypes navigationType) {
        this.runs = runs;
        this.navigationType = navigationType;
    }

    public void navigateToHomePage() {
        addPattern(WebPageConstants.HOME_PAGE_PATTERN, "startingPoint", ImageSearchTypes.POSITIVE);
        logger.info("Accessing Gmail ...");
        navigateToUrl(WebPageConstants.GMAIL_URL);
        addPattern(PathConstants.LOAD_PENDING_PATH, "navigationStart", ImageSearchTypes.POSITIVE);
        addPattern(PathConstants.GMAIL_LOADING_PATH, "firstNonBlank", ImageSearchTypes.POSITIVE);
        addPattern(GMAIL_TEXT, "hero", ImageSearchTypes.POSITIVE);
        addPattern(PathConstants.LOAD_DONE_PATH, "lastPaint", ImageSearchTypes.POSITIVE);
        addPattern(getElementFromIframe(CHAT_IFRAME, CONVERSATION_ICON), "lastPaint", ImageSearchTypes.POSITIVE);
    }


    public void accessYoutubeLink() {
        navigateToUrl(WebPageConstants.GMAIL_YOUTUBE_LINK);

        click(HYPERLINK_INSIDE_MAIL);
        driverSleep(4000);
    }

    public void runAllScenarios() {
        navigateToHomePage();
        accessYoutubeLink();
        resetRun();
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




