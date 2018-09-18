package org.mozilla.benchmark.pageObjects;

import org.mozilla.benchmark.constants.PathConstants;
import org.mozilla.benchmark.constants.WebPageConstants;
import org.mozilla.benchmark.objects.ImageSearchTypes;
import org.mozilla.benchmark.objects.LoggerManagerLevel;
import org.mozilla.benchmark.objects.PageNavigationTypes;
import org.mozilla.benchmark.utils.BasePage;
import org.mozilla.benchmark.utils.ColorManager;
import org.mozilla.benchmark.utils.LoggerManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.util.List;

/**
 * Created by andrei.filip on 10/30/2017.
 */
public class AmazonPage extends BasePage {

    private static final LoggerManager logger = new LoggerManager(AmazonPage.class.getName());

    //patterns Section 1
    private By AMAZON_CART = By.id("nav-cart");
    private By SEARCH_ICON = By.className("nav-input");
    private By HEADER=By.className("icp-nav-language");
    private Color FAVICON_AMAZON = ColorManager.getColorFromString("#050505");

    //patterns Section 2
    private By SEARCH_BAR = By.id("twotabsearchtextbox");
    private Color SECTION2_FIRSTNONB = ColorManager.getColorFromString("#cccccc");
    private By FIRST_RESULT = By.id("result_0");
    private By NAV_SEARCH = By.id("nav-search");

    //patterns Section 3
    private By VIDEO_RESULT = By.cssSelector("a[href='#btf-product-details']");
    private Color NAVBAR = ColorManager.getColorFromString("#242f3e");

    //patterns Section 5
    private By BOOK_RESULT = By.id("ebooksImgBlkFront");
    private By VIDEO_PRODUCT = By.xpath("//*[contains(text(),'The Lord Of The Rings: The Fellowship Of The Ring')]");
    private By BOOK_PRODUCT = By.xpath("//*[contains(text(),'The Fellowship of the Ring (The Lord of the Rings, Book 1)')]");
    private By BUY_BOX = By.id("combinedBuyBox");

    public AmazonPage(int runs, String testName, PageNavigationTypes navigationType) {
        super(runs, testName, navigationType);
    }

    public void accessAmazon() {
        navigateToUrl(WebPageConstants.HOME_PAGE_URL);
        addPattern(WebPageConstants.HOME_PAGE_PATTERN, "startingPoint", ImageSearchTypes.POSITIVE);
        logger.log(LoggerManagerLevel.INFO, "Accessing Amazon ...", false);
        navigateToUrl(WebPageConstants.AMAZON_URL);
        addPattern(PathConstants.LOAD_PENDING_PATH, "Section1_navigationStart", ImageSearchTypes.POSITIVE);
        addPattern(HEADER, "Section1_firstNonBlank", ImageSearchTypes.POSITIVE);
        addPattern(SEARCH_ICON, "Section1_heroElement", ImageSearchTypes.POSITIVE);
        addPattern(FAVICON_AMAZON, new Rectangle(18, 14, 2, 2), "Section1_lastPaint", ImageSearchTypes.BACKGROUND_POSITIVE);
        addPattern(PathConstants.LOAD_DONE_PATH, "Section1_lastPaint", ImageSearchTypes.POSITIVE);
        driverSleep(1000);
    }

    public void searchAmazon() {
        logger.log(LoggerManagerLevel.INFO, "Searching [" + WebPageConstants.AMAZON_SEARCH_ITEM + "] ...", false);
        sendKeysAndPressEnter(SEARCH_BAR, WebPageConstants.AMAZON_SEARCH_ITEM);
        addPattern(NAV_SEARCH, "Section2_beforeActionElement", ImageSearchTypes.POSITIVE);
        addPatternWithSimilarity(PathConstants.ENTER_DOWN_PATH, "Section2_NavigationStart", ImageSearchTypes.POSITIVE, 0.99f);
        addPattern(SECTION2_FIRSTNONB, new Rectangle(40, 268, 4, 1), "Section2_firstNonBlank", ImageSearchTypes.BACKGROUND_POSITIVE);
        addPattern(FIRST_RESULT, "Section2_heroElement", ImageSearchTypes.POSITIVE);
        addPattern(PathConstants.LOAD_DONE_PATH, "Section2_lastPaint", ImageSearchTypes.POSITIVE);
        mouseMove(400,385);
        driverSleep(5000);
    }


    public void accessVideoResult() {
        logger.log(LoggerManagerLevel.INFO, "Navigate to video product ...", false);
        mouseMove(400,385);
        click(VIDEO_PRODUCT);
        addPatternWithSimilarity(PathConstants.MOUSE_DOWN_PATH, "Section3_NavigationStart", ImageSearchTypes.POSITIVE, 0.99f);
        addPatternWithSimilarity(PathConstants.MOUSE_AND_ENTER_UP_PATH, "Section3_NavigationStart", ImageSearchTypes.POSITIVE, 0.99f);
        addPattern(NAVBAR, new Rectangle(0, 0, 200, 200), "Section3_firstNonBlank", ImageSearchTypes.BACKGROUND_POSITIVE);
        addPatternWithSimilarity(VIDEO_RESULT, "Section3_heroElement", ImageSearchTypes.POSITIVE, 0.40f);
        addPattern(PathConstants.LOAD_DONE_PATH, "Section3_lastPaint", ImageSearchTypes.POSITIVE);
        driverSleep(3000);
    }

    public void backAction() {
        logger.log(LoggerManagerLevel.INFO, "Navigate back ...", false);
        navigateBack();
        WebElement prod = getElements(VIDEO_PRODUCT).get(0);
        addPattern(PathConstants.LOAD_PENDING_PATH, "Section4_NavigationStart", ImageSearchTypes.POSITIVE);
        addPattern(FIRST_RESULT, "Section4_firstNonBlank", ImageSearchTypes.POSITIVE);
        addPattern(FIRST_RESULT, "Section4_heroElement", ImageSearchTypes.POSITIVE);
        addPattern(PathConstants.LOAD_DONE_PATH, "Section4_lastPaint", ImageSearchTypes.POSITIVE);
        waitForElementToDisplay(prod);
    }

    public void accessBookResult() {
        logger.log(LoggerManagerLevel.INFO, "Navigate to book product ...", false);
        click(BOOK_PRODUCT);
        addPatternWithSimilarity(PathConstants.MOUSE_DOWN_PATH, "Section5_NavigationStart", ImageSearchTypes.POSITIVE, 0.99f);
        addPatternWithSimilarity(PathConstants.MOUSE_AND_ENTER_UP_PATH, "Section5_NavigationStart", ImageSearchTypes.POSITIVE, 0.99f);
        addPattern(NAVBAR, new Rectangle(0, 0, 200, 200), "Section5_firstNonBlank", ImageSearchTypes.BACKGROUND_POSITIVE);
        addPattern(BOOK_RESULT, "Section5_heroElement", ImageSearchTypes.POSITIVE);
        addPattern(PathConstants.LOAD_DONE_PATH, "Section5_lastPaint", ImageSearchTypes.POSITIVE);
        driverSleep(2000);
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