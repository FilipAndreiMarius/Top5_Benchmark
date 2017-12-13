package org.mozilla.benchmark.pageObjects;

import org.mozilla.benchmark.utils.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;

/**
 * Created by andrei.filip on 10/30/2017.
 */
public class AmazonPage {

    private final WebDriver driver;
    private By searchBarLocator = By.id("twotabsearchtextbox");
    private By SearchButtonLocator = By.id("nav-search-submit-text");
    private By videoElementLocator = By.xpath("//*[contains(text(),'The Lord Of The Rings: The Fellowship Of The Ring')]");
    private By bookResultElementLocator = By.xpath("//*[contains(text(),'The Lord of the Rings: 50th Anniversary, One Vol. Edition')]");

    public AmazonPage(WebDriver driver) {
        this.driver = driver;
    }

    public void accessAmazon() throws InterruptedException {
        driver.get(Constants.PageObjects.AMAZON_URL);
        JOptionPane.showMessageDialog(null, "Website accessed");
        Thread.sleep(5000);
    }

    public void searchAmazon() {
        (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.visibilityOfElementLocated(searchBarLocator));
        driver.findElement(searchBarLocator).sendKeys(Constants.PageObjects.AMAZON_SEARCH_ITEM);
        (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.visibilityOfElementLocated(SearchButtonLocator));
        driver.findElement(SearchButtonLocator).click();
    }


    public void accessVideoResult() throws InterruptedException {
        Thread.sleep(5000);
        (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.visibilityOfElementLocated(videoElementLocator));
        driver.findElement(videoElementLocator).click();
    }

    public void backAction() {
        driver.navigate().back();
    }

    public void accessBookResult() {
        (new WebDriverWait(driver, 7))
                .until(ExpectedConditions.visibilityOfElementLocated(bookResultElementLocator));
        driver.findElement(bookResultElementLocator).click();
    }

    public void runAllScenarios() throws InterruptedException {
        accessAmazon();
        searchAmazon();
        accessVideoResult();
        backAction();
        accessBookResult();
    }
}
