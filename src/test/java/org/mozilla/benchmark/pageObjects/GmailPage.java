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

    public GmailPage(int runs, PageNavigationTypes navigationType) {
        this.runs = runs;
        this.navigationType = navigationType;
    }

    public void navigateToHomePage() {
        logger.info("Accessing Gmail ...");
        navigateToUrl(Constants.PageObjects.GMAIL_URL);
        driverSleep(1000);
    }


    public void accessYoutubeLink() {
        navigateToUrl(Constants.PageObjects.GMAIL_YOUTUBE_LINK);
        driverSleep(2000);
        click(HYPERLINK_INSIDE_MAIL);
    }

    public void runAllScenarios() {
        navigateToHomePage();
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




