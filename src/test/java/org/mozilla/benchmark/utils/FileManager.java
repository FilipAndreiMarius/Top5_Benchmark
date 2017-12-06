package org.mozilla.benchmark.utils;

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
}
