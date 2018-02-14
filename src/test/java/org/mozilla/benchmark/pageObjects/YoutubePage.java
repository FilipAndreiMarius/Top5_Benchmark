package org.mozilla.benchmark.pageObjects;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mozilla.benchmark.objects.PageNavigationTypes;
import org.mozilla.benchmark.utils.BasePage;
import org.mozilla.benchmark.utils.Constants;
import org.mozilla.benchmark.utils.DriverUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;



/**
 * Created by andrei.filip on 10/30/2017.
 */
public class YoutubePage extends BasePage{

    private static final Logger logger = LogManager.getLogger(YoutubePage.class.getName());
    private int runs;
    private PageNavigationTypes navigationType;

    private By YOUTUBE_SEARCH_BAR = By.name("search_query");
    private By YOUTUBE_SEARCH_BUTTON = By.id("search-icon-legacy");
    private By YOUTUBE_FIRST_VIDEO = By.cssSelector("a[href*='/watch?v=G2944Wc2V_4']");
    private By YOUTUBE_SECOND_VIDEO = By.xpath("//*[@class='yt-simple-endpoint style-scope ytd-compact-video-renderer']");
    private By YOUTUBE_TRENDING_LINK = By.xpath("//*[contains(text(), 'Trending')]");

    public YoutubePage(int runs, PageNavigationTypes navigationType) {
        this.runs = runs;
        this.navigationType = navigationType;
    }

    public void accessYoutube() {
        logger.info("Accessing Youtube...");
        navigateToUrl(Constants.PageObjects.YOUTUBE_URL);
    }

    public void accessTrending() {

       click(YOUTUBE_TRENDING_LINK);
    }

    public void searchInTrending() {
        sendKeys(YOUTUBE_SEARCH_BAR, Constants.PageObjects.YOUTUBE_ITEM);
        click(YOUTUBE_SEARCH_BUTTON);
    }

    public void playFirstVideo() {
        WebElement firstVideo = getElement(YOUTUBE_FIRST_VIDEO);
        click(firstVideo);
        driverSleep(3000);
    }

    public void playSecondVideo()  {
        WebElement secondVideo = getElement(YOUTUBE_SECOND_VIDEO);
        click(secondVideo);
    }

    public void runAllScenarios()  {
        accessYoutube();
        accessTrending();
/*        searchInTrending();
        playFirstVideo();
        playSecondVideo();*/
    }

    @Override
    public void run() {
        for (int i = 0; i < this.runs; i++) {
            runAllScenarios();
        }
        DriverUtils.closeWebBrowser();
    }



}



