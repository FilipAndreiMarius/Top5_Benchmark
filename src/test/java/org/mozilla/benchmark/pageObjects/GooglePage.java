package org.mozilla.benchmark.pageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mozilla.benchmark.utils.BasePage;
import org.mozilla.benchmark.utils.Constants;
import org.openqa.selenium.By;

/**
 * Created by andrei.filip on 10/30/2017.
 */
public class GooglePage extends BasePage {

    private static final Logger logger = LogManager.getLogger(GooglePage.class.getName());
    private int runs;

    private By GOOGLE_SEARCH_BAR = By.id("lst-ib");
    private By GOOGLE_IMAGE = By.xpath("//*[@class='q qs']");

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


