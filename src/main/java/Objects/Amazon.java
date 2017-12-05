package Objects;

import Constants.Object_Constants;
import Utils.Utils;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;

import static Constants.Object_Constants.*;

/**
 * Created by andrei.filip on 10/4/2017.
 */
public class Amazon {
    JsonObject reportObject=new JsonObject();
    Utils utils = new Utils();






    public JsonObject getReportObject() {
        System.out.print("JsonObject resulted:"+"\n"+reportObject);
        return reportObject;
    }




    public JsonObject getFirstNonBlankHero() throws IOException {
        reportObject=new JsonObject();
        int frame_number = 0;
        int counter = 0;

        ArrayList<Object> images_array = new ArrayList<>();
        ArrayList<Object> image_patterns = new ArrayList<>();
        Boolean First_non_blank_found=false;
        Boolean SearchBar=false;
        Boolean LordOfTheRingsSearch=false;



        images_array = utils.getImages(Object_Constants.AMAZON_IMAGE_FOLDER);
        image_patterns=utils.getImages(AMAZON_PATTERN_FOLDER);

        for (int i = 0; i < image_patterns.size(); i++) {
            for(int j=0;j<images_array.size();j++) {
                  Object p = images_array.get(j);
                  String path_pattern = p.toString();
                   String fff=image_patterns.get(i).toString();

                 Boolean result =false;

                /*Utils.run(path_pattern, fff);*/


                result = Utils.searchImage(path_pattern, fff, (float) 0.8);
                  counter = j;

                  if (result==true && fff.contains("FirstNonBlank")) {
                      frame_number = counter;
                      reportObject.addProperty(FIRST_NON_BLANK, frame_number);
                      First_non_blank_found=true;
                      i=i+1;

                  }
                  if (First_non_blank_found==true&&result==true && fff.contains("SearchBarHeroElement")) {
                      frame_number = counter;
                      reportObject.addProperty(SEARCH_BAR_HERO, frame_number);
                      SearchBar=true;
                      i=i+1;
                  }
                  if (/*First_non_blank_found==true&&*/result==true && fff.contains("SearchLordOfTheRings.png")) {
                      frame_number = counter;
                      reportObject.addProperty(LORD_OF_THE_RINGS_SEARCH_ACTION, frame_number);
                      LordOfTheRingsSearch=true;
                      break;
                  }
                  if(SearchBar && First_non_blank_found &&LordOfTheRingsSearch){
                      return reportObject;
                  }
     // break;
              }
        }
        return reportObject;
    }


}
