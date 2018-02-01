package org.mozilla.benchmark.pageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mozilla.benchmark.utils.BasePage;
import org.mozilla.benchmark.utils.DriverUtils;
import org.mozilla.benchmark.utils.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by andrei.filip on 10/30/2017.
 */
public class AmazonPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(AmazonPage.class.getName());
    private int runs;

    private By SEARCH_BAR = By.id("twotabsearchtextbox");
    private By SEARCH_BUTTON = By.className("nav-input");
    private By VIDEO_PRODUCT = By.xpath("//*[contains(text(),'The Lord Of The Rings: The Fellowship Of The Ring')]");
    private By BOOK_PRODUCT = By.xpath("//*[contains(text(),'The Lord of the Rings: 50th Anniversary, One Vol. Edition')]");

    public AmazonPage(int runs) {
        this.runs = runs;
    }

    public void accessAmazon() {
        logger.info("Accessing Amazon ...");
        navigateToURL(Constants.PageObjects.AMAZON_URL);
    }

    public void searchAmazon() {
        logger.info("Searching [" + Constants.PageObjects.AMAZON_SEARCH_ITEM + "] ...");
        sendKeys(SEARCH_BAR, Constants.PageObjects.AMAZON_SEARCH_ITEM);
        click(SEARCH_BUTTON);
    }


    public void accessVideoResult() {
        logger.info("Navigate to video product ...");
        click(VIDEO_PRODUCT);
    }

    public void backAction() {
        logger.info("Navigate back ...");
        navigateBack();
    }

    public void accessBookResult() {
        logger.info("Navigate to book product ...");
        click(BOOK_PRODUCT);
    }

    public void runAllScenarios() {
        accessAmazon();
        searchAmazon();
        accessVideoResult();
        backAction();
        accessBookResult();
    }

    @Override
    public void run() {
        for (int i = 0; i < this.runs; i++) {
            runAllScenarios();
        }
        DriverUtils.closeWebBrowser();
    }
}
