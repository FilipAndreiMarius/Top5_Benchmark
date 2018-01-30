package org.mozilla.benchmark.utils;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

/**
 * Created by Silviu on 06/12/2017.
 */
public class FileManager {

    private static final Logger logger = LogManager.getLogger(FileManager.class.getName());

    public static int transformSecondsToFrames(int seconds) {
        return seconds * Constants.Video.FPS;
    }

    public static int getIntFromString(String s) {
        return Integer.parseInt(s.replaceAll("[\\D]", ""));
    }

    public static void removeFiles(String directoryName, int seconds) {
        File folder = new File(directoryName);
        File[] files = folder.listFiles();
        int frames = transformSecondsToFrames(seconds);

        if (files == null) {
            logger.warn("No files deleted");
        } else {

            for (File file : files) {
                if (getIntFromString(file.getName()) < frames) {
                    if (file.delete()) {
                        logger.debug("Deleted: [" + file.getName() + "]");
                    } else {
                        logger.error("Failed to delete " + file.getName());
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
        logger.info("Creating: [" + filename + "] ... ");
        boolean successful = dir.mkdirs();
        if (successful) {
            logger.debug("[" + filename + "] created successfully !!!");
        } else {
            logger.error("Failed to create [" + filename + "] !!!");
        }
        return successful;
    }

    public static void copyImage(String sourcePath, String destinationPath) {
        File source = new File(sourcePath);
        File destination = new File(destinationPath);
        try {
            FileUtils.copyFile(source, destination);
        } catch (IOException e) {
            logger.error("Failed to copy file!!!" + e);
        }
    }
}
