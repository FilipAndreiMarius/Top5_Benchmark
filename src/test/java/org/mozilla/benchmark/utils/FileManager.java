package org.mozilla.benchmark.utils;

import org.apache.commons.io.FileUtils;
import org.mozilla.benchmark.constants.VideoConstants;
import org.mozilla.benchmark.objects.LoggerManagerLevel;

import java.io.File;
import java.io.IOException;

/**
 * Created by Silviu on 06/12/2017.
 */
public class FileManager {

    private static final LoggerManager logger = new LoggerManager(FileManager.class.getName());

    public static int transformSecondsToFrames(int seconds) {
        return seconds * VideoConstants.FFMPEG_FINAL_FPS;
    }

    public static int getIntFromString(String s) {
        return Integer.parseInt(s.replaceAll("[\\D]", ""));
    }

    public static void removeFiles(String directoryName, int seconds) {
        File folder = new File(directoryName);
        File[] files = folder.listFiles();
        int frames = transformSecondsToFrames(seconds);

        if (files == null) {
            logger.log(LoggerManagerLevel.WARN, "No files deleted", false);
        } else {

            for (File file : files) {
                if (getIntFromString(file.getName()) < frames) {
                    if (file.delete()) {
                        logger.log(LoggerManagerLevel.DEBUG, String.format("Deleted: [%s]", file.getName()), false);
                    } else {
                        logger.log(LoggerManagerLevel.ERROR, String.format("Failed to delete [%s]", file.getName()), PropertiesManager.getEmailNotification());
                    }
                }
            }
        }
    }

    public static Boolean fileFound(String fileName, String directoryName) {
        File folder = new File(directoryName);
        File[] files = folder.listFiles();
        if (files == null || files.length == 0) {
            return false;
        }
        for (File file : files) {
            if (fileName.equals(file.getName())) {
                return true;
            }
        }
        return false;
    }

    public static int filesFoundCount(String fileName, String directoryName) {
        int count = 0;
        File folder = new File(directoryName);
        File[] files = folder.listFiles();
        if (files == null || files.length == 0) {
            return 0;
        }
        for (File file : files) {
            if ((file.getName().contains(fileName))) {
                count++;
            }
        }
        return count;
    }

    public static Boolean createDirectories(String filename) {
        File dir = new File(filename);
        logger.log(LoggerManagerLevel.INFO, String.format("Creating:  [%s]", filename), false);
        boolean successful = dir.mkdirs();
        if (successful) {
            logger.log(LoggerManagerLevel.DEBUG, String.format("[%s] created successfully !!!", filename), false);
        } else {
            logger.log(LoggerManagerLevel.ERROR, String.format("Failed to create [%s]", filename), PropertiesManager.getEmailNotification());
        }
        return successful;
    }

    public static void copyImage(String sourcePath, String destinationPath) {
        File source = new File(sourcePath);
        File destination = new File(destinationPath);
        try {
            FileUtils.copyFile(source, destination);
        } catch (IOException e) {
            logger.log(LoggerManagerLevel.ERROR, String.format("Failed to copy file [%s]: [%s]" , source.getName(), e), PropertiesManager.getEmailNotification());
        }
    }
}
