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
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.util.List;


/**
 * Created by andrei.filip on 10/30/2017.
 */
public class YoutubePage extends BasePage{

    private static final LoggerManager logger = new LoggerManager(YoutubePage.class.getName());

    private By YOUTUBE_LOGO=By.xpath("//a[contains(@id,'logo')]");

    //Section 1
    private Color THUMBNAIL_PRESENCE = ColorManager.getColorFromString("#fafafa");
    private Color THUMBNAIL_PRESENCE2 = ColorManager.getColorFromString("#e3e3e3");
    private Color FAVICON_YOUTUBE = ColorManager.getColorFromString("#fe0000");

    //Section 2
    private By YOUTUBE_TRENDING_LINK = By.xpath("//span[text()='Trending']/ancestor::ytd-guide-entry-renderer");
    private Color TRENDING_HOME = ColorManager.getColorFromString("#888888");

    //Section 3
    private By YOUTUBE_SEARCH_BAR = By.name("search_query");

    //Section 4
    private Color VIDEO_FIRST_PAINT = ColorManager.getColorFromString("#000000");
    private By VIDEO_TEST = By.cssSelector("ytd-video-renderer.ytd-item-section-renderer:nth-child(2)");

    //Section 5
    private By YOUTUBE_SECOND_VIDEO = By.xpath("//*[@class='yt-simple-endpoint style-scope ytd-compact-video-renderer']");

    public YoutubePage(int runs, String testName, PageNavigationTypes navigationType) {
        super(runs, testName, navigationType);
    }

    public void accessYoutube() {
        navigateToUrl(WebPageConstants.HOME_PAGE_URL);
        addPattern(WebPageConstants.HOME_PAGE_PATTERN, "startingPoint", ImageSearchTypes.POSITIVE);
        logger.log(LoggerManagerLevel.INFO, "Accessing Youtube...", false);
        navigateToUrl(WebPageConstants.YOUTUBE_URL);
        addPattern(PathConstants.LOAD_PENDING_PATH, "Section1_navigationStart", ImageSearchTypes.POSITIVE);
        addPattern(YOUTUBE_LOGO, "Section1_firstNonBlank", ImageSearchTypes.POSITIVE);
        addPattern(THUMBNAIL_PRESENCE2, new Rectangle(532, 260, 2, 2), "Section1_heroElement", ImageSearchTypes.BACKGROUND_POSITIVE);
        addPattern(THUMBNAIL_PRESENCE2, new Rectangle(750, 260, 2, 2), "Section1_heroElement", ImageSearchTypes.BACKGROUND_POSITIVE);
        addPattern(THUMBNAIL_PRESENCE2, new Rectangle(966, 260, 2, 2), "Section1_heroElement", ImageSearchTypes.BACKGROUND_POSITIVE);
        addPattern(THUMBNAIL_PRESENCE2, new Rectangle(1180, 260, 2, 2), "Section1_heroElement", ImageSearchTypes.BACKGROUND_POSITIVE);
        addPattern(THUMBNAIL_PRESENCE2, new Rectangle(1390, 260, 2, 2), "Section1_heroElement", ImageSearchTypes.BACKGROUND_POSITIVE);
        addPattern(THUMBNAIL_PRESENCE2, new Rectangle(1606, 260, 2, 2), "Section1_heroElement", ImageSearchTypes.BACKGROUND_POSITIVE);
        addPattern(THUMBNAIL_PRESENCE2, new Rectangle(532, 500, 2, 2), "Section1_heroElement", ImageSearchTypes.BACKGROUND_POSITIVE);
        addPattern(THUMBNAIL_PRESENCE2, new Rectangle(750, 500, 2, 2), "Section1_heroElement", ImageSearchTypes.BACKGROUND_POSITIVE);
        addPattern(THUMBNAIL_PRESENCE2, new Rectangle(966, 500, 2, 2), "Section1_heroElement", ImageSearchTypes.BACKGROUND_POSITIVE);
        addPattern(THUMBNAIL_PRESENCE2, new Rectangle(1180, 500, 2, 2), "Section1_heroElement", ImageSearchTypes.BACKGROUND_POSITIVE);
        addPattern(THUMBNAIL_PRESENCE2, new Rectangle(1390, 500, 2, 2), "Section1_heroElement", ImageSearchTypes.BACKGROUND_POSITIVE);
        addPattern(THUMBNAIL_PRESENCE2, new Rectangle(1606, 500, 2, 2), "Section1_heroElement", ImageSearchTypes.BACKGROUND_POSITIVE);
        addPattern(THUMBNAIL_PRESENCE, new Rectangle(532, 260, 2, 2), "Section1_heroElement", ImageSearchTypes.BACKGROUND_POSITIVE);
        addPattern(THUMBNAIL_PRESENCE, new Rectangle(750, 260, 2, 2), "Section1_heroElement", ImageSearchTypes.BACKGROUND_POSITIVE);
        addPattern(THUMBNAIL_PRESENCE, new Rectangle(966, 260, 2, 2), "Section1_heroElement", ImageSearchTypes.BACKGROUND_POSITIVE);
        addPattern(THUMBNAIL_PRESENCE, new Rectangle(1180, 260, 2, 2), "Section1_heroElement", ImageSearchTypes.BACKGROUND_POSITIVE);
        addPattern(THUMBNAIL_PRESENCE, new Rectangle(1390, 260, 2, 2), "Section1_heroElement", ImageSearchTypes.BACKGROUND_POSITIVE);
        addPattern(THUMBNAIL_PRESENCE, new Rectangle(1606, 260, 2, 2), "Section1_heroElement", ImageSearchTypes.BACKGROUND_POSITIVE);
        addPattern(THUMBNAIL_PRESENCE, new Rectangle(532, 500, 2, 2), "Section1_heroElement", ImageSearchTypes.BACKGROUND_POSITIVE);
        addPattern(THUMBNAIL_PRESENCE, new Rectangle(750, 500, 2, 2), "Section1_heroElement", ImageSearchTypes.BACKGROUND_POSITIVE);
        addPattern(THUMBNAIL_PRESENCE, new Rectangle(966, 500, 2, 2), "Section1_heroElement", ImageSearchTypes.BACKGROUND_POSITIVE);
        addPattern(THUMBNAIL_PRESENCE, new Rectangle(1180, 500, 2, 2), "Section1_heroElement", ImageSearchTypes.BACKGROUND_POSITIVE);
        addPattern(THUMBNAIL_PRESENCE, new Rectangle(1390, 500, 2, 2), "Section1_heroElement", ImageSearchTypes.BACKGROUND_POSITIVE);
        addPattern(THUMBNAIL_PRESENCE, new Rectangle(1606, 500, 2, 2), "Section1_heroElement", ImageSearchTypes.BACKGROUND_POSITIVE);
        addPattern(THUMBNAIL_PRESENCE, new Rectangle(532, 260, 2, 2), "Section1_heroElement", ImageSearchTypes.BACKGROUND_NEGATIVE);
        addPattern(THUMBNAIL_PRESENCE, new Rectangle(750, 260, 2, 2), "Section1_heroElement", ImageSearchTypes.BACKGROUND_NEGATIVE);
        addPattern(THUMBNAIL_PRESENCE, new Rectangle(966, 260, 2, 2), "Section1_heroElement", ImageSearchTypes.BACKGROUND_NEGATIVE);
        addPattern(THUMBNAIL_PRESENCE, new Rectangle(1180, 260, 2, 2), "Section1_heroElement", ImageSearchTypes.BACKGROUND_NEGATIVE);
        addPattern(THUMBNAIL_PRESENCE, new Rectangle(1390, 260, 2, 2), "Section1_heroElement", ImageSearchTypes.BACKGROUND_NEGATIVE);
        addPattern(THUMBNAIL_PRESENCE, new Rectangle(1606, 260, 2, 2), "Section1_heroElement", ImageSearchTypes.BACKGROUND_NEGATIVE);
        addPattern(THUMBNAIL_PRESENCE, new Rectangle(532, 500, 2, 2), "Section1_heroElement", ImageSearchTypes.BACKGROUND_NEGATIVE);
        addPattern(THUMBNAIL_PRESENCE, new Rectangle(750, 500, 2, 2), "Section1_heroElement", ImageSearchTypes.BACKGROUND_NEGATIVE);
        addPattern(THUMBNAIL_PRESENCE, new Rectangle(966, 500, 2, 2), "Section1_heroElement", ImageSearchTypes.BACKGROUND_NEGATIVE);
        addPattern(THUMBNAIL_PRESENCE, new Rectangle(1180, 500, 2, 2), "Section1_heroElement", ImageSearchTypes.BACKGROUND_NEGATIVE);
        addPattern(THUMBNAIL_PRESENCE, new Rectangle(1390, 500, 2, 2), "Section1_heroElement", ImageSearchTypes.BACKGROUND_NEGATIVE);
        addPattern(THUMBNAIL_PRESENCE, new Rectangle(1606, 500, 2, 2), "Section1_heroElement", ImageSearchTypes.BACKGROUND_NEGATIVE);
        addPattern(FAVICON_YOUTUBE, new Rectangle(12, 17, 2, 2), "Section1_lastPaint", ImageSearchTypes.BACKGROUND_POSITIVE);
        addPattern(PathConstants.LOAD_DONE_PATH, "Section1_lastPaint", ImageSearchTypes.POSITIVE);
        driverSleep(4000);
    }

    public void accessTrending() {
        click(YOUTUBE_TRENDING_LINK);
        waitUntilTitleContains("Trending");
        addPatternWithSimilarity(PathConstants.MOUSE_DOWN_PATH, "Section2_NavigationStart", ImageSearchTypes.POSITIVE, 0.99f);
        addPatternWithSimilarity(PathConstants.MOUSE_AND_ENTER_UP_PATH, "Section2_NavigationStart", ImageSearchTypes.POSITIVE, 0.99f);
        addPattern(TRENDING_HOME, new Rectangle(35, 158, 2, 2), "Section2_firstNonBlank", ImageSearchTypes.BACKGROUND_POSITIVE);
        addPattern(THUMBNAIL_PRESENCE, new Rectangle(430, 160, 2, 2), "Section2_heroElement", ImageSearchTypes.BACKGROUND_NEGATIVE);
        addPattern(THUMBNAIL_PRESENCE, new Rectangle(430, 314, 2, 2), "Section2_heroElement", ImageSearchTypes.BACKGROUND_NEGATIVE);
        addPattern(THUMBNAIL_PRESENCE, new Rectangle(430, 468, 2, 2), "Section2_heroElement", ImageSearchTypes.BACKGROUND_NEGATIVE);
        addPattern(THUMBNAIL_PRESENCE, new Rectangle(430, 622, 2, 2), "Section2_heroElement", ImageSearchTypes.BACKGROUND_NEGATIVE);
        addPattern(THUMBNAIL_PRESENCE, new Rectangle(430, 776, 2, 2), "Section2_heroElement", ImageSearchTypes.BACKGROUND_NEGATIVE);
        addPattern(THUMBNAIL_PRESENCE, new Rectangle(430, 930, 2, 2), "Section2_heroElement", ImageSearchTypes.BACKGROUND_NEGATIVE);
        addPattern(PathConstants.LOAD_DONE_PATH, "Section2_lastPaint", ImageSearchTypes.POSITIVE);
    }

    public void searchInTrending() {
        sendKeysAndPressEnter(YOUTUBE_SEARCH_BAR, WebPageConstants.YOUTUBE_ITEM);
        waitUntilTitleContains("sarah jeffery recorder");
        addPatternWithSimilarity(PathConstants.ENTER_DOWN_PATH, "Section3_NavigationStart", ImageSearchTypes.POSITIVE, 0.99f);
        addPattern(THUMBNAIL_PRESENCE, new Rectangle(4, 80, 2, 2), "Section3_Before_First_Non", ImageSearchTypes.BACKGROUND_POSITIVE);
        addPattern(YOUTUBE_LOGO, "Section3_firstNonBlank", ImageSearchTypes.POSITIVE);
        addPattern(THUMBNAIL_PRESENCE, new Rectangle(440, 400, 2, 2), "Section3_heroElement", ImageSearchTypes.BACKGROUND_NEGATIVE);
        addPattern(PathConstants.LOAD_DONE_PATH, "Section3_lastPaint", ImageSearchTypes.POSITIVE);
    }

    public void playFirstVideo() {
        click(VIDEO_TEST);
        addPatternWithSimilarity(PathConstants.MOUSE_DOWN_PATH, "Section4_NavigationStart", ImageSearchTypes.POSITIVE, 0.99f);
        addPatternWithSimilarity(PathConstants.MOUSE_AND_ENTER_UP_PATH, "Section4_NavigationStart", ImageSearchTypes.POSITIVE, 0.99f);
        addPattern(VIDEO_FIRST_PAINT, new Rectangle(700, 360, 20, 20), "Section4_Video_firstPaint", ImageSearchTypes.BACKGROUND_POSITIVE);
        addPattern(VIDEO_FIRST_PAINT, new Rectangle(700, 300, 20, 20), "Section4_Video_firstPaint", ImageSearchTypes.BACKGROUND_POSITIVE);
        addPattern(VIDEO_FIRST_PAINT, new Rectangle(700, 360, 2, 2), "Section4_Video_Playback_Start", ImageSearchTypes.BACKGROUND_NEGATIVE);
        addPattern(PathConstants.LOAD_DONE_PATH, "Section4_lastPaint", ImageSearchTypes.POSITIVE);
        driverSleep(3000);
    }

    public void playSecondVideo()  {
        click(YOUTUBE_SECOND_VIDEO);
        addPatternWithSimilarity(PathConstants.MOUSE_DOWN_PATH, "Section5_NavigationStart", ImageSearchTypes.POSITIVE, 0.99f);
        addPatternWithSimilarity(PathConstants.MOUSE_AND_ENTER_UP_PATH, "Section5_NavigationStart", ImageSearchTypes.POSITIVE, 0.99f);
        addPattern(THUMBNAIL_PRESENCE, new Rectangle(4, 80, 2, 2), "Section3_Before_First_Non", ImageSearchTypes.BACKGROUND_POSITIVE);
        addPattern(VIDEO_FIRST_PAINT, new Rectangle(700, 360, 20, 20), "Section5_Video_firstPaint", ImageSearchTypes.BACKGROUND_POSITIVE);
        addPattern(VIDEO_FIRST_PAINT, new Rectangle(700, 300, 20, 20), "Section5_Video_firstPaint", ImageSearchTypes.BACKGROUND_POSITIVE);
        addPattern(VIDEO_FIRST_PAINT, new Rectangle(700, 360, 2, 2), "Section5_Video_Playback_Start", ImageSearchTypes.BACKGROUND_NEGATIVE);
        addPattern(PathConstants.LOAD_DONE_PATH, "Section5_lastPaint", ImageSearchTypes.POSITIVE);
    }

    public void runAllScenarios()  {
        accessYoutube();
        accessTrending();
        searchInTrending();
        playFirstVideo();
        playSecondVideo();
        resetRun();
    }
}