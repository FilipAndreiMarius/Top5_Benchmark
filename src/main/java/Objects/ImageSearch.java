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

   //String testName"Gsearch,Amazon,Gmail,Facebook
   //path_name:'SplitedVideos' ,'Patterns'

    public static ArrayList<Object> getPatterns(String testName,String pathName) throws IOException {
        ArrayList<Object> images_array = null;
        File patternFolder = new File(pathName);
        File[] patternDirectoriesArray = patternFolder.listFiles();

        for (File folder : patternDirectoriesArray) {
            if (folder.getName().contains(testName)) {
                images_array = Utils.Utils.getImages(folder.getPath());
                return images_array;
            }
        }
        return images_array;
    }





}
