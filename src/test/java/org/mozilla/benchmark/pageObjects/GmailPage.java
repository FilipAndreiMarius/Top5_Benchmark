package org.mozilla.benchmark.pageObjects;

import org.mozilla.benchmark.utils.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.net.MalformedURLException;


/**
 * Created by andrei.filip on 10/30/2017.
 */
public class GmailPage {

    private final WebDriver driver;
    private By userNameLocator = By.id("identifierId");
    private By nextButtonLocator = By.id("identifierNext");
    private By passwordLocator = By.id("password");
    private By passwordNextButtonLocator = By.id("passwordNext");
    private By tabLocator = By.id("gbwa");
    private By gmailTabLocator = By.id("gb23");
    private By youtubeLinkLocator = By.className("m_-7793005015168254029m_3189775553631395212video-title-font-class");

    public GmailPage(WebDriver driver) {
        this.driver = driver;
    }

    public void loginGmail() throws InterruptedException, MalformedURLException, AWTException {
        driver.get(Constants.PageObjects.GMAIL_URL);
        (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.presenceOfElementLocated(userNameLocator));
        driver.findElement(userNameLocator).sendKeys(Constants.PageObjects.USER_NAME);
        new WebDriverWait(driver, 5);
        driver.findElement(nextButtonLocator).click();
        Thread.sleep(4000);
        driver.findElement(passwordLocator).sendKeys(Constants.PageObjects.PASS);
        driver.findElement(passwordNextButtonLocator).click();
    }

    public void accessEmail() throws MalformedURLException, AWTException {
        (new WebDriverWait(driver, 6))
                .until(ExpectedConditions.presenceOfElementLocated(tabLocator));
        driver.findElement(tabLocator).click();

        (new WebDriverWait(driver, 6))
                .until(ExpectedConditions.presenceOfElementLocated(gmailTabLocator));
        driver.findElement(gmailTabLocator).click();
    }

    public void accessYoutubeLink() {
        driver.get(Constants.PageObjects.YOUTUBE_LINK);
        (new WebDriverWait(driver, 6))
                .until(ExpectedConditions.presenceOfElementLocated(youtubeLinkLocator));
        driver.findElement(youtubeLinkLocator).click();
    }
}




