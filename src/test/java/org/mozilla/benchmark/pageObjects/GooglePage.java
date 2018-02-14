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
public class GooglePage extends BasePage {

    private static final Logger logger = LogManager.getLogger(GooglePage.class.getName());
    private int runs;
    private PageNavigationTypes navigationType;
    private static final String TEST_NAME = "google";

    private By GOOGLE_LOGO = By.id("hplogo");
    private By GOOGLE_SEARCH_BAR = By.id("lst-ib");
    private By TOP_MENU = By.xpath("//*[@class='q qs']");
    private By TOP_RIGTH_MENU = By.xpath("//*[@class='gb_P']");

    //sidebar images
    private By SIDEBAR_IMAGE_1 = By.id("uid_dimg_3");
    private By SIDEBAR_IMAGE_2 = By.id("uid_dimg_4");
    private By SIDEBAR_IMAGE_3 = By.id("uid_dimg_5");
    private By SIDEBAR_IMAGE_4 = By.id("uid_dimg_6");
    private By SIDEBAR_IMAGE_5 = By.id("uid_dimg_7");
    private By SIDEBAR_IMAGE_6 = By.id("uid_dimg_8");

    //top stories
    private By TOP_STORIES_LIST = By.className("_KBh");

    //first row of image search results
    private By FIRST_ROW_IMAGE_RESULTS = By.xpath("//*[@data-row='0']");

    public GooglePage(int runs, PageNavigationTypes navigationType) {
        this.runs = runs;
        this.navigationType = navigationType;
    }

    public void navigateToHomePage() {
        logger.info("Accessing Google ...");
        navigateToUrl(WebPageConstants.GSEARCH_URL);
        addPattern(GOOGLE_LOGO, "startingPoint", ImageSearchTypes.POSITIVE);
        addPattern(PathConstants.LOAD_DONE_PATH, "startingPoint", ImageSearchTypes.POSITIVE);
    }

    public void search() {
        addPattern(PathConstants.LOAD_PENDING_PATH, "navigationStart", ImageSearchTypes.POSITIVE);
        addPattern(getElements(TOP_RIGTH_MENU).get(0), "firstNonBlank", ImageSearchTypes.NEGATIVE);

        logger.info("Searching [" + WebPageConstants.SEARCH_ITEM + "] ...");
        sendKeysAndPressEnter(GOOGLE_SEARCH_BAR, WebPageConstants.SEARCH_ITEM);

        addPattern(TOP_STORIES_LIST,"hero", ImageSearchTypes.POSITIVE);
        addPattern(SIDEBAR_IMAGE_1,"hero", ImageSearchTypes.POSITIVE);
        addPattern(SIDEBAR_IMAGE_2,"hero", ImageSearchTypes.POSITIVE);
        addPattern(SIDEBAR_IMAGE_3,"hero", ImageSearchTypes.POSITIVE);
        addPattern(SIDEBAR_IMAGE_4,"hero", ImageSearchTypes.POSITIVE);
        addPattern(SIDEBAR_IMAGE_5,"hero", ImageSearchTypes.POSITIVE);
        addPattern(SIDEBAR_IMAGE_6,"hero", ImageSearchTypes.POSITIVE);
        addPattern(PathConstants.LOAD_DONE_PATH, "lastPaint", ImageSearchTypes.POSITIVE);
        driverSleep(1500);
    }

    public void accessImage() {
        logger.info("Selecting [Images] section ...");
        click(getElements(TOP_MENU).get(0));
        addPattern(PathConstants.LOAD_PENDING_PATH, "imageStart", ImageSearchTypes.POSITIVE);
        addPattern(getElements(TOP_MENU).get(0),"imageFirstNonBlank", ImageSearchTypes.POSITIVE);
        addPattern(FIRST_ROW_IMAGE_RESULTS, "imageHero", ImageSearchTypes.POSITIVE);
        addPattern(PathConstants.LOAD_DONE_PATH, "imageLastPaint", ImageSearchTypes.POSITIVE);
        driverSleep(1500);
    }

    public void runAllScenarios() {
        navigateToHomePage();
        search();
        accessImage();
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


