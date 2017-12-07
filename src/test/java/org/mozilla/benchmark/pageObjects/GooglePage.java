package org.mozilla.benchmark.pageObjects;

import org.mozilla.benchmark.utils.ObjectTypes;
import org.mozilla.benchmark.videoProcessor.VideoCapture;
import org.mozilla.benchmark.utils.Constants;
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

/**
 * Created by andrei.filip on 10/30/2017.
 */
public class GooglePage extends Thread {

    private final WebDriver driver;
    private int runs;

    private By googleSearchBarLocator = By.id("lst-ib");
    private By googleSearchButtonLocator = By.className("lsb");
    private By googleImageLocator = By.xpath("//*[@class='q qs']");

    public GooglePage(int runs) {
        this.runs = runs;
        System.setProperty("webdriver.gecko.driver", "C:\\workspace\\geckoDriver\\geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
    }

    public void accessImage() {
        (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.presenceOfElementLocated(googleImageLocator));
        driver.findElement(googleImageLocator).click();
    }

    public void accessGsearch() {
        System.out.print("GSearch page is accessed:");
        driver.get(Constants.PageObjects.GSEARCH_URL);
    }

    public void searchGoogle() {
        try {
            driver.findElement(googleSearchBarLocator).sendKeys(Constants.PageObjects.SEARCH_ITEM);
            (new WebDriverWait(driver, 5))
                    .until(ExpectedConditions.presenceOfElementLocated(googleSearchButtonLocator));
            Actions actions = new Actions(driver);
            actions.sendKeys(driver.findElement(googleSearchButtonLocator), Keys.ENTER).build().perform();
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void runAllScenarios() {
        accessGsearch();
        searchGoogle();
        accessImage();
    }

    @Override
    public void run() {
        for (int i = 0; i < this.runs; i++) {
            System.out.println(Thread.currentThread().getName() + " started!");
            runAllScenarios();
            System.out.println(Thread.currentThread().getName() + " stopped!");
        }
    }

    public static void main(String args[]) throws IOException {
        Thread recordVideo = new VideoCapture("15", "40", "runVideo", ObjectTypes.GOOGLE.toString());
        recordVideo.start();

        Thread a = new GooglePage(1);
        a.start();

      /*  try {
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
        splitVideo.start();*/
    }
}


