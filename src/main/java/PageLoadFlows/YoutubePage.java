package PageLoadFlows;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static Constants.Page_Objects_Constants.YoutubeItem;
import static Constants.Page_Objects_Constants.YoutubeUrl;

/**
 * Created by andrei.filip on 10/30/2017.
 */
public class YoutubePage {
    static WebDriver driver;

    // By trendingElment=By.xpath("//*[class='style-scope ytd-guide-section-renderer']");
    By trendingElment = By.className("title style-scope ytd-guide-entry-renderer");

    By YoutubeSearchBar = By.id("search-input");
    By YoutubeSearchButton = By.id("search-icon-legacy");
    By youtubeFirstVideo = By.cssSelector("a[href*='/watch?v=G2944Wc2V_4']");
    By youtube2ndVideo = By.xpath("//*[@class='yt-simple-endpoint style-scope ytd-compact-video-renderer']");


    public YoutubePage(WebDriver driver) {
        this.driver = driver;

    }

    public void accessYoutube() {
        driver.get(YoutubeUrl);
    }

    public void accessTrending() throws InterruptedException {

    /*   (new WebDriverWait(driver, 5))
               .until(ExpectedConditions.presenceOfElementLocated(trendingElment));*/
        driver.get("https://www.youtube.com/feed/trending");

    }

    public void searchInTrending() throws InterruptedException {

        (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.visibilityOfElementLocated(YoutubeSearchBar));
        driver.findElement(YoutubeSearchBar).sendKeys(YoutubeItem);
        (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.presenceOfElementLocated(YoutubeSearchButton));
        driver.findElement(YoutubeSearchButton).click();
    }

    public void playVideo() {
        (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.presenceOfElementLocated(youtubeFirstVideo));
        driver.findElement(youtubeFirstVideo).click();
    }

    public void play2ndVideo() throws InterruptedException {
        (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.presenceOfElementLocated(youtube2ndVideo));
        driver.findElement(youtube2ndVideo).click();

    }

    public void runAllScenarios() throws InterruptedException {
        accessYoutube();
        accessTrending();
        searchInTrending();
        playVideo();
        play2ndVideo();
    }

}
