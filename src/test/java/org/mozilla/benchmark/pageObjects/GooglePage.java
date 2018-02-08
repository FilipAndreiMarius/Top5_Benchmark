package org.mozilla.benchmark.pageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mozilla.benchmark.objects.ImageSearchTypes;
import org.mozilla.benchmark.utils.*;
import org.openqa.selenium.*;

import java.awt.*;
import java.util.List;

/**
 * Created by andrei.filip on 10/30/2017.
 */
public class GooglePage extends BasePage {

    private static final Logger logger = LogManager.getLogger(GooglePage.class.getName());
    private int runs;
    private Boolean takeScreenshot;
    private static final String TEST_NAME = "google";

    private By GOOGLE_LOGO = By.id("hplogo");
    private By GOOGLE_SEARCH_BAR = By.id("lst-ib");
    private By TOP_MENU = By.xpath("//*[@class='q qs']");
    private By TOP_RIGTH_MENU = By.xpath("//*[@class='gb_P']");
    private By ENGLISH_LANGUAGE = By.xpath("//*[contains(text(), 'English')]");

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

    public GooglePage(int runs, Boolean takeScreenshot) {
        this.runs = runs;
        this.takeScreenshot = takeScreenshot;
    }

    public void navigateToHomePage() {
        logger.info("Accessing Google ...");
        navigateToURL(Constants.PageObjects.GSEARCH_URL);
        changeToEnglishVersion();
        addPattern(GOOGLE_LOGO, "startingPoint", ImageSearchTypes.POSITIVE);
        addPattern(Constants.Paths.LOAD_DONE_PATH, "startingPoint", ImageSearchTypes.POSITIVE);
    }

    public void search() {
        logger.info("Searching [" + Constants.PageObjects.SEARCH_ITEM + "] ...");
        sendKeysAndPressEnter(GOOGLE_SEARCH_BAR, Constants.PageObjects.SEARCH_ITEM);
        addPattern(Constants.Paths.LOAD_PENDING_PATH, "navigationStart", ImageSearchTypes.POSITIVE);
        addPattern(getElements(TOP_RIGTH_MENU).get(0), "firstNonBlank", ImageSearchTypes.NEGATIVE);
        addPattern(TOP_STORIES_LIST,"hero", ImageSearchTypes.POSITIVE);
        addPattern(SIDEBAR_IMAGE_1,"hero", ImageSearchTypes.POSITIVE);
        addPattern(SIDEBAR_IMAGE_2,"hero", ImageSearchTypes.POSITIVE);
        addPattern(SIDEBAR_IMAGE_3,"hero", ImageSearchTypes.POSITIVE);
        addPattern(SIDEBAR_IMAGE_4,"hero", ImageSearchTypes.POSITIVE);
        addPattern(SIDEBAR_IMAGE_5,"hero", ImageSearchTypes.POSITIVE);
        addPattern(SIDEBAR_IMAGE_6,"hero", ImageSearchTypes.POSITIVE);
        addPattern(Constants.Paths.LOAD_DONE_PATH, "lastPaint", ImageSearchTypes.POSITIVE);
        driverSleep(1500);
    }

    public void accessImage() {
        logger.info("Selecting [Images] section ...");
        click(getElements(TOP_MENU).get(0));
        addPattern(Constants.Paths.LOAD_PENDING_PATH, "imageStart", ImageSearchTypes.POSITIVE);
        addPattern(getElements(TOP_MENU).get(0),"imageFirstNonBlank", ImageSearchTypes.POSITIVE);
        addPattern(FIRST_ROW_IMAGE_RESULTS, "imageHero", ImageSearchTypes.POSITIVE);
        addPattern(Constants.Paths.LOAD_DONE_PATH, "imageLastPaint", ImageSearchTypes.POSITIVE);
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
    public Boolean getTakeScreenshot() {
        return this.takeScreenshot;
    }

    private void addPattern(String source, String elementName, ImageSearchTypes searchType) {
        addPattern(source, elementName, TEST_NAME, getTakeScreenshot(), searchType, PropertiesManager.getDefaultSimilarity());
    }

    private void addPattern(By selector, String elementName, ImageSearchTypes searchType) {
        addPattern(selector, elementName, TEST_NAME, getTakeScreenshot(), searchType, PropertiesManager.getDefaultSimilarity());
    }

    private void addPattern(Color color, String elementName, ImageSearchTypes searchType) {
        addPattern(color, elementName, TEST_NAME, getTakeScreenshot(), searchType, PropertiesManager.getDefaultSimilarity());
    }

    private void addPattern(WebElement webElement, String elementName, ImageSearchTypes searchType) {
        addPattern(webElement, elementName, TEST_NAME, getTakeScreenshot(), searchType, PropertiesManager.getDefaultSimilarity());
    }

    private void changeToEnglishVersion() {
        if (("Imagini").equals(getElements(TOP_RIGTH_MENU).get(1).getText())){
            click(ENGLISH_LANGUAGE);
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < getRuns(); i++) {
            runAllScenarios();
        }
        DriverUtils.closeWebBrowser();
    }
}


