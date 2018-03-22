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
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Rectangle;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
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
            logger.log(LoggerManagerLevel.ERROR, String.format("Could NOT load [%s]: [%s]", url, ErrorManager.getErrorMessage(e.getStackTrace())), PropertiesManager.getEmailNotification());
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
            logger.log(LoggerManagerLevel.ERROR, String.format("Could NOT close all tabs except first: [%s]", ErrorManager.getErrorMessage(e.getStackTrace())), PropertiesManager.getEmailNotification());
        }
    }

    public void changeFocusToLastOpenedTab() {
        driverSleep(1000);
        int numberOfTabs = _driver.getWindowHandles().size();
        String winHandle = _driver.getWindowHandles().toArray()[numberOfTabs - 1].toString();
        _driver.switchTo().window(winHandle);
    }

    public void resetRun() {
        closeAllTabsExceptFirst();
        navigateToUrl(WebPageConstants.HOME_PAGE_URL);
    }

    public void navigateBack() {
        try {
            _driver.navigate().back();
        } catch (Exception e) {
            logger.log(LoggerManagerLevel.ERROR, String.format("Could NOT navigate to previous page: [%s]", ErrorManager.getErrorMessage(e.getStackTrace())), PropertiesManager.getEmailNotification());
        }
    }

    public Dimension getWindowSize() {
        try {
            return _driver.manage().window().getSize();
        } catch (Exception e) {
            logger.log(LoggerManagerLevel.ERROR, String.format("Could NOT get window size: [%s]", ErrorManager.getErrorMessage(e.getStackTrace())), PropertiesManager.getEmailNotification());
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
            logger.log(LoggerManagerLevel.ERROR, String.format("The following element did not display: [%s] - [%s]", selector, ErrorManager.getErrorMessage(e.getStackTrace())), PropertiesManager.getEmailNotification());
        }
        return null;
    }

    private int getElementRectTop(WebElement element) {
        return ((Number) ((JavascriptExecutor) _driver).executeScript("return arguments[0].getBoundingClientRect().top;", element)).intValue();
    }

    private int getElementRectLeft(WebElement element) {
        return ((Number) ((JavascriptExecutor) _driver).executeScript("return arguments[0].getBoundingClientRect().left;", element)).intValue();
    }

    private void scrollToViewport(WebElement element) {
        ((JavascriptExecutor) _driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void click(Object elementOrSelector) {
        WebElement element = null;

        if (!(elementOrSelector instanceof By || elementOrSelector instanceof WebElement)) {
            logger.log(LoggerManagerLevel.ERROR, String.format("The following object is not an instance of By or WebElement : [%s]", elementOrSelector), PropertiesManager.getEmailNotification());
            return;
        }

        if (elementOrSelector instanceof By) {
            element = getElement((By) elementOrSelector);
        }
        waitForElementToBeClickable(element);

        if (element == null) {
            return;
        }

        Dimension elemSize = element.getSize();
        Actions actionBuilder = new Actions(_driver);

        try {
            actionBuilder.moveToElement(element).perform();
        } catch (MoveTargetOutOfBoundsException e) {
            scrollToViewport(element);
        }

        int moveX = getElementRectLeft(element) + elemSize.getWidth() / 2;
        int moveY = getElementRectTop(element) + 75 + elemSize.getHeight() / 2;

        try {
            Robot r = new Robot();
            r.mouseMove(moveX, moveY);
            r.mousePress(InputEvent.BUTTON1_MASK);
            driverSleep(100);
            r.mouseRelease(InputEvent.BUTTON1_MASK);
        } catch (AWTException e) {
            logger.log(LoggerManagerLevel.ERROR, String.format("The following element is not clickable: [%s] - [%s]", element, ErrorManager.getErrorMessage(e.getStackTrace())), PropertiesManager.getEmailNotification());
        }
    }

    public void sendKeys(By selector, String value) {
        WebElement element = getElement(selector);
        clearField(element);
        try {
            element.sendKeys(value);
        } catch (Exception e) {
            logger.log(LoggerManagerLevel.ERROR, String.format("Error in sending [%s] to the following element: [%s] - [%s]", value, selector.toString(), ErrorManager.getErrorMessage(e.getStackTrace())), PropertiesManager.getEmailNotification());
        }
    }

    public void sendKeysAndPressEnter(By selector, String value) {
        WebElement element = getElement(selector);
        clearField(element);
        try {
            element.sendKeys(value);
            Robot r = new Robot();
            r.keyPress(KeyEvent.VK_ENTER);
            driverSleep(51);
            r.keyRelease(KeyEvent.VK_ENTER);
        } catch (Exception e) {
            logger.log(LoggerManagerLevel.ERROR, String.format("Error in sending [%s] to the following element: [%s] - [%s]", value, selector.toString(), ErrorManager.getErrorMessage(e.getStackTrace())), PropertiesManager.getEmailNotification());
        }
    }

    public void clearField(WebElement element) {
        try {
            element.clear();
            waitForElementTextToBeEmpty(element);
        } catch (Exception e) {
            logger.log(LoggerManagerLevel.ERROR, String.format("The following element could not be cleared: [%s] - [%s]", element, ErrorManager.getErrorMessage(e.getStackTrace())), PropertiesManager.getEmailNotification());
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
            logger.log(LoggerManagerLevel.ERROR, String.format("The following element could not be cleared: [%s] - [%s]", element, ErrorManager.getErrorMessage(e.getStackTrace())), PropertiesManager.getEmailNotification());
        }
    }

    public void waitUntilTitleContains(String title) {
        try {
            wait = new WebDriverWait(_driver, timeout);
            wait.until(ExpectedConditions.titleContains(title));
        } catch (Exception e) {
            logger.log(LoggerManagerLevel.ERROR, String.format("Page with title: [%s] NOT found [%s]", title, ErrorManager.getErrorMessage(e.getStackTrace())), PropertiesManager.getEmailNotification());
        }
    }

    public void waitForElementToBeVisible(By selector) {
        try {
            wait = new WebDriverWait(_driver, timeout);
            wait.until(ExpectedConditions.presenceOfElementLocated(selector));
        } catch (Exception e) {
            logger.log(LoggerManagerLevel.ERROR, String.format("The following element was not visible: [%s] - [%s]", selector, ErrorManager.getErrorMessage(e.getStackTrace())), PropertiesManager.getEmailNotification());
        }
    }

    public void waitUntilElementIsDisplayedOnScreen(By selector) {
        try {
            wait = new WebDriverWait(_driver, timeout);
            wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
        } catch (Exception e) {
            logger.log(LoggerManagerLevel.ERROR, String.format("The following element was not visible: [%s] - [%s]", selector, ErrorManager.getErrorMessage(e.getStackTrace())), PropertiesManager.getEmailNotification());
        }
    }

    public void waitForElementToBeClickable(By selector) {
        try {
            wait = new WebDriverWait(_driver, timeout);
            wait.until(ExpectedConditions.elementToBeClickable(selector));
        } catch (Exception e) {
            logger.log(LoggerManagerLevel.ERROR, String.format("The following element is not clickable: [%s] - [%s]", selector, ErrorManager.getErrorMessage(e.getStackTrace())), PropertiesManager.getEmailNotification());
        }
    }

    public void waitForElementToBeClickable(WebElement element) {
        try {
            wait = new WebDriverWait(_driver, timeout);
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            logger.log(LoggerManagerLevel.ERROR, String.format("The following element is not clickable: [%s] - [%s]", element, ErrorManager.getErrorMessage(e.getStackTrace())), PropertiesManager.getEmailNotification());
        }
    }

    public void waitForElementToDisplay(WebElement element) {
        try {
            wait = new WebDriverWait(_driver, timeout);
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            logger.log(LoggerManagerLevel.ERROR, String.format("The following element is not clickable: [%s] - [%s]", element, ErrorManager.getErrorMessage(e.getStackTrace())), PropertiesManager.getEmailNotification());
        }
    }

    public void driverSleep(final long millis) {
        logger.log(LoggerManagerLevel.INFO, String.format("Sleeping %d ms", millis), false);
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            logger.log(LoggerManagerLevel.ERROR, String.format("Sleep interrupted: [%s]", ErrorManager.getErrorMessage(e.getStackTrace())), PropertiesManager.getEmailNotification());
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
            logger.log(LoggerManagerLevel.ERROR, String.format("Could not save screenshot for element: [%s] - [%s]", selector, ErrorManager.getErrorMessage(e.getStackTrace())), PropertiesManager.getEmailNotification());
        }
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
            logger.log(LoggerManagerLevel.ERROR, String.format("Could not save screenshot for element: [%s] - [%s]", element, ErrorManager.getErrorMessage(e.getStackTrace())), PropertiesManager.getEmailNotification());
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
            logger.log(LoggerManagerLevel.ERROR, String.format("Could not create image [%s]: [%s]", destination, ErrorManager.getErrorMessage(e.getStackTrace())), PropertiesManager.getEmailNotification());
        }
    }

    public void addPatternWithSimilarity(Color color, Rectangle rectangle, String elementName, ImageSearchTypes searchType, float similarity) {
        if (PageNavigationTypes.SAVE_PATTERN.equals(getNavigationType())) {
            String imageDetailsName = ScenarioManager.getPatternName(elementName, getTestName());
            String imageDetailsNameShort = ScenarioManager.getPatternNameShort(elementName, getTestName());
            createBackgroundImage(new Color(color.getRed(), color.getGreen(), color.getBlue()), imageDetailsName);
            if (PropertiesManager.getDynamicPatterns()) {
                ImagePattern.createDynamicPattern(getTestName(), rectangle, elementName, imageDetailsNameShort, searchType, similarity);
            }
        }
    }

    public void addPattern(Color color, Rectangle rectangle, String elementName, ImageSearchTypes searchType) {
        addPatternWithSimilarity(color, rectangle, elementName, searchType, PropertiesManager.getDefaultSimilarity());
    }

    public void addPatternWithSimilarity(WebElement element, String elementName, ImageSearchTypes searchType, float similarity) {
        if (PageNavigationTypes.SAVE_PATTERN.equals(getNavigationType())) {
            String imageDetailsName = ScenarioManager.getPatternName(elementName, getTestName());
            String imageDetailsNameShort = ScenarioManager.getPatternNameShort(elementName, getTestName());
            captureElementScreenshot(element, imageDetailsName);
            if (PropertiesManager.getDynamicPatterns()) {
                ImagePattern.createDynamicPattern(getTestName(), null, elementName, imageDetailsNameShort, searchType, similarity);
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
                    ImagePattern.createDynamicPattern(getTestName(), null, elementName, imageDetailsNameShort, searchType, similarity);
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
                ImagePattern.createDynamicPattern(getTestName(), null, elementName, imageDetailsNameShort, searchType, similarity);
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
