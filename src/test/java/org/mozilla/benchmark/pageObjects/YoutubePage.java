package org.mozilla.benchmark.pageObjects;

import org.mozilla.benchmark.utils.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by andrei.filip on 10/30/2017.
 */
public class YoutubePage {

    private final WebDriver driver;

    private By trendingLocator = By.className("title style-scope ytd-guide-entry-renderer");
    private By youtubeSearchBarLocator = By.id("search-input");
    private By youtubeSearchButtonLocator = By.id("search-icon-legacy");
    private By youtubeFirstVideoLocator = By.cssSelector("a[href*='/watch?v=G2944Wc2V_4']");
    private By youtubeSecondVideoLocator = By.xpath("//*[@class='yt-simple-endpoint style-scope ytd-compact-video-renderer']");

    public YoutubePage(WebDriver driver) {
        this.driver = driver;
    }

    public void accessYoutube() {
        driver.get(Constants.PageObjects.YOUTUBE_URL);
    }

    public void accessTrending() {
        driver.get("https://www.youtube.com/feed/trending");
    }

    public void searchInTrending() throws InterruptedException {
        (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.visibilityOfElementLocated(youtubeSearchBarLocator));
        driver.findElement(youtubeSearchBarLocator).sendKeys(Constants.PageObjects.YOUTUBE_ITEM);
        (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.presenceOfElementLocated(youtubeSearchButtonLocator));
        driver.findElement(youtubeSearchButtonLocator).click();
    }

    public void playFirstVideo() {
        (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.presenceOfElementLocated(youtubeFirstVideoLocator));
        driver.findElement(youtubeFirstVideoLocator).click();
    }

    public void playSecondVideo() throws InterruptedException {
        (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.presenceOfElementLocated(youtubeSecondVideoLocator));
        driver.findElement(youtubeSecondVideoLocator).click();
    }

    public void runAllScenarios() throws InterruptedException {
        accessYoutube();
        accessTrending();
        searchInTrending();
        playFirstVideo();
        playSecondVideo();
    }
}
