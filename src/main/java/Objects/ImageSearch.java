package Objects;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by andrei.filip on 11/22/2017.
 */
public class ImageSearch {

    //hardcoded value just for testing
    static String testName = "Gsearch";


    public static ArrayList<Object> getSplitedVideo(String testName) throws IOException {
        testName = testName;
        ArrayList<Object> images_array = null;
        File splitedVideoDirectory = new File("SplitedVideos");
        File[] splitedVideoForders = splitedVideoDirectory.listFiles();

        for (File videoFolder : splitedVideoForders) {
            if (videoFolder.getName().contains(testName)) {
                images_array = Utils.Utils.getImages(videoFolder.getPath());

            }
            return images_array;
        }
        return images_array;
    }

    public static ArrayList<Object> getPatterns(String testName) throws IOException {
        testName = testName;
        ArrayList<Object> images_array = null;
        File patternFolder = new File("Patterns");
        File[] patternDirectoriesArray = patternFolder.listFiles();

        for (File folder : patternDirectoriesArray) {
            if (folder.getName().contains(testName)) ;
            {
                images_array = Utils.Utils.getImages(folder.getPath());
            }

            return images_array;
        }
        return images_array;
    }







    public static void main(String args[]) throws IOException {


        ArrayList<Object> images_array=  getSplitedVideo("Gsearch");
        System.out.print(images_array);


    }


}
