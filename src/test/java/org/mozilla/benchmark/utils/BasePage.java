package org.mozilla.benchmark.utils;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mozilla.benchmark.objects.ImagePattern;
import org.mozilla.benchmark.objects.ImageSearchTypes;
import org.mozilla.benchmark.objects.PageNavigationTypes;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by silviu.checherita on 1/10/2018.
 */
public class BasePage extends Thread {

    private static final Logger logger = LogManager.getLogger(BasePage.class.getName());
    public static WebDriver _driver;
    private static int timeout = 10;
    public WebDriverWait wait;
    public static String BROWSER_BG_COLOR = "#f9f9f9";

    public BasePage() {
        _driver = DriverUtils.getInstance();
        driverSleep(1000);
    }

    public void navigateToURL(String url) {
        try {
            _driver.navigate().to(url);
        } catch (Exception e) {
            logger.error(String.format("Could NOT load [%s]: [%s]", url, e));
            if (PropertiesManager.getExitIfErrorsFound()) {
                System.exit(1);
            }
        }
    }

    public void navigateBack() {
        try {
            _driver.navigate().back();
        } catch (Exception e) {
            logger.error(String.format("Could NOT navigate to previous page: [%s]", e));
            if (PropertiesManager.getExitIfErrorsFound()) {
                System.exit(1);
            }
        }
    }

    public Dimension getWindowSize() {
        try {
            return _driver.manage().window().getSize();
        } catch (Exception e) {
            logger.error(String.format("Could NOT get window size: [%s]", e));
            if (PropertiesManager.getExitIfErrorsFound()) {
                System.exit(1);
            }
        }
        return null;
    }

    public void storeCookieInformation(String cookieFile) {
        if (FileManager.createDirectories(cookieFile)) {
            File file = new File(cookieFile);
            try {
                file.delete();
                file.createNewFile();
                FileWriter fileWriter = new FileWriter(file);
                BufferedWriter writeBuffer = new BufferedWriter(fileWriter);
                for (org.openqa.selenium.Cookie cook : _driver.manage().getCookies()) {
                    String writeup = cook.getName() + ";" + cook.getValue() + ";" + cook.getDomain() + ";" + cook.getPath() + ";" + cook.getExpiry() + ";" + cook.isSecure();
                    writeBuffer.write(writeup);
                    System.out.println(writeup);
                    writeBuffer.newLine();
                }
                writeBuffer.flush();
                writeBuffer.close();
                fileWriter.close();
            } catch (Exception e) {
                logger.error(String.format("Could not store cookie information !!! [%s]", e));
                if (PropertiesManager.getExitIfErrorsFound()) {
                    System.exit(1);
                }
            }
        }
    }

    public void getCookieInformation(String cookieFile) {
        try {
            File file = new File(cookieFile);
            System.out.println(file);
            FileReader fileReader = new FileReader(file);
            BufferedReader buffReader = new BufferedReader(fileReader);
            String strLine;
            while ((strLine = buffReader.readLine()) != null) {
                StringTokenizer token = new StringTokenizer(strLine, ";");

                while (token.hasMoreTokens()) {

                    String name = token.nextToken();
                    String value = token.nextToken();
                    String domain = token.nextToken();
                    String path = token.nextToken();
                    System.out.println(name);
                    Date expiry = null;

                    String val;
                    if (!(val = token.nextToken()).equals("null")) {
                        expiry = new Date(val);
                    }
                    Boolean isSecure = Boolean.parseBoolean(token.nextToken());
                    Cookie ck = new Cookie(name, value, domain, path, expiry, isSecure);
                    System.out.println(ck);
                    _driver.manage().addCookie(ck);
                }
            }
        } catch (Exception e) {
            logger.error(String.format("Could not retrieve cookie information !!! [%s]", e));
            if (PropertiesManager.getExitIfErrorsFound()) {
                System.exit(1);
            }
        }
    }

    public WebElement getElement(By selector) {
        waitForElementToBeVisible(selector);
        try {
            return _driver.findElement(selector);
        } catch (Exception e) {
            logger.error(String.format("Element [%s] does not exist - proceeding", selector));
            if (PropertiesManager.getExitIfErrorsFound()) {
                System.exit(1);
            }
        }
        return null;
    }

    public List<WebElement> getElements(By selector) {
        waitForElementToBeVisible(selector);
        try {
            return _driver.findElements(selector);
        } catch (Exception e) {
            logger.error(String.format("The following element did not display: [%s] - [%s]", selector, e));
            if (PropertiesManager.getExitIfErrorsFound()) {
                System.exit(1);
            }
        }
        return null;
    }

    public void click(By selector) {
        WebElement element = getElement(selector);
        waitForElementToBeClickable(selector);
        try {
            element.click();
        } catch (Exception e) {
            logger.error(String.format("The following element is not clickable: [%s] - [%s]", selector, e));
            if (PropertiesManager.getExitIfErrorsFound()) {
                System.exit(1);
            }
        }
    }

    public void click(WebElement element) {
        waitForElementToBeClickable(element);
        try {
            element.click();
        } catch (Exception e) {
            logger.error(String.format("The following element is not clickable: [%s] - [%s]", element, e));
            if (PropertiesManager.getExitIfErrorsFound()) {
                System.exit(1);
            }
        }

    }

    public void sendKeys(By selector, String value) {
        WebElement element = getElement(selector);
        clearField(element);
        try {
            element.sendKeys(value);
        } catch (Exception e) {
            logger.error(String.format("Error in sending [%s] to the following element: [%s] - [%s]", value, selector.toString(), e));
            if (PropertiesManager.getExitIfErrorsFound()) {
                System.exit(1);
            }
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
            logger.error(String.format("Error in sending [%s] to the following element: [%s] - [%s]", value, selector.toString(), e));
            if (PropertiesManager.getExitIfErrorsFound()) {
                System.exit(1);
            }
        }
    }

    public void clearField(WebElement element) {
        try {
            element.clear();
            waitForElementTextToBeEmpty(element);
        } catch (Exception e) {
            logger.error(String.format("The following element could not be cleared: [%s] - [%s]", element, e));
            if (PropertiesManager.getExitIfErrorsFound()) {
                System.exit(1);
            }
        }
    }

    public void waitForElementToDisplay(By selector) {
        WebElement element = getElement(selector);
        while (!element.isDisplayed()) {
            logger.debug(String.format("Waiting for element to display: [%s]", selector));
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
            logger.error(String.format("The following element could not be cleared: [%s] - [%s]", element, e));
            if (PropertiesManager.getExitIfErrorsFound()) {
                System.exit(1);
            }
        }
    }

    public void waitForElementToBeVisible(By selector) {
        try {
            wait = new WebDriverWait(_driver, timeout);
            wait.until(ExpectedConditions.presenceOfElementLocated(selector));
        } catch (Exception e) {
            logger.error(String.format("The following element was not visible: [%s] - [%s]", selector, e));
            if (PropertiesManager.getExitIfErrorsFound()) {
                System.exit(1);
            }
        }
    }

    public void waitUntilElementIsDisplayedOnScreen(By selector) {
        try {
            wait = new WebDriverWait(_driver, timeout);
            wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
        } catch (Exception e) {
            logger.error(String.format("The following element was not visible: [%s] - [%s]", selector, e));
            if (PropertiesManager.getExitIfErrorsFound()) {
                System.exit(1);
            }
        }
    }

    public void waitForElementToBeClickable(By selector) {
        try {
            wait = new WebDriverWait(_driver, timeout);
            wait.until(ExpectedConditions.elementToBeClickable(selector));
        } catch (Exception e) {
            logger.error(String.format("The following element is not clickable: [%s] - [%s]", selector, e));
            if (PropertiesManager.getExitIfErrorsFound()) {
                System.exit(1);
            }
        }
    }

    public void waitForElementToBeClickable(WebElement element) {
        try {
            wait = new WebDriverWait(_driver, timeout);
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            logger.error(String.format("The following element is not clickable: [%s] - [%s]", element, e));
            if (PropertiesManager.getExitIfErrorsFound()) {
                System.exit(1);
            }
        }
    }

    public void waitForElementToDisplay(WebElement element) {
        try {
            wait = new WebDriverWait(_driver, timeout);
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            logger.error(String.format("The following element is not clickable: [%s] - [%s]", element, e));
            if (PropertiesManager.getExitIfErrorsFound()) {
                System.exit(1);
            }
        }
    }

    public void driverSleep(final long millis) {
        logger.info((String.format("Sleeping %d ms", millis)));
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            logger.error(String.format("Sleep interrupted: [%s]", e));
            if (PropertiesManager.getExitIfErrorsFound()) {
                System.exit(1);
            }
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
            logger.info(String.format("Saving screenshot for [%s] at the following destination: [%s] ...", selector, destination));
            img = ImageIO.read(screen);
            BufferedImage dest = img.getSubimage(imageX, imageY, imageWidth, imageHeight);
            ImageIO.write(dest, Constants.Extensions.IMAGE_EXTENSION, screen);
            FileUtils.copyFile(screen, new File(destination));
            logger.debug("Screenshot saved !!!");
        } catch (IOException e) {
            logger.error(String.format("Could not save screenshot for element: [%s] - [%s]", selector, e));
            if (PropertiesManager.getExitIfErrorsFound()) {
                System.exit(1);
            }
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
        try {
            logger.info(String.format("Saving screenshot for [%s] at the following destination: [%s] ...", element.toString(), destination));
            img = ImageIO.read(screen);
            BufferedImage dest = img.getSubimage(imageX, imageY, imageWidth, imageHeight);
            ImageIO.write(dest, Constants.Extensions.IMAGE_EXTENSION, screen);
            FileUtils.copyFile(screen, new File(destination));
            logger.debug("Screenshot saved !!!");
        } catch (IOException e) {
            logger.error(String.format("Could not save screenshot for element: [%s] - [%s]", element, e));
            if (PropertiesManager.getExitIfErrorsFound()) {
                System.exit(1);
            }
        }
    }

    public void createBackgroundImage(Color color, String destination) {
        try {
            logger.info(String.format("Saving image at the following destination: [%s] ...", destination));
            BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = image.createGraphics();
            graphics.setPaint(color);
            graphics.fillRect(0, 0, 1, 1);

            if (FileManager.createDirectories(destination)) {
                File outputFile = new File(destination);
                ImageIO.write(image, Constants.Extensions.IMAGE_EXTENSION, outputFile);
                logger.debug("Image created !!!");
            }
        } catch (IOException e) {
            logger.error(String.format("Could not create image [%s]: [%s]", destination, e));
            if (PropertiesManager.getExitIfErrorsFound()) {
                System.exit(1);
            }
        }
    }

    public void addPattern(Color color, String elementName, String testName, PageNavigationTypes navigationType, ImageSearchTypes searchType, float similarity) {
        if (PageNavigationTypes.SAVE_PATTERN.equals(navigationType)) {
            String imageDetailsName = ScenarioManager.getPatternName(elementName, testName);
            String imageDetailsNameShort = ScenarioManager.getPatternNameShort(elementName, testName);
            createBackgroundImage(new Color(color.getRed(), color.getGreen(), color.getBlue()), imageDetailsName);
            if (PropertiesManager.getDynamicPatterns()) {
                ImagePattern.createDynamicPattern(testName, elementName, imageDetailsNameShort, searchType, similarity);
            }
        }
    }

    public void addPattern(WebElement element, String elementName, String testName, PageNavigationTypes navigationType, ImageSearchTypes searchType, float similarity) {
        if (PageNavigationTypes.SAVE_PATTERN.equals(navigationType)) {
            String imageDetailsName = ScenarioManager.getPatternName(elementName, testName);
            String imageDetailsNameShort = ScenarioManager.getPatternNameShort(elementName, testName);
            captureElementScreenshot(element, imageDetailsName);
            if (PropertiesManager.getDynamicPatterns()) {
                ImagePattern.createDynamicPattern(testName, elementName, imageDetailsNameShort, searchType, similarity);
            }
        }
    }

    public void addPattern(By element, String elementName, String testName, PageNavigationTypes navigationType, ImageSearchTypes searchType, float similarity) {
        if (PageNavigationTypes.SAVE_PATTERN.equals(navigationType)) {
            for (WebElement elem : getElements(element)) {
                String imageDetailsName = ScenarioManager.getPatternName(elementName, testName);
                String imageDetailsNameShort = ScenarioManager.getPatternNameShort(elementName, testName);
                captureElementScreenshot(elem, imageDetailsName);
                if (PropertiesManager.getDynamicPatterns()) {
                    ImagePattern.createDynamicPattern(testName, elementName, imageDetailsNameShort, searchType, similarity);
                }
            }
        }
    }

    public void addPattern(String source, String elementName, String testName, PageNavigationTypes navigationType, ImageSearchTypes searchType, float similarity) {
        if (PageNavigationTypes.SAVE_PATTERN.equals(navigationType)) {
            String imageDetailsName = ScenarioManager.getPatternName(elementName, testName);
            String imageDetailsNameShort = ScenarioManager.getPatternNameShort(elementName, testName);
            FileManager.copyImage(source, imageDetailsName);
            if (PropertiesManager.getDynamicPatterns()) {
                ImagePattern.createDynamicPattern(testName, elementName, imageDetailsNameShort, searchType, similarity);
            }
        }
    }
}
