package org.mozilla.benchmark.pageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mozilla.benchmark.objects.Driver;
import org.mozilla.benchmark.utils.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by andrei.filip on 10/30/2017.
 */
public class FacebookPage extends Thread{

    private static final Logger logger = LogManager.getLogger(FacebookPage.class.getName());
    private final WebDriver driver = Driver.getInstance();
    private int runs;
    //login locators
    private By userNameLocator = By.id("email");
    private By passwordLocator = By.id("pass");
    private By loginButtonLocator = By.id("loginbutton");

    //group locators
    private By groupButtonLocator = By.className("_5afe");

    //home locator
    private By homeButtonLocator = By.id("u_0_c");

    //access User
    private By accessUserLocator = By.className("_5pb8 _8o _8s lfloat _ohe");

    public FacebookPage(int runs) {
        this.runs = runs;
    }

    public void LoginFacebook() {
        driver.get(Constants.PageObjects.FACEBOOK_URL);
        driver.findElement(this.userNameLocator).sendKeys(Constants.PageObjects.FACEBOOK_USER_NAME);
        driver.findElement(this.passwordLocator).sendKeys(Constants.PageObjects.FACEBOOK_PASS);
        driver.findElement(this.loginButtonLocator).click();
    }

    public void accessGroup() {
        driver.findElements(groupButtonLocator).get(3).click();
    }

    public void goHome() {
        driver.findElement(homeButtonLocator).click();
    }

    public void accessUser() {
        driver.findElement(accessUserLocator).click();
    }

    public void runAllScenarios() {
        LoginFacebook();
        accessGroup();
        goHome();
    }

    @Override
    public void run() {
        for (int i = 0; i < this.runs; i++) {
            runAllScenarios();
        }
    }
}




