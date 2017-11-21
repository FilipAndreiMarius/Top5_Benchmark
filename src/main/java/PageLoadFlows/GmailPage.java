package PageLoadFlows;

import Constants.Page_Objects_Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.net.MalformedURLException;

import static Constants.Page_Objects_Constants.GmailUrl;
import static Constants.Page_Objects_Constants.YoutubLink;

/**
 * Created by andrei.filip on 10/30/2017.
 */
public class GmailPage  {


    static WebDriver driver;
    static By userNameElement = By.id("identifierId");
    static By nextButton = By.id("identifierNext");
    static By passwordElement = By.id("password");
    static By passwordnextButton = By.id("passwordNext");
    static By tabElement = By.id("gbwa");
    static By GmailtabElement = By.id("gb23");
    static By youtubeLinkElement = By.className("m_-7793005015168254029m_3189775553631395212video-title-font-class");


    public GmailPage(WebDriver driver) {
        this.driver = driver;
    }


    public void loginGmail() throws InterruptedException, MalformedURLException, AWTException {
     /*   Notifications a=new Notifications("Gmail","GmailUrl is accessed") ;
        a.run();
       // a.stop();
 */       driver.get(GmailUrl);
        (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.presenceOfElementLocated(userNameElement));
        driver.findElement(userNameElement).sendKeys(Page_Objects_Constants.userName);
        new WebDriverWait(driver, 5);
        driver.findElement(nextButton).click();
        Thread.sleep(4000);
        driver.findElement(passwordElement).sendKeys(Page_Objects_Constants.pass);
        driver.findElement(passwordnextButton).click();

    }

    public static void accessEmail() throws MalformedURLException, AWTException {
        (new WebDriverWait(driver, 6))
                .until(ExpectedConditions.presenceOfElementLocated(tabElement));
        driver.findElement(tabElement).click();

        (new WebDriverWait(driver, 6))
                .until(ExpectedConditions.presenceOfElementLocated(GmailtabElement));
        driver.findElement(GmailtabElement).click();

    }

    public static void accessYoutubeLink() {
        driver.get(YoutubLink);
        (new WebDriverWait(driver, 6))
                .until(ExpectedConditions.presenceOfElementLocated(youtubeLinkElement));
        driver.findElement(youtubeLinkElement).click();
    }


}




