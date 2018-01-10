package org.mozilla.benchmark.gSheetReport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by andrei.filip on 12/21/2017.
 */
public class GSheetConstants {


    //'GoogleSheetId' only for testing
    static String spreadsheetId = "1cEJnQzZATVsdINb-gxhrtabGcsDFBOD8sEQHapUn7sc";
    //run numbers
    static List<String>RunList= Arrays.asList("Run:","1","2","3","4","5","6","7","8","9","10");
    static List<Object>RunListObject=new ArrayList<Object>(RunList);

    //GSearch Headers
    static List<String> GSearchHeaders= Arrays.asList("Navigation_Start","First_Non_Blank","Hero_Element","Image_Start","First_Non_Blank","Hero_Element");
    static List<Object>GSearchHeaderObject=new ArrayList<Object>(GSearchHeaders);

    //Gmail Headers
    static List<String>GmailHeaders= Arrays.asList("Navigation_Start","First_Non_Blank","Hero_Element","Image_Start","First_Non_Blank","Hero_Element");
    static List<Object>GmailHeaderObjects=new ArrayList<Object>(GmailHeaders);

    //Facebook Headers
    static List<String>FacebookHeaders= Arrays.asList("Navigation_Start","First_Non_Blank","Hero_Element","Image_Start","First_Non_Blank","Hero_Element");
    static List<Object>FacebookHeaderObjects=new ArrayList<Object>(FacebookHeaders);

    //Amazon Headers
    static List<String>AmazonHeaders= Arrays.asList("Navigation_Start","First_Non_Blank","Hero_Element","Image_Start","First_Non_Blank","Hero_Element");
    static List<Object>AmazonHeaderObjects=new ArrayList<Object>(AmazonHeaders);

    //Youtube Headers
    static List<String>YoutubeHeaders= Arrays.asList("Navigation_Start","First_Non_Blank","Hero_Element","Image_Start","First_Non_Blank","Hero_Element");
    static List<Object>YoutubeHeaderObjects=new ArrayList<Object>(YoutubeHeaders);



    //test Scenarios
    //when a new SpreadSheet is created this list is parsed and a new tab is created for each value
    static List<String> Scenarios=Arrays.asList("GoogleSearch","Gmail","Facebook","Amazon","Youtube");

    //mock data just for testing
    //delete after
    static String resultList = "[{\n" +
            "\t\"zero_element\": 1401,\n" +
            "\t\"first_non_blank\": 1436,\n" +
            "\t\"hero_element\": 1544,\n" +
            "\t\"Access_Images\": 1596,\n" +
            "\t\"images_non_blank\": 1690,\n" +
            "\t\"last_hero\": 1704\n" +
            "}, {\n" +
            "\t\"zero_element\": 1958,\n" +
            "\t\"first_non_blank\": 1968,\n" +
            "\t\"hero_element\": 2008,\n" +
            "\t\"Access_Images\": 2144,\n" +
            "\t\"images_non_blank\": 2202,\n" +
            "\t\"last_hero\": 2203\n" +
            "}, {\n" +
            "\t\"zero_element\": 2414,\n" +
            "\t\"first_non_blank\": 2430,\n" +
            "\t\"hero_element\": 2460,\n" +
            "\t\"Access_Images\": 2610,\n" +
            "\t\"images_non_blank\": 2646,\n" +
            "\t\"last_hero\": 2647\n" +
            "}, {\n" +
            "\t\"zero_element\": 2862,\n" +
            "\t\"first_non_blank\": 2878,\n" +
            "\t\"hero_element\": 2904,\n" +
            "\t\"Access_Images\": 3062,\n" +
            "\t\"images_non_blank\": 3106,\n" +
            "\t\"last_hero\": 3107\n" +
            "}, {\n" +
            "\t\"zero_element\": 3332,\n" +
            "\t\"first_non_blank\": 3342,\n" +
            "\t\"hero_element\": 3378,\n" +
            "\t\"Access_Images\": 3532,\n" +
            "\t\"images_non_blank\": 3572,\n" +
            "\t\"last_hero\": 3573\n" +
            "}, {\n" +
            "\t\"zero_element\": 3786,\n" +
            "\t\"first_non_blank\": 3798,\n" +
            "\t\"hero_element\": 3828,\n" +
            "\t\"Access_Images\": 3990,\n" +
            "\t\"images_non_blank\": 4044,\n" +
            "\t\"last_hero\": 4045\n" +
            "}, {\n" +
            "\t\"zero_element\": 4258,\n" +
            "\t\"first_non_blank\": 4270,\n" +
            "\t\"hero_element\": 4302,\n" +
            "\t\"Access_Images\": 4448,\n" +
            "\t\"images_non_blank\": 4486,\n" +
            "\t\"last_hero\": 4487\n" +
            "}, {\n" +
            "\t\"zero_element\": 4724,\n" +
            "\t\"first_non_blank\": 4734,\n" +
            "\t\"hero_element\": 4774,\n" +
            "\t\"Access_Images\": 4914,\n" +
            "\t\"images_non_blank\": 4956,\n" +
            "\t\"last_hero\": 4957\n" +
            "}, {\n" +
            "\t\"zero_element\": 5166,\n" +
            "\t\"first_non_blank\": 5176,\n" +
            "\t\"hero_element\": 5218,\n" +
            "\t\"Access_Images\": 5854,\n" +
            "\t\"images_non_blank\": 5892,\n" +
            "\t\"last_hero\": 5893\n" +
            "}]";

}



