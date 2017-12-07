package org.mozilla.benchmark.objects;

import org.mozilla.benchmark.utils.ImageManager;
import com.google.gson.JsonObject;
import org.mozilla.benchmark.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by andrei.filip on 10/4/2017.
 */
public class Amazon {

    JsonObject reportObject = new JsonObject();

    public JsonObject getReportObject() {
        System.out.print("JsonObject resulted:" + "\n" + reportObject);
        return reportObject;
    }

    public JsonObject getFirstNonBlankHero() throws IOException {

        reportObject = new JsonObject();

        Boolean firstNonBlankFound = false;
        Boolean SearchBar = false;
        Boolean LordOfTheRingsSearch = false;

        ArrayList<Object> images_array = ImageManager.getImages(Constants.Patterns.AMAZON_IMAGE_FOLDER);
        ArrayList<Object> image_patterns = ImageManager.getImages(Constants.Patterns.AMAZON_PATTERN_FOLDER);

        for (int i = 0; i < image_patterns.size(); i++) {
            for (int j = 0; j < images_array.size(); j++) {

                String path_pattern = images_array.get(j).toString();
                String fff = image_patterns.get(i).toString();

                Boolean result = ImageManager.searchImage(path_pattern, fff, 0.8f);

                if (result && fff.contains("FirstNonBlank")) {
                    reportObject.addProperty(Constants.Elements.FIRST_NON_BLANK, j);
                    firstNonBlankFound = true;
                    i++;
                }

                if (firstNonBlankFound && result && fff.contains("SearchBarHeroElement")) {
                    reportObject.addProperty(Constants.Elements.SEARCH_BAR_HERO, j);
                    SearchBar = true;
                    i++;
                }

                if (/*firstNonBlankFound==true&&*/result && fff.contains("SearchLordOfTheRings.png")) {
                    reportObject.addProperty(Constants.Elements.LORD_OF_THE_RINGS_SEARCH_ACTION, j);
                    LordOfTheRingsSearch = true;
                    break;
                }
                if (SearchBar && firstNonBlankFound && LordOfTheRingsSearch) {
                    return reportObject;
                }
            }
        }
        return reportObject;
    }
}
