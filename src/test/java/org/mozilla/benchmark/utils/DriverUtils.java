package org.mozilla.benchmark.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mozilla.benchmark.objects.TimestampContainer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;

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
                logger.info("Setting [" + Constants.Driver.WEBDRIVER_PROPERTY + "] property ...");
                System.setProperty(Constants.Driver.WEBDRIVER_PROPERTY, Constants.Driver.WEBDRIVER_PATH);
                FirefoxProfile profile = null;
                try {
                    profile = new FirefoxProfile(new File(Constants.Paths.PROFILE_PATH));
                }catch (Exception e){
                    logger.fatal(String.format("Cannot find Profile file !!! [%s]", e));
                    if (PropertiesManager.getExitIfErrorsFound()) {
                        System.exit(1);
                    }
                }
                FirefoxOptions options = new FirefoxOptions();
                options.setProfile(profile);
                options.addPreference("browser.link.open_newwindow", 3);
                if (Constants.FirefoxPrefs.GFX_WEBRENDER_BLOB_IMAGES != null) {
                    logger.info("Adding [" + Constants.FirefoxPrefs.GFX_WEBRENDER_BLOB_IMAGES_PREFERENCE + "] preference ...");
                    options.addPreference(Constants.FirefoxPrefs.GFX_WEBRENDER_BLOB_IMAGES_PREFERENCE, Constants.FirefoxPrefs.GFX_WEBRENDER_BLOB_IMAGES);
                }
                if (Constants.FirefoxPrefs.GFX_WEBRENDER_ENABLED != null) {
                    logger.info("Adding [" + Constants.FirefoxPrefs.GFX_WEBRENDER_ENABLED_PREFERENCE + "] preference ...");
                    options.addPreference(Constants.FirefoxPrefs.GFX_WEBRENDER_ENABLED_PREFERENCE, Constants.FirefoxPrefs.GFX_WEBRENDER_ENABLED);
                }



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
