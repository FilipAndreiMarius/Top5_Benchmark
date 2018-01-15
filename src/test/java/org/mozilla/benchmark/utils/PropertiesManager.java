package org.mozilla.benchmark.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Silviu on 18/12/2017.
 */
public class PropertiesManager {

    private static final Logger logger = LogManager.getLogger(PropertiesManager.class.getName());
    private static Properties prop = loadProperties(Constants.Paths.PROP_FILE_PATH);

    private static String getString(String key) {
        String value = prop.getProperty(key);
        return (isNullOrEmpty(value) ? "" : value.trim());
    }

    private static Integer getInteger(String key) {
        String value = getString(key);
        return isNullOrEmpty(value) ? 0 : Integer.valueOf(value);
    }

    private static Boolean getBoolean(String key) {
        String value = getString(key);

        return isNullOrEmpty(value) ? null : Boolean.valueOf(value);
    }

    public static int getNumberOfRuns() {
        return getInteger("numberOfRuns");
    }

    public static int getFps() {
        return getInteger("fps");
    }

    public static String getVideoExtension() {
        return getString("videoExtension");
    }

    public static String getScreenshotExtension() {
        return getString("screenshotExtension");
    }

    public static Boolean getGfxWebrenderEnabled() {
        return getBoolean(Constants.FirefoxPrefs.GFX_WEBRENDER_ENABLED_PREFERENCE);
    }

    public static Boolean getGfxWebrenderBlobImages() {
        return getBoolean(Constants.FirefoxPrefs.GFX_WEBRENDER_BLOB_IMAGES_PREFERENCE);
    }

    public static String[] getExecutedScenarios() {
        return getString("executedScenarios").split(",");
    }

    public static Properties loadProperties(String inputFilePath) {
        Properties properties = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream(inputFilePath);
            properties.load(input);
        } catch (IOException ex) {
            logger.error("Could not load " + inputFilePath + " " + ex);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    logger.error("Could not close " + inputFilePath + " " + e);
                }
            }
        }
        return properties;
    }

    private static boolean isNullOrEmpty(String string) {
        return ((string == null) || (string.trim().length() == 0));
    }
}
