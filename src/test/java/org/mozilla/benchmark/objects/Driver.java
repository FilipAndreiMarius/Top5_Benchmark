package org.mozilla.benchmark.objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mozilla.benchmark.utils.Constants;
import org.mozilla.benchmark.utils.TimeManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * Created by Silviu on 03/01/2018.
 */
public class Driver {

    private static final Logger logger = LogManager.getLogger(Driver.class.getName());
    private static WebDriver instance = null;

    public static WebDriver getInstance() {
        if (instance == null) {
            synchronized (Driver.class) {
                logger.info("Initializing Driver ...");
                logger.info("Setting " + Constants.Driver.WEBDRIVER_PROPERTY + " property ...");
                System.setProperty(Constants.Driver.WEBDRIVER_PROPERTY, Constants.Driver.WEBDRIVER_PATH);
                FirefoxOptions options = new FirefoxOptions();
                if (Constants.FirefoxPrefs.GFX_WEBRENDER_BLOB_IMAGES != null) {
                    logger.info("Adding " + Constants.FirefoxPrefs.GFX_WEBRENDER_BLOB_IMAGES_PREFERENCE + " preference ...");
                    options.addPreference(Constants.FirefoxPrefs.GFX_WEBRENDER_BLOB_IMAGES_PREFERENCE, Constants.FirefoxPrefs.GFX_WEBRENDER_BLOB_IMAGES);
                }
                if (Constants.FirefoxPrefs.GFX_WEBRENDER_ENABLED != null) {
                    logger.info("Adding " + Constants.FirefoxPrefs.GFX_WEBRENDER_ENABLED_PREFERENCE + " preference ...");
                    options.addPreference(Constants.FirefoxPrefs.GFX_WEBRENDER_ENABLED_PREFERENCE, Constants.FirefoxPrefs.GFX_WEBRENDER_ENABLED);
                }
                instance = new FirefoxDriver(options);
                TimestampContainer.getInstance().setMaximize(TimeManager.getCurrentTimestamp());
                logger.info("Maximizing window ...");
                instance.manage().window().maximize();
            }
        }
        logger.info("Driver initialized !");
        return instance;
    }

    public static void closeWebBrowser(){
        logger.info("Closing Driver ...");
        if (null != instance){
            instance.quit();
        }
        instance = null;
        logger.info("Driver closed !");
    }

}
