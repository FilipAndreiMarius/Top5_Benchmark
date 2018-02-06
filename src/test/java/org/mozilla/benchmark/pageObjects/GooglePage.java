package org.mozilla.benchmark.pageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mozilla.benchmark.objects.ImageSearchTypes;
import org.mozilla.benchmark.utils.*;
import org.openqa.selenium.*;

/**
 * Created by andrei.filip on 10/30/2017.
 */
public class GooglePage extends BasePage {

    private static final Logger logger = LogManager.getLogger(GooglePage.class.getName());
    private int runs;
    private Boolean takeScreenshot;
    private static final String TEST_NAME = "google";
    private By GOOGLE_SEARCH_BAR = By.id("lst-ib");
    private By GOOGLE_IMAGE = By.xpath("//*[@class='q qs']");
    private By ENGLISH_LANGUAGE = By.xpath("//*[contains(text(), 'English')]");

    //sidebar images
    private By SIDEBAR_IMAGE_1 = By.id("uid_dimg_0");
    private By SIDEBAR_IMAGE_2 = By.id("uid_dimg_1");
    private By SIDEBAR_IMAGE_3 = By.id("uid_dimg_2");
    private By SIDEBAR_IMAGE_4 = By.id("uid_dimg_3");
    private By SIDEBAR_IMAGE_5 = By.id("uid_dimg_4");
    private By SIDEBAR_IMAGE_6 = By.id("uid_dimg_5");



    public GooglePage(int runs, Boolean takeScreenshot) {
        this.runs = runs;
        this.takeScreenshot = takeScreenshot;
    }

    public void navigateToHomePage() {
        logger.info("Accessing Google ...");
        navigateToURL(Constants.PageObjects.GSEARCH_URL);
        click(ENGLISH_LANGUAGE);
    }

    public void search() {
        logger.info("Searching [" + Constants.PageObjects.SEARCH_ITEM + "] ...");
        sendKeysAndPressEnter(GOOGLE_SEARCH_BAR, Constants.PageObjects.SEARCH_ITEM);
        addPattern(Constants.Paths.LOAD_PENDING_PATH, "navigationStart", TEST_NAME, takeScreenshot, ImageSearchTypes.POSITIVE, 0.95f);
        addPattern(ColorManager.getColorFromString(BROWSER_BG_COLOR),
                "firstNonBlank", TEST_NAME, getTakeScreenshot(), ImageSearchTypes.NEGATIVE, 0.98f);
        addPattern(SIDEBAR_IMAGE_1,"hero", TEST_NAME, getTakeScreenshot(), ImageSearchTypes.POSITIVE, 0.95f);
        addPattern(SIDEBAR_IMAGE_2,"hero", TEST_NAME, getTakeScreenshot(), ImageSearchTypes.POSITIVE, 0.95f);
        addPattern(SIDEBAR_IMAGE_3,"hero", TEST_NAME, getTakeScreenshot(), ImageSearchTypes.POSITIVE, 0.95f);
        addPattern(SIDEBAR_IMAGE_4,"hero", TEST_NAME, getTakeScreenshot(), ImageSearchTypes.POSITIVE, 0.95f);
        addPattern(SIDEBAR_IMAGE_5,"hero", TEST_NAME, getTakeScreenshot(), ImageSearchTypes.POSITIVE, 0.95f);
        addPattern(SIDEBAR_IMAGE_6,"hero", TEST_NAME, getTakeScreenshot(), ImageSearchTypes.POSITIVE, 0.95f);
    }

    public void accessImage() {
        logger.info("Selecting [Images] section ...");
        click(GOOGLE_IMAGE);
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

    @Override
    public void run() {
        for (int i = 0; i < getRuns(); i++) {
            runAllScenarios();
        }
        DriverUtils.closeWebBrowser();
    }
}


