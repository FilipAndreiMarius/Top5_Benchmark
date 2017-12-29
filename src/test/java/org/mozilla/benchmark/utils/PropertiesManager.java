package org.mozilla.benchmark.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Silviu on 18/12/2017.
 */
public class PropertiesManager {

    private static final Logger logger = LogManager.getLogger(PropertiesManager.class.getName());

    public static String getString(Properties properties, String key) {
        String value = properties.getProperty(key);
        return (isNullOrEmpty(value) ? "" : value.trim());
    }

    public static Integer getInteger(Properties properties, String key) {
        String value = getString(properties, key);
        return isNullOrEmpty(value) ? 0 : Integer.valueOf(value);
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
