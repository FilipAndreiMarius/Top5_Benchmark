package org.mozilla.benchmark.utils;

import org.apache.commons.io.FileUtils;
import org.mozilla.benchmark.constants.FileExtensionsConstants;
import org.mozilla.benchmark.constants.WebPageConstants;
import org.mozilla.benchmark.objects.ImagePattern;
import org.mozilla.benchmark.objects.ImageSearchTypes;
import org.mozilla.benchmark.objects.LoggerManagerLevel;
import org.mozilla.benchmark.objects.PageNavigationTypes;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by silviu.checherita on 1/10/2018.
 */
public abstract class BasePage extends Thread {

    private static final LoggerManager logger = new LoggerManager(BasePage.class.getName());
    private static WebDriver _driver;
    private static int timeout = 10;
    private WebDriverWait wait;
    private PageNavigationTypes navigationType;
    private String testName;
    private int runs;

    public PageNavigationTypes getNavigationType() {
        return navigationType;
    }

    public void setNavigationType(PageNavigationTypes navigationType) {
        this.navigationType = navigationType;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public int getRuns() {
        return runs;
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }

    public BasePage(int runs, String testName, PageNavigationTypes navigationType) {
        _driver = DriverUtils.getInstance();
        this.runs = runs;
        this.testName = testName;
        this.navigationType = navigationType;
    }

    public void navigateToUrl(String url) {
        try {
            driverSleep(1000);
            _driver.navigate().to(url);
        } catch (Exception e) {
            logger.log(LoggerManagerLevel.ERROR, String.format("Could NOT load [%s]: [%s]", url, e), PropertiesManager.getEmailNotification());
        }
    }

    public void closeAllTabsExceptFirst() {
        try {
            int numberOfTabs = _driver.getWindowHandles().size();
            if (numberOfTabs > 1) {
                for (int i = numberOfTabs - 1; i > 0; i--) {
                    String winHandle = _driver.getWindowHandles().toArray()[i].toString();
                    _driver.switchTo().window(winHandle);
                    _driver.close();
                }
                _driver.switchTo().window(_driver.getWindowHandles().toArray()[0].toString());
            }
        } catch (Exception e) {
            logger.log(LoggerManagerLevel.ERROR, String.format("Could NOT close all tabs except first: [%s]", e), PropertiesManager.getEmailNotification());
        }
    }

    public void resetRun() {
        closeAllTabsExceptFirst();
        navigateToUrl(WebPageConstants.HOME_PAGE_URL);
    }

    public void navigateBack() {
        try {
            _driver.navigate().back();
        } catch (Exception e) {
            logger.log(LoggerManagerLevel.ERROR, String.format("Could NOT navigate to previous page: [%s]", e), PropertiesManager.getEmailNotification());
        }
    }

    public Dimension getWindowSize() {
        try {
            return _driver.manage().window().getSize();
        } catch (Exception e) {
            logger.log(LoggerManagerLevel.ERROR, String.format("Could NOT get window size: [%s]", e), PropertiesManager.getEmailNotification());
        }
        return null;
    }

    public WebElement getElementFromIframe(By iframe, By selector) {
        _driver.switchTo().defaultContent();
        _driver.switchTo().frame(getElement(iframe));
        return getElement(selector);
    }

    public WebElement getElement(By selector) {
        waitForElementToBeVisible(selector);
        try {
            return _driver.findElement(selector);
        } catch (Exception e) {
            logger.log(LoggerManagerLevel.ERROR, String.format("Element [%s] does not exist - proceeding", selector), PropertiesManager.getEmailNotification());
        }
        return null;
    }

    public List<WebElement> getElements(By selector) {
        waitForElementToBeVisible(selector);
        try {
            return _driver.findElements(selector);
        } catch (Exception e) {
            logger.log(LoggerManagerLevel.ERROR, String.format("The following element did not display: [%s] - [%s]", selector, e), PropertiesManager.getEmailNotification());
        }
        return null;
    }

    public void click(By selector) {
        WebElement element = getElement(selector);
        waitForElementToBeClickable(selector);
        try {
            element.click();
        } catch (Exception e) {
            logger.log(LoggerManagerLevel.ERROR, String.format("The following element is not clickable: [%s] - [%s]", selector, e), PropertiesManager.getEmailNotification());
        }
    }

    public void click(WebElement element) {
        waitForElementToBeClickable(element);
        try {
            element.click();
        } catch (Exception e) {
            logger.log(LoggerManagerLevel.ERROR, String.format("The following element is not clickable: [%s] - [%s]", element, e), PropertiesManager.getEmailNotification());
        }

    }

    public void sendKeys(By selector, String value) {
        WebElement element = getElement(selector);
        clearField(element);
        try {
            element.sendKeys(value);
        } catch (Exception e) {
            logger.log(LoggerManagerLevel.ERROR, String.format("Error in sending [%s] to the following element: [%s] - [%s]", value, selector.toString(), e), PropertiesManager.getEmailNotification());
        }
    }

    public void sendKeysAndPressEnter(By selector, String value) {
        WebElement element = getElement(selector);
        clearField(element);
        try {
            element.sendKeys(value);
            Actions actions = new Actions(_driver);
            actions.sendKeys(element, Keys.ENTER).build().perform();
        } catch (Exception e) {
            logger.log(LoggerManagerLevel.ERROR, String.format("Error in sending [%s] to the following element: [%s] - [%s]", value, selector.toString(), e), PropertiesManager.getEmailNotification());
        }
    }

    public void clearField(WebElement element) {
        try {
            element.clear();
            waitForElementTextToBeEmpty(element);
        } catch (Exception e) {
            logger.log(LoggerManagerLevel.ERROR, String.format("The following element could not be cleared: [%s] - [%s]", element, e), PropertiesManager.getEmailNotification());
        }
    }

    public void waitForElementToDisplay(By selector) {
        WebElement element = getElement(selector);
        while (!element.isDisplayed()) {
            logger.log(LoggerManagerLevel.ERROR, String.format("Waiting for element to display: [%s]", selector), PropertiesManager.getEmailNotification());
            driverSleep(200);
        }
    }

    public void waitForElementTextToBeEmpty(WebElement element) {
        String text;
        try {
            text = element.getText();
            int maxRetries = 10;
            int retry = 0;
            while ((text.length() >= 1) || (retry < maxRetries)) {
                retry++;
                text = element.getText();
            }
        } catch (Exception e) {
            logger.log(LoggerManagerLevel.ERROR, String.format("The following element could not be cleared: [%s] - [%s]", element, e), PropertiesManager.getEmailNotification());
        }
    }

    public void waitForElementToBeVisible(By selector) {
        try {
            wait = new WebDriverWait(_driver, timeout);
            wait.until(ExpectedConditions.presenceOfElementLocated(selector));
        } catch (Exception e) {
            logger.log(LoggerManagerLevel.ERROR, String.format("The following element was not visible: [%s] - [%s]", selector, e), PropertiesManager.getEmailNotification());
        }
    }

    public void waitUntilElementIsDisplayedOnScreen(By selector) {
        try {
            wait = new WebDriverWait(_driver, timeout);
            wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
        } catch (Exception e) {
            logger.log(LoggerManagerLevel.ERROR, String.format("The following element was not visible: [%s] - [%s]", selector, e), PropertiesManager.getEmailNotification());
        }
    }

    public void waitForElementToBeClickable(By selector) {
        try {
            wait = new WebDriverWait(_driver, timeout);
            wait.until(ExpectedConditions.elementToBeClickable(selector));
        } catch (Exception e) {
            logger.log(LoggerManagerLevel.ERROR, String.format("The following element is not clickable: [%s] - [%s]", selector, e), PropertiesManager.getEmailNotification());
        }
    }

    public void waitForElementToBeClickable(WebElement element) {
        try {
            wait = new WebDriverWait(_driver, timeout);
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            logger.log(LoggerManagerLevel.ERROR, String.format("The following element is not clickable: [%s] - [%s]", element, e), PropertiesManager.getEmailNotification());
        }
    }

    public void waitForElementToDisplay(WebElement element) {
        try {
            wait = new WebDriverWait(_driver, timeout);
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            logger.log(LoggerManagerLevel.ERROR, String.format("The following element is not clickable: [%s] - [%s]", element, e), PropertiesManager.getEmailNotification());
        }
    }

    public void driverSleep(final long millis) {
        logger.log(LoggerManagerLevel.INFO, String.format("Sleeping %d ms", millis), false);
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            logger.log(LoggerManagerLevel.ERROR, String.format("Sleep interrupted: [%s]", e), PropertiesManager.getEmailNotification());
        }
    }

    public void captureElementScreenshot(By selector, String destination) {
        waitForElementToDisplay(selector);
        WebElement element = getElement(selector);
        int imageX = element.getLocation().getX();
        int imageY = element.getLocation().getY();
        int imageWidth = element.getSize().getWidth();
        int imageHeight = element.getSize().getHeight();
        File screen = ((TakesScreenshot) _driver).getScreenshotAs(OutputType.FILE);
        BufferedImage img;
        try {
            logger.log(LoggerManagerLevel.INFO, String.format("Saving screenshot for [%s] at the following destination: [%s] ...", selector, destination), false);
            img = ImageIO.read(screen);
            BufferedImage dest = img.getSubimage(imageX, imageY, imageWidth, imageHeight);
            ImageIO.write(dest, FileExtensionsConstants.IMAGE_EXTENSION, screen);
            FileUtils.copyFile(screen, new File(destination));
            logger.log(LoggerManagerLevel.DEBUG, "Screenshot saved !!!", false);
        } catch (IOException e) {
            logger.log(LoggerManagerLevel.ERROR, String.format("Could not save screenshot for element: [%s] - [%s]", selector, e), PropertiesManager.getEmailNotification());
        }
    }

    private int getElementRectTop(WebElement element) {
        return Integer.valueOf(((JavascriptExecutor) _driver).executeScript("return arguments[0].getBoundingClientRect().top;", element).toString());
    }

    public void captureElementScreenshot(WebElement element, String destination) {
        waitForElementToDisplay(element);
        int imageX = element.getLocation().getX();
        int imageY = element.getLocation().getY();
        int imageWidth = element.getSize().getWidth();
        int imageHeight = element.getSize().getHeight();
        File screen = ((TakesScreenshot) _driver).getScreenshotAs(OutputType.FILE);
        BufferedImage img;
        BufferedImage dest;
        try {
            logger.log(LoggerManagerLevel.INFO, String.format("Saving screenshot for [%s] at the following destination: [%s] ...", element.toString(), destination), false);
            img = ImageIO.read(screen);
            try {
                dest = img.getSubimage(imageX, imageY, imageWidth, imageHeight);
            } catch (RasterFormatException e) {
                dest = img.getSubimage(imageX, getElementRectTop(element), imageWidth, imageHeight);
            }
            ImageIO.write(dest, FileExtensionsConstants.IMAGE_EXTENSION, screen);
            FileUtils.copyFile(screen, new File(destination));
            logger.log(LoggerManagerLevel.DEBUG, "Screenshot saved !!!", false);
        } catch (IOException e) {
            logger.log(LoggerManagerLevel.ERROR, String.format("Could not save screenshot for element: [%s] - [%s]", element, e), PropertiesManager.getEmailNotification());
        }
    }

    public void createBackgroundImage(Color color, String destination) {
        try {
            logger.log(LoggerManagerLevel.INFO, String.format("Saving image at the following destination: [%s] ...", destination), false);
            BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = image.createGraphics();
            graphics.setPaint(color);
            graphics.fillRect(0, 0, 1, 1);

            if (FileManager.createDirectories(destination)) {
                File outputFile = new File(destination);
                ImageIO.write(image, FileExtensionsConstants.IMAGE_EXTENSION, outputFile);
                logger.log(LoggerManagerLevel.DEBUG, "Image created !!!", false);
            }
        } catch (IOException e) {
            logger.log(LoggerManagerLevel.ERROR, String.format("Could not create image [%s]: [%s]", destination, e), PropertiesManager.getEmailNotification());
        }
    }

    public void addPatternWithSimilarity(Color color, String elementName, ImageSearchTypes searchType, float similarity) {
        if (PageNavigationTypes.SAVE_PATTERN.equals(getNavigationType())) {
            String imageDetailsName = ScenarioManager.getPatternName(elementName, getTestName());
            String imageDetailsNameShort = ScenarioManager.getPatternNameShort(elementName, getTestName());
            createBackgroundImage(new Color(color.getRed(), color.getGreen(), color.getBlue()), imageDetailsName);
            if (PropertiesManager.getDynamicPatterns()) {
                ImagePattern.createDynamicPattern(getTestName(), elementName, imageDetailsNameShort, searchType, similarity);
            }
        }
    }

    public void addPattern(Color color, String elementName, ImageSearchTypes searchType) {
        addPatternWithSimilarity(color, elementName, searchType, PropertiesManager.getDefaultSimilarity());
    }

    public void addPatternWithSimilarity(WebElement element, String elementName, ImageSearchTypes searchType, float similarity) {
        if (PageNavigationTypes.SAVE_PATTERN.equals(getNavigationType())) {
            String imageDetailsName = ScenarioManager.getPatternName(elementName, getTestName());
            String imageDetailsNameShort = ScenarioManager.getPatternNameShort(elementName, getTestName());
            captureElementScreenshot(element, imageDetailsName);
            if (PropertiesManager.getDynamicPatterns()) {
                ImagePattern.createDynamicPattern(getTestName(), elementName, imageDetailsNameShort, searchType, similarity);
            }
        }
    }

    public void addPattern(WebElement element, String elementName, ImageSearchTypes searchType) {
        addPatternWithSimilarity(element, elementName, searchType, PropertiesManager.getDefaultSimilarity());
    }

    public void addPatternWithSimilarity(By element, String elementName, ImageSearchTypes searchType, float similarity) {
        if (PageNavigationTypes.SAVE_PATTERN.equals(getNavigationType())) {
            for (WebElement elem : getElements(element)) {
                String imageDetailsName = ScenarioManager.getPatternName(elementName, getTestName());
                String imageDetailsNameShort = ScenarioManager.getPatternNameShort(elementName, getTestName());
                captureElementScreenshot(elem, imageDetailsName);
                if (PropertiesManager.getDynamicPatterns()) {
                    ImagePattern.createDynamicPattern(getTestName(), elementName, imageDetailsNameShort, searchType, similarity);
                }
            }
        }
    }

    public void addPattern(By selector, String elementName, ImageSearchTypes searchType) {
        addPatternWithSimilarity(selector, elementName, searchType, PropertiesManager.getDefaultSimilarity());
    }

    public void addPatternWithSimilarity(String source, String elementName, ImageSearchTypes searchType, float similarity) {
        if (PageNavigationTypes.SAVE_PATTERN.equals(getNavigationType())) {
            String imageDetailsName = ScenarioManager.getPatternName(elementName, getTestName());
            String imageDetailsNameShort = ScenarioManager.getPatternNameShort(elementName, getTestName());
            FileManager.copyImage(source, imageDetailsName);
            if (PropertiesManager.getDynamicPatterns()) {
                ImagePattern.createDynamicPattern(getTestName(), elementName, imageDetailsNameShort, searchType, similarity);
            }
        }
    }

    public void addPattern(String source, String elementName, ImageSearchTypes searchType) {
        addPatternWithSimilarity(source, elementName, searchType, PropertiesManager.getDefaultSimilarity());
    }

    public abstract void runAllScenarios();

    @Override
    public void run() {
        for (int i = 0; i < getRuns(); i++) {
            runAllScenarios();
        }
        DriverUtils.closeWebBrowser();
    }
}
