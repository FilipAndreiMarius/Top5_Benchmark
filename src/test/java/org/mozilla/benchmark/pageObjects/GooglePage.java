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

    public GooglePage(int runs, Boolean takeScreenshot) {
        this.runs = runs;
        this.takeScreenshot = takeScreenshot;
    }

    public void accessImage() {
        logger.info("Selecting [Images] section ...");
        click(GOOGLE_IMAGE);
    }

    public void navigateToHomePage() {
        logger.info("Accessing Google ...");
        navigateToURL(Constants.PageObjects.GSEARCH_URL);
        addPattern(Constants.Paths.LOAD_PENDING_PATH, "firstNonBlank", TEST_NAME, getTakeScreenshot(), ImageSearchTypes.POSITIVE, 0.95f);
        addPattern(ColorManager.getColorFromString(BROWSER_BG_COLOR),
                "firstNonBlank", TEST_NAME, getTakeScreenshot(), ImageSearchTypes.NEGATIVE, 0.98f);
    }

    public void search() {
        logger.info("Searching [" + Constants.PageObjects.SEARCH_ITEM + "] ...");
        sendKeysAndPressEnter(GOOGLE_SEARCH_BAR, Constants.PageObjects.SEARCH_ITEM);
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


