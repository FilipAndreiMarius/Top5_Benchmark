package org.mozilla.benchmark.pageObjects;

import org.mozilla.benchmark.constants.PathConstants;
import org.mozilla.benchmark.constants.WebPageConstants;
import org.mozilla.benchmark.objects.ImageSearchTypes;
import org.mozilla.benchmark.objects.LoggerManagerLevel;
import org.mozilla.benchmark.objects.PageNavigationTypes;
import org.mozilla.benchmark.utils.BasePage;
import org.mozilla.benchmark.utils.LoggerManager;
import org.openqa.selenium.By;
import org.mozilla.benchmark.utils.ColorManager;

import java.awt.*;


/**
 * Created by andrei.filip on 10/30/2017.
 */
public class GooglePage extends BasePage {

    private static final LoggerManager logger = new LoggerManager(GooglePage.class.getName());
    private Color TOP_BAR_BACKGROUND = ColorManager.getColorFromString("#fafafa");
    private Color IMAGES_SELECTED = ColorManager.getColorFromString("#1a72e8");
    private By GOOGLE_LOGO = By.id("hplogo");
    private By GOOGLE_SEARCH_BAR = By.id("lst-ib");
    private By IMAGES = By.linkText("Images");

    //sidebar images
    private By SIDEBAR_IMAGE_1 = By.id("uid_dimg_3");
    private By SIDEBAR_IMAGE_2 = By.id("uid_dimg_4");
    private By SIDEBAR_IMAGE_3 = By.id("uid_dimg_5");
    private By SIDEBAR_IMAGE_4 = By.id("uid_dimg_6");
    private By SIDEBAR_IMAGE_5 = By.id("uid_dimg_7");

    //top stories
    private By TOP_STORIES_IMAGE_1= By.id("uid_dimg_0");
    private By TOP_STORIES_IMAGE_2= By.id("uid_dimg_1");
    private By TOP_STORIES_IMAGE_3= By.id("uid_dimg_2");

    //first row of image search results
    private By FIRST_ROW_IMAGE_RESULTS = By.xpath("//*[@data-row='0']");

    public GooglePage(int runs, String testName, PageNavigationTypes navigationType) {
        super(runs, testName, navigationType);
    }

    public void navigateToHomePage() {
        logger.log(LoggerManagerLevel.INFO, "Accessing Google ...", false);
        navigateToUrl(WebPageConstants.GSEARCH_URL);
        addPattern(GOOGLE_LOGO, "startingPoint", ImageSearchTypes.POSITIVE);
        addPattern(PathConstants.LOAD_DONE_PATH, "startingPoint", ImageSearchTypes.POSITIVE);
    }

    public void search() {

        logger.log(LoggerManagerLevel.INFO, "Searching [" + WebPageConstants.SEARCH_ITEM + "] ...", false);
        sendKeysAndPressEnter(GOOGLE_SEARCH_BAR, WebPageConstants.SEARCH_ITEM);
        addPatternWithSimilarity(PathConstants.ENTER_DOWN_PATH, "Section1_NavigationStart", ImageSearchTypes.POSITIVE, 0.99f);
        addPattern(TOP_BAR_BACKGROUND, new Rectangle(2, 80, 20, 2), "Section1_firstNonBlank", ImageSearchTypes.BACKGROUND_POSITIVE);
        addPattern(SIDEBAR_IMAGE_1,"Section1_heroElement", ImageSearchTypes.POSITIVE);
        addPattern(SIDEBAR_IMAGE_2,"Section1_heroElement", ImageSearchTypes.POSITIVE);
        addPattern(SIDEBAR_IMAGE_3,"Section1_heroElement", ImageSearchTypes.POSITIVE);
        addPattern(SIDEBAR_IMAGE_4,"Section1_heroElement", ImageSearchTypes.POSITIVE);
        addPattern(SIDEBAR_IMAGE_5,"Section1_heroElement", ImageSearchTypes.POSITIVE);
        addPattern(TOP_STORIES_IMAGE_1,"Section1_heroElement", ImageSearchTypes.POSITIVE);
        addPattern(TOP_STORIES_IMAGE_2,"Section1_heroElement", ImageSearchTypes.POSITIVE);
        addPattern(TOP_STORIES_IMAGE_3,"Section1_heroElement", ImageSearchTypes.POSITIVE);
        addPattern(PathConstants.LOAD_DONE_PATH, "Section1_lastPaint", ImageSearchTypes.POSITIVE);
        driverSleep(1000);
    }

    public void accessImage() {
        logger.log(LoggerManagerLevel.INFO, "Selecting [Images] section ...", false);
        click(IMAGES);
        addPatternWithSimilarity(PathConstants.MOUSE_DOWN_PATH, "Section2_NavigationStart", ImageSearchTypes.POSITIVE, 0.99f);
        addPatternWithSimilarity(PathConstants.MOUSE_AND_ENTER_UP_PATH, "Section2_NavigationStart", ImageSearchTypes.POSITIVE, 0.99f);
        addPattern(IMAGES_SELECTED, new Rectangle(204, 194, 20, 1), "Section2_firstNonBlank", ImageSearchTypes.BACKGROUND_POSITIVE);
        addPattern(FIRST_ROW_IMAGE_RESULTS, "Section2_heroElement", ImageSearchTypes.POSITIVE);
        addPattern(PathConstants.LOAD_DONE_PATH, "Section2_lastPaint", ImageSearchTypes.POSITIVE);
        driverSleep(1000);
    }

    public void runAllScenarios() {
        navigateToHomePage();
        search();
        accessImage();
    }
}


