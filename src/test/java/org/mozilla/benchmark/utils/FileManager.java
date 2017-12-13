package org.mozilla.benchmark.utils;

import org.mozilla.benchmark.objects.TimestampContainer;

import java.io.File;

/**
 * Created by Silviu on 06/12/2017.
 */
public class FileManager {

    public static String createDirectory(String directoryName) {

        File file = new File(directoryName);
        String PATH = "";

        if (!file.exists()) {
            System.out.println("Creating directory:" + file.getName());
            Boolean result = false;

            try {
                file.mkdir();
                result = true;
                PATH = file.getAbsolutePath();
            } catch (SecurityException se) {
                se.getLocalizedMessage();
            }
            if (result) {
                System.out.print("DIR created");

                return PATH;
            }
        } else {
            System.out.println("A directory with this name already exists:");
        }
        return PATH;
    }

    public static int transformSecondsToFrames(int seconds) {
        return seconds * Constants.FPS;
    }

    public static int getIntFromString(String s){
        return Integer.parseInt(s.replaceAll("[\\D]", ""));
    }

    public static void removeFiles(String directoryName, int seconds) {
        File folder = new File(directoryName);
        int frames = transformSecondsToFrames(seconds);

        for (File file : folder.listFiles()) {
            if (getIntFromString(file.getName()) < frames) {
                file.delete();
            }
        }
    }

    public static Boolean createDirectories(String filename) {
        File dir = new File(filename);

        boolean successful = dir.mkdirs();
        if (successful)
        {
            // created the directories successfully
            System.out.println("directories were created successfully");
        }
        else
        {
            // something failed trying to create the directories
            System.out.println("failed trying to create the directories");
        }
        return successful;
    }


    //contructs a test path based on a test name
    public static String setTestPath(String testNm) {
        String testPath = null;
        switch (testNm) {
            case "Google":
                testPath = "Google" + "\\" + testNm;
                break;
            case "Gmail":
                testPath = "Gmail" + "\\" + testNm;
                break;
            case "Amazon":
                testPath = "Amazon" + "\\" + testNm;
                break;
            case "Facebook":
                testPath = "Facebook" + "\\" + testNm;
                break;
            case "Youtube":
                testPath = "Youtube" + "\\" + testNm;
                break;
        }
        return testPath;
    }
}
