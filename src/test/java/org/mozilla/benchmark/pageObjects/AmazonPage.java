package org.mozilla.benchmark.pageObjects;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;

import org.mozilla.benchmark.utils.Constants;

/**
 * Created by andrei.filip on 10/30/2017.
 */
public class AmazonPage {

    static WebDriver driver;
    static By AmazonSearchBarElement = By.id("twotabsearchtextbox");
    static By AmazonSearchButton = By.id("nav-search-submit-text");
    static By VideoElement = By.xpath("//*[contains(text(),'The Lord Of The Rings: The Fellowship Of The Ring')]");
    static By bookResultElement = By.xpath("//*[contains(text(),'The Lord of the Rings: 50th Anniversary, One Vol. Edition')]");

    public AmazonPage(WebDriver driver) {
        this.driver = driver;
    }

    public static void accessAmazon() throws InterruptedException {
        Alert alert = null;
        driver.get(Constants.PageObjects.AMAZON_URL);
        JOptionPane.showMessageDialog(null, "Website accessed");
        Thread.sleep(5000);
    }

    public static void searchAmazon() {
        (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.visibilityOfElementLocated(AmazonSearchBarElement));
        driver.findElement(AmazonSearchBarElement).sendKeys(Constants.PageObjects.AMAZON_SEARCH_ITEM);
        (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.visibilityOfElementLocated(AmazonSearchButton));
        driver.findElement(AmazonSearchButton).click();
    }


    public static void accessVideoResult() throws InterruptedException {
        Thread.sleep(5000);
        (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.visibilityOfElementLocated(VideoElement));
        driver.findElement(VideoElement).click();
    }

    public static void backAction() {
        driver.navigate().back();
    }

    public static void accessBookResult() {
        (new WebDriverWait(driver, 7))
                .until(ExpectedConditions.visibilityOfElementLocated(bookResultElement));
        driver.findElement(bookResultElement).click();
    }

    public static void runAllScenarios() throws InterruptedException {
        accessAmazon();
        searchAmazon();
        accessVideoResult();
        backAction();
        accessBookResult();
    }
}
