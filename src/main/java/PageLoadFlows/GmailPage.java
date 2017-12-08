package PageLoadFlows;

import Constants.Page_Objects_Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import static Constants.Page_Objects_Constants.*;

/**
 * Created by andrei.filip on 10/30/2017.
 */
public class GmailPage extends Thread  {

    static int nrRuns;
    static WebDriver driver;
    static By userNameElement = By.id("identifierId");
    static By nextButton = By.id("identifierNext");
    static By passwordElement = By.id("password");
    static By passwordnextButton = By.id("passwordNext");
    static By tabElement = By.id("gbwa");
    static By GmailtabElement = By.id("gb23");
    static By youtubeLinkElement = By.className("m_-7793005015168254029m_3189775553631395212video-title-font-class");


    public GmailPage(int nrRuns) {
        this.nrRuns=nrRuns;
        System.setProperty("webdriver.gecko.driver", "C:\\Commons\\geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
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
        new WebDriverWait(driver, 4);
        Thread.sleep(3000);
        new WebDriverWait(driver, 6)
                .until(ExpectedConditions.elementToBeClickable(passwordElement));
         WebElement el=driver.findElement(passwordElement);
         el.sendKeys(pass);
        driver.findElement(passwordnextButton).click();

    }

    public static void accessEmail() throws MalformedURLException, AWTException, InterruptedException {
        (new WebDriverWait(driver, 6))
                .until(ExpectedConditions.presenceOfElementLocated(tabElement));
        driver.findElement(tabElement).click();

        (new WebDriverWait(driver, 6))
                .until(ExpectedConditions.presenceOfElementLocated(GmailtabElement));
        driver.findElement(GmailtabElement).click();
        Thread.sleep(3000);

    }

    public static  void accessGmail() throws InterruptedException {
        System.out.print("Gmail page is accessed:");
        driver.get(Gmail);
        Thread.sleep(3000);

    }

    public static void accessYoutubeLink() throws InterruptedException {
        driver.get(YoutubLink);
        (new WebDriverWait(driver, 6))
                .until(ExpectedConditions.presenceOfElementLocated(youtubeLinkElement));
        driver.findElement(youtubeLinkElement).click();
        Thread.sleep(5000);
        ArrayList tabs2 = new ArrayList (driver.getWindowHandles());
        driver.switchTo().window((String) tabs2.get(1));
        Thread.sleep(3000);
        driver.close();
        driver.switchTo().window((String) tabs2.get(0));



    }

    public static void ghostPage() throws InterruptedException {
        System.out.print("Ghost Page page is accessed:");
        driver.get(ghostPage);
        Thread.sleep(2000);


    }



    public void runAllScenarios() throws MalformedURLException, AWTException, InterruptedException {
        loginGmail();
        accessEmail();
        accessYoutubeLink();
    }


    public void run() {
        try {
            runAllScenarios();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (AWTException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < nrRuns; i++) {
            try {
                System.out.println("Thread- Started"
                        + Thread.currentThread().getName());
                ghostPage();
                accessGmail();
                accessYoutubeLink();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Thread- Ended"
                        + Thread.currentThread().getName());

            }
        }
    }


    public  static  void  main(String args[]) throws AWTException, InterruptedException, MalformedURLException {

        Thread g = new GmailPage(3);
        g.start();


    }


}




