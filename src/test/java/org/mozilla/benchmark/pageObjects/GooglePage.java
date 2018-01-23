package org.mozilla.benchmark.pageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mozilla.benchmark.utils.BasePage;
import org.mozilla.benchmark.utils.ColorManager;
import org.mozilla.benchmark.utils.Constants;
import org.mozilla.benchmark.utils.ScenarioManager;
import org.openqa.selenium.*;

import java.awt.*;

/**
 * Created by andrei.filip on 10/30/2017.
 */
public class GooglePage extends BasePage {

    private static final Logger logger = LogManager.getLogger(GooglePage.class.getName());
    private int runs;
    private static final String TEST_NAME = "google";

    private By GOOGLE_LOGO = By.id("hplogo");
    private By GOOGLE_SEARCH_BAR = By.id("lst-ib");
    private By GOOGLE_IMAGE = By.xpath("//*[@class='q qs']");
    private By GOOGLE_BACKGROUND = By.xpath("//*[@id='gsr']");

    public GooglePage(int runs) {
        this.runs = runs;
    }

    public void accessImage() {
        logger.info("Selecting [Images] section ...");
        click(GOOGLE_IMAGE);
    }

    public void navigateToHomePage() {
        logger.info("Accessing Google ...");
        navigateToURL(Constants.PageObjects.GSEARCH_URL);
        makeBackground(ColorManager.getColorFromString(getElement(GOOGLE_BACKGROUND).getCssValue("background-color")), "firstNonBlank", TEST_NAME);
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

    @Override
    public void run() {
        for (int i = 0; i < this.runs; i++) {
            runAllScenarios();
        }
    }
}


