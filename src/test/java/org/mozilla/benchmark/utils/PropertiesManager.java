package org.mozilla.benchmark.utils;

import org.mozilla.benchmark.constants.FirefoxPrefsConstants;
import org.mozilla.benchmark.constants.PathConstants;
import org.mozilla.benchmark.objects.LoggerManagerLevel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Silviu on 18/12/2017.
 */
public class PropertiesManager {

    private static final LoggerManager logger = new LoggerManager(PropertiesManager.class.getName());
    private static Properties prop = loadProperties(PathConstants.PROP_FILE_PATH);

    private static String getString(String key) {
        String value = prop.getProperty(key);
        return (isNullOrEmpty(value) ? "" : value.trim());
    }

    private static Integer getInteger(String key) {
        String value = getString(key);
        return isNullOrEmpty(value) ? 0 : Integer.valueOf(value);
    }

    private static float getFloat(String key) {
        String value = getString(key);
        return isNullOrEmpty(value) ? 0 : Float.valueOf(value);
    }

    private static Boolean getBoolean(String key) {
        String value = getString(key);

        return isNullOrEmpty(value) ? null : Boolean.valueOf(value);
    }

    public static int getNumberOfRuns() {
        return getInteger("number_of_runs");
    }

    public static String getProfilePath() {
        return getString("profile_path");
    }

    public static Boolean getExitIfErrorsFound() {
        return getBoolean("exit_if_errors_found");
    }

    public static Boolean getDynamicPatterns() {
        return getBoolean("dynamic_patterns");
    }

    public static float getDefaultSimilarity() {
        return getFloat("default_similarity");
    }

    public static int getFfmpegInitialFps() {
        return getInteger("ffmpeg_initial_fps");
    }

    public static int getFfmpegFinalFps() {
        return getInteger("ffmpeg_final_fps");
    }

    public static int getFfmpegRecordDuration() {
        return getInteger("ffmpeg_record_duration");
    }

    public static String getVideoExtension() {
        return getString("video_extension");
    }

    public static String getImageExtension() {
        return getString("image_extension");
    }

    public static Boolean getGfxWebrenderAll() {
        return getBoolean(FirefoxPrefsConstants.GFX_WEBRENDER_ALL_PREFERENCE);
    }

    public static String[] getScenariosToExecute() {
        return getString("scenarios_to_execute").split(",");
    }

    public static String getEmailSender() {
        return getString("email_sender");
    }

    public static String getErrorEmailRecipients() {
        return getString("error_email_recipients");
    }

    public static String getResultsEmailRecipients() {
        return getString("results_email_recipients");
    }

    public static String getEmailUserName() {
        return getString("email_user_name");
    }

    public static String getEmailPassword() {
        return getString("email_password");
    }

    public static Boolean getEmailNotification() {
        return getBoolean("email_notification");
    }

    public static Properties loadProperties(String inputFilePath) {
        Properties properties = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream(inputFilePath);
            properties.load(input);
        } catch (IOException e) {
            logger.log(LoggerManagerLevel.ERROR, String.format("Could NOT load [%s]: [%s]", inputFilePath, ErrorManager.getErrorMessage(e.getStackTrace())), PropertiesManager.getEmailNotification());
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    logger.log(LoggerManagerLevel.ERROR, String.format("Could NOT close [%s]: [%s]", inputFilePath, ErrorManager.getErrorMessage(e.getStackTrace())), PropertiesManager.getEmailNotification());
                }
            }
        }
        return properties;
    }

    private static boolean isNullOrEmpty(String string) {
        return ((string == null) || (string.trim().length() == 0));
    }
}
