package org.mozilla.benchmark.pageObjects;

import org.mozilla.benchmark.constants.PathConstants;
import org.mozilla.benchmark.constants.WebPageConstants;
import org.mozilla.benchmark.objects.ImageSearchTypes;
import org.mozilla.benchmark.objects.LoggerManagerLevel;
import org.mozilla.benchmark.objects.PageNavigationTypes;
import org.mozilla.benchmark.utils.BasePage;
import org.mozilla.benchmark.utils.LoggerManager;
import org.mozilla.benchmark.utils.ColorManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.awt.*;

/**
 * Created by andrei.filip on 10/30/2017.
 */
public class FacebookPage extends BasePage {

    private static final LoggerManager logger = new LoggerManager(FacebookPage.class.getName());

    //Section 1
    private Color FAVICON_FACEBOOK = ColorManager.getColorFromString("#4167b1");
    private Color FB_HEADER = ColorManager.getColorFromString("#4167b1");
    private By POST_IMAGE = By.xpath("//a[contains(@href,'https://www.facebook.com/photo.php?fbid=318319665590394&set=a.110565376365825&type=3')]");

    //Section 2
    private Color FADE_OUT = ColorManager.getColorFromString("#eaebef");
    private By GROUP_MESSAGE_ICON= By.className("groupMessageIcon");
    private By GROUP_POST_IMAGE = By.xpath("//a[contains(@href,'https://www.facebook.com/photo.php?fbid=310049923063894&set=gm.487788225030162&type=3&ifg=1')]");

    //Section 4
    private By FOOTER = By.id("pagelet_rhc_footer");
    private By GROUP = By.xpath("//*[contains(text(),'Benchmark test group item first post')]");
    private By HOME_BUTTON = By.cssSelector("a[href*='https://www.facebook.com/?ref=tn_tnmn']");
    private By SECOND_USER = By.xpath("//*[contains(text(),'Bench MarkSecond')]");
    private Color PROFILE_BG = ColorManager.getColorFromString("#010101");



    public FacebookPage(int runs, String testName, PageNavigationTypes navigationType) {
        super(runs, testName, navigationType);
    }

    public void accessFacebook() {
        navigateToUrl(WebPageConstants.HOME_PAGE_URL);
        addPattern(WebPageConstants.HOME_PAGE_PATTERN, "startingPoint", ImageSearchTypes.POSITIVE);
        logger.log(LoggerManagerLevel.INFO, "Accessing Facebook ...", false);
        navigateToUrl(WebPageConstants.FACEBOOK_URL);
        WebElement post = getElements(POST_IMAGE).get(1);
        addPattern(PathConstants.LOAD_PENDING_PATH, "Section1_navigationStart", ImageSearchTypes.POSITIVE);
        addPattern(FB_HEADER, new Rectangle(0, 0, 200, 200), "Section1_firstNonBlank", ImageSearchTypes.BACKGROUND_POSITIVE);
        addPattern(post, "Section1_heroElement", ImageSearchTypes.POSITIVE);
        addPattern(FAVICON_FACEBOOK, new Rectangle(12, 18, 2, 2), "Section1_lastPaint", ImageSearchTypes.BACKGROUND_POSITIVE);
        addPattern(PathConstants.LOAD_DONE_PATH, "Section1_lastPaint", ImageSearchTypes.POSITIVE);
        driverSleep(2000);
    }

    public void accessGroup() {
        click(GROUP);
        addPatternWithSimilarity(PathConstants.MOUSE_DOWN_PATH, "Section2_NavigationStart", ImageSearchTypes.POSITIVE, 0.99f);
        addPatternWithSimilarity(PathConstants.MOUSE_AND_ENTER_UP_PATH, "Section2_NavigationStart", ImageSearchTypes.POSITIVE, 0.99f);
        addPattern(FADE_OUT, new Rectangle(650, 360, 20, 20), "Section2_firstNonBlank", ImageSearchTypes.BACKGROUND_POSITIVE);
        addPattern(GROUP_POST_IMAGE, "Section2_heroElement", ImageSearchTypes.POSITIVE);
        addPattern(PathConstants.LOAD_DONE_PATH, "Section2_lastPaint", ImageSearchTypes.POSITIVE);
        addPattern(GROUP_MESSAGE_ICON, "Section2_lastPaint", ImageSearchTypes.POSITIVE);
        driverSleep(3000);
    }

    public void homeLink() {
        click(HOME_BUTTON);
        addPatternWithSimilarity(PathConstants.MOUSE_DOWN_PATH, "Section3_NavigationStart", ImageSearchTypes.POSITIVE, 0.99f);
        addPatternWithSimilarity(PathConstants.MOUSE_AND_ENTER_UP_PATH, "Section3_NavigationStart", ImageSearchTypes.POSITIVE, 0.99f);
        addPattern(FADE_OUT, new Rectangle(650, 626, 2, 2), "Section3_firstNonBlank", ImageSearchTypes.BACKGROUND_POSITIVE);
        WebElement post = getElements(POST_IMAGE).get(1);
        addPattern(post, "Section3_heroElement", ImageSearchTypes.POSITIVE);
        addPattern(PathConstants.LOAD_DONE_PATH, "Section3_lastPaint", ImageSearchTypes.POSITIVE);
        driverSleep(2000);
    }

    public void accessUser() {
        click(SECOND_USER);
        mouseMove(400,385);
        addPatternWithSimilarity(PathConstants.MOUSE_DOWN_PATH, "Section4_NavigationStart", ImageSearchTypes.POSITIVE, 0.99f);
        addPatternWithSimilarity(PathConstants.MOUSE_AND_ENTER_UP_PATH, "Section4_NavigationStart", ImageSearchTypes.POSITIVE, 0.99f);
        addPattern(PROFILE_BG, new Rectangle(458, 140, 2, 2), "Section4_firstNonBlank", ImageSearchTypes.BACKGROUND_NEGATIVE);
        WebElement post = getElements(POST_IMAGE).get(1);
        addPattern(post, "Section4_heroElement", ImageSearchTypes.POSITIVE);
        addPattern(FOOTER, "Section4_lastPaint", ImageSearchTypes.POSITIVE);
        addPattern(PathConstants.LOAD_DONE_PATH, "Section4_lastPaint", ImageSearchTypes.POSITIVE);
        driverSleep(2000);
    }


    public void runAllScenarios() {
        accessFacebook();
        accessGroup();
        homeLink();
        accessUser();
        resetRun();
    }
}