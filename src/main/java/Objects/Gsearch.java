package Objects;
/**
 * Created by andrei.filip on 10/4/2017.
 */

import PageLoadFlows.GooglePage;
import Utils.ObjectTypes;
import Utils.Utils;
import VideoProcessor.VideoCapture;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;

import static Constants.Object_Constants.*;


public class Gsearch extends ImageSearch {

    Boolean First_non_blank_found=false;
    Boolean SearchBar=false;
    Boolean LordOfTheRingsSearch=false;
    JsonObject reportObject;
    String testName= ObjectTypes.GOOGLE.name;
    ArrayList<Object> images_array;
    ArrayList<Object> image_patterns;
    JsonArray  resultsArray = new JsonArray();


    public JsonObject getReportObject() {
        System.out.print("JsonObject resulted:"+"\n"+reportObject);
        return reportObject;
    }



    public JsonArray getFirstNonBlankHero() throws IOException {

        reportObject=new JsonObject();
        int frame_number ;
        int counter ;


        resultsArray = new JsonArray();
        reportObject = new JsonObject();
        Boolean StoriesHero = false;
        Boolean AccessImages = false;
        int found=0;
        int accesImag=0;



        Boolean LastHero = false;
        Boolean zeroElement=false;
        float similarity= (float) 0.95;

        images_array=getPatterns(testName,"SplitedVideos");
        image_patterns=getPatterns(testName,"Patterns");
        for (int i = 0; i < image_patterns.size(); i++) {
            for (int j = 1370; j < images_array.size(); j++) {
                Object p = images_array.get(j);
                String path_pattern = p.toString();
                String imagePattern = image_patterns.get(i).toString();

                Boolean result = false;


                result = Utils.searchImage(path_pattern, imagePattern,similarity);
                System.out.println("Search nr:"+j);
                counter = j;
               //first time google search is  used
                if (result&&imagePattern.contains("zero")) {
                    found++;
                    similarity= (float) 0.95;
                    if(found>1) {
                        frame_number = counter;
                        reportObject.addProperty(ZERO_ELEMENT, frame_number);
                        System.out.println(reportObject);
                        similarity= (float) 0.95;
                        zeroElement = true;

                    }
                    i = i + 1;

                }

                if (zeroElement && result && imagePattern.contains("FirstNonBlank")) {
                    frame_number = counter;
                    reportObject.addProperty(FIRST_NON_BLANK, frame_number);
                    System.out.println("First non Blank:"+reportObject);
                    First_non_blank_found = true;
                    i = i + 1;
                }
                if (First_non_blank_found  && result  && imagePattern.contains("HeroElement")) {
                    frame_number = counter;
                    reportObject.addProperty(TOP_STORIES_HERO, frame_number);
                    System.out.println("Top Stories:"+reportObject);
                    StoriesHero = true;
                    similarity= (float) 0.95;
                    i = i + 1;
                    j=j+15;
                }
                if (/*StoriesHero &&*/ result && imagePattern.contains("AccessImages")) {
                    accesImag++;
                    i = i + 1;
                    similarity = (float) 0.99;
                    if(accesImag>2) {
                        accesImag=0;
                        frame_number = counter;
                        reportObject.addProperty(ACCESS_IMAGES, frame_number);
                        System.out.println("Access images:" + reportObject);
                        AccessImages = true;
                        similarity = (float) 0.95;
                    }
                }

                if (First_non_blank_found  && result  && imagePattern.contains("JimagesNonBlank")) {
                    frame_number = counter;
                    reportObject.addProperty(IMAGES_NON_BLANK, frame_number);
                    System.out.println("image non blank:"+reportObject);
                    similarity= (float) 0.95;
                    i = i + 1;
                }



                if (First_non_blank_found && result && imagePattern.contains("LastHero.png")) {
                    frame_number = counter;
                    reportObject.addProperty(LAST_HERO, frame_number);
                    System.out.println("last hero"+reportObject);
                    System.out.println("Results Array" + resultsArray);
                    LastHero = true;
                    resultsArray.add(reportObject);
                    System.out.println("Result object: " + reportObject);
                }
                if (First_non_blank_found && StoriesHero && LastHero ) {
                    while(LastHero){
                        LastHero= Utils.searchImage(path_pattern,  images_array.get(j).toString(),similarity);
                        j=j+1;

                    }

                    First_non_blank_found = false;
                    StoriesHero = false;
                    LastHero = false;
                    i=0;
                    found=0;
                    accesImag=0;
                    reportObject = new JsonObject();
                }

                if(j == images_array.size()){
                    return resultsArray;
                }






            }
        }

        return resultsArray;
    }




    public static void main(String args[]) throws IOException {
       /*Thread recordVideo = new VideoCapture("30", "120", "runVideo", ObjectTypes.GOOGLE.name);
        recordVideo.start();

        Thread a = new GooglePage(10);
        a.start();

       try {
            recordVideo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread compress =new VideoCapture("compress",ObjectTypes.GOOGLE.name);
        compress.start();

        try {
            compress.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Thread splitVideo =new VideoCapture("splitVideo",ObjectTypes.GOOGLE.name);
        splitVideo.start();*/


        Gsearch g = new Gsearch();
        g.getFirstNonBlankHero();
    }
}
