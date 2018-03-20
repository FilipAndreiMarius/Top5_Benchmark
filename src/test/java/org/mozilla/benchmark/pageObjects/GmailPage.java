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


/**
 * Created by andrei.filip on 10/30/2017.
 */
public class GmailPage extends BasePage {

    private static final LoggerManager logger = new LoggerManager(GmailPage.class.getName());

    //section 1
    private By STAR_RATE = By.xpath("//span[contains(@title,'Not starred')]");

    //section 2
    private By HYPERLINK_INSIDE_MAIL = By.className("m_-7793005015168254029m_3189775553631395212video-title-font-class");
    private By YOUTUBE_LOGO = By.xpath("//a[contains(@id,'logo')]");
    private Color FIRST_VIDEO_FRAME = ColorManager.getColorFromString("#000000");
    private By COMMENTS_SORT_BY = By.id("icon-label");

    public GmailPage(int runs, String testName, PageNavigationTypes navigationType) {
        super(runs, testName, navigationType);
    }

    public void navigateToHomePage() {
        addPattern(WebPageConstants.HOME_PAGE_PATTERN, "startingPoint", ImageSearchTypes.POSITIVE);
        logger.log(LoggerManagerLevel.INFO, "Accessing Gmail ...", false);
        navigateToUrl(WebPageConstants.GMAIL_URL);
        addPattern(PathConstants.LOAD_PENDING_PATH, "Section1_NavigationStart", ImageSearchTypes.POSITIVE);
        addPattern(PathConstants.GMAIL_LOADING_PATH, "Section1_firstNonBlank", ImageSearchTypes.POSITIVE);
        addPattern(STAR_RATE, "Section1_heroElement", ImageSearchTypes.POSITIVE);
        addPattern(PathConstants.LOAD_DONE_PATH, "Section1_lastPaint", ImageSearchTypes.POSITIVE);

    }

    public void accessYoutubeLink() {
        navigateToUrl(WebPageConstants.GMAIL_YOUTUBE_LINK);
        click(HYPERLINK_INSIDE_MAIL);
        changeFocusToLastOpenedTab();
        addPatternWithSimilarity(PathConstants.MOUSE_DOWN_PATH, "Section2_NavigationStart", ImageSearchTypes.POSITIVE, 0.99f);
        addPatternWithSimilarity(PathConstants.MOUSE_AND_ENTER_UP_PATH, "Section2_NavigationStart", ImageSearchTypes.POSITIVE, 0.99f);
        WebElement logoYoutube = getElements(YOUTUBE_LOGO).get(0);
        addPattern(logoYoutube, "Section2_firstNonBlank", ImageSearchTypes.POSITIVE);
        addPattern(FIRST_VIDEO_FRAME, new Rectangle(394, 203, 2, 1), "Section2_heroElement", ImageSearchTypes.BACKGROUND_NEGATIVE);
        addPattern(COMMENTS_SORT_BY, "Section2_lastPaint", ImageSearchTypes.POSITIVE);
        addPattern(PathConstants.LOAD_DONE_PATH, "Section2_lastPaint", ImageSearchTypes.POSITIVE);
        waitForElementToDisplay(COMMENTS_SORT_BY);
    }

    public void runAllScenarios() {
        navigateToHomePage();
        accessYoutubeLink();
        resetRun();
    }
}




