package org.mozilla.benchmark.pageObjects;

import org.mozilla.benchmark.constants.WebPageConstants;
import org.mozilla.benchmark.objects.LoggerManagerLevel;
import org.mozilla.benchmark.objects.PageNavigationTypes;
import org.mozilla.benchmark.utils.BasePage;
import org.mozilla.benchmark.utils.LoggerManager;
import org.openqa.selenium.By;



/**
 * Created by andrei.filip on 10/30/2017.
 */
public class YoutubePage extends BasePage{

    private static final LoggerManager logger = new LoggerManager(YoutubePage.class.getName());

    private By YOUTUBE_SEARCH_BAR = By.name("search_query");
    private By YOUTUBE_SEARCH_BUTTON = By.id("search-icon-legacy");
    private By YOUTUBE_FIRST_VIDEO = By.cssSelector("a[href*='/watch?v=G2944Wc2V_4']");
    private By YOUTUBE_SECOND_VIDEO = By.xpath("//*[@class='yt-simple-endpoint style-scope ytd-compact-video-renderer']");
    private By YOUTUBE_TRENDING_LINK = By.xpath("//*[contains(text(), 'Trending')]");

    public YoutubePage(int runs, String testName, PageNavigationTypes navigationType) {
        super(runs, testName, navigationType);
    }

    public void accessYoutube() {
        logger.log(LoggerManagerLevel.INFO, "Accessing Youtube...", false);
        navigateToUrl(WebPageConstants.YOUTUBE_URL);
    }

    public void accessTrending() {

       click(YOUTUBE_TRENDING_LINK);
    }

    public void searchInTrending() {
        sendKeys(YOUTUBE_SEARCH_BAR, WebPageConstants.YOUTUBE_ITEM);
        click(YOUTUBE_SEARCH_BUTTON);
    }

    public void playFirstVideo() {
        click(YOUTUBE_FIRST_VIDEO);
        driverSleep(3000);
    }

    public void playSecondVideo()  {
        click(YOUTUBE_SECOND_VIDEO);
    }

    public void runAllScenarios()  {
        accessYoutube();
        accessTrending();
        searchInTrending();
        playFirstVideo();
        playSecondVideo();
    }
}



