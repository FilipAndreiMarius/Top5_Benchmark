package org.mozilla.benchmark.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mozilla.benchmark.constants.FirefoxPrefsConstants;
import org.mozilla.benchmark.constants.PathConstants;
import org.mozilla.benchmark.constants.WebPageConstants;
import org.mozilla.benchmark.constants.WebdriverConstants;
import org.mozilla.benchmark.objects.TimestampContainer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.io.File;

/**
 * Created by Silviu on 03/01/2018.
 */
public class DriverUtils {

    private static final Logger logger = LogManager.getLogger(DriverUtils.class.getName());
    private static WebDriver instance = null;

    public static WebDriver getInstance() {
        if (instance == null) {
            synchronized (DriverUtils.class) {
                logger.info("Initializing Driver ...");
                logger.info("Setting [" + WebdriverConstants.WEBDRIVER_PROPERTY + "] property ...");
                System.setProperty(WebdriverConstants.WEBDRIVER_PROPERTY, WebdriverConstants.WEBDRIVER_PATH);

                File profileFile = new File(PropertiesManager.getProfilePath());
                if(!profileFile.exists()) {
                    logger.fatal(String.format("Could not locate Firefox Profile. Check if the provided path is correct: [%s]", profileFile));
                    System.exit(1);
                }

                FirefoxProfile profile = new FirefoxProfile(profileFile);
                FirefoxOptions options = new FirefoxOptions();
                options.setProfile(profile);

                if (FirefoxPrefsConstants.GFX_WEBRENDER_BLOB_IMAGES != null) {
                    logger.info("Adding [" + FirefoxPrefsConstants.GFX_WEBRENDER_BLOB_IMAGES_PREFERENCE + "] preference ...");
                    options.addPreference(FirefoxPrefsConstants.GFX_WEBRENDER_BLOB_IMAGES_PREFERENCE, FirefoxPrefsConstants.GFX_WEBRENDER_BLOB_IMAGES);
                }
                if (FirefoxPrefsConstants.GFX_WEBRENDER_ENABLED != null) {
                    logger.info("Adding [" + FirefoxPrefsConstants.GFX_WEBRENDER_ENABLED_PREFERENCE + "] preference ...");
                    options.addPreference(FirefoxPrefsConstants.GFX_WEBRENDER_ENABLED_PREFERENCE, FirefoxPrefsConstants.GFX_WEBRENDER_ENABLED);
                }

                options.addPreference(FirefoxPrefsConstants.STARTUP_HOMEPAGE_PREFERENCE, WebPageConstants.HOME_PAGE_URL);

                instance = new FirefoxDriver(options);
                TimestampContainer.getInstance().setMaximize(TimeManager.getCurrentTimestamp());
                System.out.println("BROWSER START: " + TimestampContainer.getInstance().getMaximize());
            }
        }
        logger.info("Driver initialized !");
        return instance;
    }

    public static void closeWebBrowser() {
        logger.info("Closing Driver ...");
        if (null != instance) {
            instance.quit();
        }
        instance = null;
        logger.info("Driver closed !");
    }
}
