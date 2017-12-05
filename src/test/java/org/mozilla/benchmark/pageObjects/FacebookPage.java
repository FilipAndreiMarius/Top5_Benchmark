package org.mozilla.benchmark.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.mozilla.benchmark.utils.Constants;

/**
 * Created by andrei.filip on 10/30/2017.
 */
public class FacebookPage {

    static WebDriver driver;
    //login locators
    By username = By.id("email");
    By password = By.id("PASS");
    By loginButton = By.id("loginbutton");

    //group locators
    By groupButton = By.className("_5afe");

    //home locator
    By homeButton = By.id("u_0_c");

    //access User
    By accessUser = By.className("_5pb8 _8o _8s lfloat _ohe");

    public FacebookPage(WebDriver driver) {
        this.driver = driver;
    }

    public void LoginFacebook() {
        driver.get(Constants.PageObjects.FACEBOOK_URL);
        driver.findElement(this.username).sendKeys(Constants.PageObjects.FACEBOOK_USER_NAME);
        driver.findElement(this.password).sendKeys(Constants.PageObjects.FACEBOOK_PASS);
        driver.findElement(this.loginButton).click();
    }

    public void accessGroup() {
        driver.findElements(groupButton).get(3).click();
    }

    public void goHome() throws InterruptedException {
        driver.findElement(homeButton).click();
    }

    public void accessUser() {
        driver.findElement(accessUser).click();
    }
}




