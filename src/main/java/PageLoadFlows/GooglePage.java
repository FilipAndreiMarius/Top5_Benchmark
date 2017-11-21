package PageLoadFlows;

import VideoProcessor.VideoCapture;
import com.paulgavrikov.notification.Notification;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;

import static Constants.Page_Objects_Constants.GsearchUrl;
import static Constants.Page_Objects_Constants.searchItem;

/**
 * Created by andrei.filip on 10/30/2017.
 */
public class GooglePage extends Thread {

    static WebDriver driver;
    static int nrRuns;

    static By GoogleSearchBar = By.id("lst-ib");
    static By GoogleSearchButton = By.className("lsb");
    static By GoogleImage = By.xpath("//*[@class='q qs']");
    Boolean runTest=false;


    public GooglePage(int nrRuns) {
     this.nrRuns=nrRuns;
    }

    public static void accessImage() throws MalformedURLException, AWTException {

        (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.presenceOfElementLocated(GoogleImage));
        driver.findElement(GoogleImage).click();
    }


    public void setUp() throws IOException, InterruptedException {
        runTest=Utils.Utils.checkCpuLoad();
       if(runTest!=false) {
           System.setProperty("webdriver.gecko.driver", "C:\\Commons\\geckodriver.exe");
           driver = new FirefoxDriver();
           driver.manage().window().maximize();
       }
    }

    public void accessGsearch() throws MalformedURLException, AWTException, InterruptedException {
        System.out.print("GSearch page is accessed:");
        Notification notificationP = new Notification();
        notificationP.push("GPage", "GSearch page is accessed");
        driver.get(GsearchUrl);
        notificationP.push("123123123", "ads");


    }

    public void searchGoogle() throws MalformedURLException, AWTException {
        driver.findElement(GoogleSearchBar).sendKeys(searchItem);

        (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.presenceOfElementLocated(GoogleSearchButton));
        new Thread(new Notifications()).start();

        Actions actions = new Actions(driver);
        actions.sendKeys(driver.findElement(GoogleSearchButton), Keys.ENTER).build().perform();


    }

    public void runAllScenarios() throws MalformedURLException, AWTException, InterruptedException {
        accessGsearch();
        searchGoogle();
        accessImage();

    }

    public void quit() {
        driver.quit();
    }

    @Override
    public void run() {
        for (int i = 0; i < nrRuns; i++) {
            try {
                System.out.println("Thread- Started"
                        + Thread.currentThread().getName());
                setUp();
                runAllScenarios();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (AWTException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Thread- Ended"
                        + Thread.currentThread().getName());
                teardown();

            }
        }
    }

    public void teardown() {
        driver.quit();
    }

    public static void main(String args[]) throws IOException {

        Thread recordVideo = new VideoCapture("15", "40", "runVideo");
        recordVideo.start();

        Thread a = new GooglePage(1);
        a.start();

        try {
            recordVideo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread compress =new VideoCapture("compress");
        compress.start();


        try {
            compress.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread splitVideo =new VideoCapture("splitVideo");
        splitVideo.start();





    }
}


