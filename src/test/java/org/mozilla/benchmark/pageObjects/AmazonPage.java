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
public class AmazonPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(AmazonPage.class.getName());

    //patterns Section 1
    private By AMAZON_CART = By.id("nav-cart");
    private By LOGIN_POP_UP = By.id("nav-signin-tooltip");
    private By NAV_LOGO = By.id("nav-logo");

    //patterns Section 2,4
    private By FIRST_RESULT = By.id("result_1");
    private By NAV_SEARCH = By.id("nav-search");

    //patterns Section 3
    private By VIDEO_RESULT = By.id("aiv-main-content");

    //patterns Section 5
    private By SECOND_RESULT = By.id("img-canvas");

    private By SEARCH_BAR = By.id("twotabsearchtextbox");
    private By SEARCH_BUTTON = By.className("nav-input");
    private By VIDEO_PRODUCT = By.xpath("//*[contains(text(),'The Lord Of The Rings: The Fellowship Of The Ring')]");
    private By BOOK_PRODUCT = By.xpath("//*[contains(text(),'The Lord of the Rings: 50th Anniversary, One Vol. Edition')]");

    public AmazonPage(int runs, String testName, PageNavigationTypes navigationType) {
        super(runs, testName, navigationType);
    }

    public void accessAmazon() {
        logger.info("Accessing Amazon ...");
        navigateToUrl(WebPageConstants.AMAZON_URL);
        addPattern(PathConstants.LOAD_PENDING_PATH, "Section1_navigationStart", ImageSearchTypes.POSITIVE);
        addPattern(NAV_LOGO, "Section1_firstNonBlank", ImageSearchTypes.POSITIVE);
        addPattern(AMAZON_CART, "Section1_heroElement", ImageSearchTypes.POSITIVE);
        addPattern(SEARCH_BUTTON, "Section1_heroElement", ImageSearchTypes.POSITIVE);
        addPattern(LOGIN_POP_UP, "Section1_lastPaint", ImageSearchTypes.POSITIVE);
        addPattern(PathConstants.LOAD_DONE_PATH, "Section1_lastPaint", ImageSearchTypes.POSITIVE);
    }

    public void searchAmazon() {
        logger.info("Searching [" + WebPageConstants.AMAZON_SEARCH_ITEM + "] ...");
        sendKeys(SEARCH_BAR, WebPageConstants.AMAZON_SEARCH_ITEM);
        click(SEARCH_BUTTON);
        addPattern(NAV_SEARCH, "Section2_beforeActionElement", ImageSearchTypes.POSITIVE);
        addPattern(PathConstants.LOAD_PENDING_PATH, "Section2_searchStart", ImageSearchTypes.POSITIVE);
        addPattern(FIRST_RESULT, "Section2_heroElement", ImageSearchTypes.POSITIVE);
        addPattern(PathConstants.LOAD_DONE_PATH, "Section2_lastPaint", ImageSearchTypes.POSITIVE);
    }


    public void accessVideoResult() {
        logger.info("Navigate to video product ...");
        click(VIDEO_PRODUCT);
        addPattern(PathConstants.LOAD_PENDING_PATH, "Section3_searchStart", ImageSearchTypes.POSITIVE);
        addPattern(PathConstants.LOAD_DONE_PATH, "Section3_firstNonBlank", ImageSearchTypes.POSITIVE);
        addPattern(VIDEO_RESULT, "Section3_heroElement", ImageSearchTypes.POSITIVE);
        addPattern(PathConstants.LOAD_DONE_PATH, "Section3_lastPaint", ImageSearchTypes.POSITIVE);
    }

    public void backAction() {
        logger.info("Navigate back ...");
        navigateBack();
        addPattern(PathConstants.LOAD_PENDING_PATH, "Section4_backAction", ImageSearchTypes.POSITIVE);
        addPattern(PathConstants.LOAD_DONE_PATH, "Section4_firstNonBlank", ImageSearchTypes.POSITIVE);
        addPattern(FIRST_RESULT, "Section4_heroElement", ImageSearchTypes.POSITIVE);
        addPattern(PathConstants.LOAD_DONE_PATH, "Section4_lastPaint", ImageSearchTypes.POSITIVE);
    }

    public void accessBookResult() {
        logger.info("Navigate to book product ...");
        click(BOOK_PRODUCT);
        addPattern(PathConstants.LOAD_PENDING_PATH, "Section5_AccessPapper", ImageSearchTypes.POSITIVE);
        addPattern(PathConstants.LOAD_DONE_PATH, "Section5_firstNonBlank", ImageSearchTypes.POSITIVE);
        addPattern(SECOND_RESULT, "Section5_heroElement", ImageSearchTypes.POSITIVE);
        addPattern(PathConstants.LOAD_DONE_PATH, "Section5_lastPaint", ImageSearchTypes.POSITIVE);
    }

    public void runAllScenarios() {
        accessAmazon();
        searchAmazon();
        accessVideoResult();
        backAction();
        accessBookResult();
        resetRun();
    }
}