package org.mozilla.benchmark.utils;

/**
 * Created by Silviu on 06/12/2017.
 */
public class Constants {
    public final class Elements {

        public static final String ZERO_ELEMENT = "zero_element";
        public static final String FIRST_NON_BLANK = "first_non_blank";
        public static final String SEARCH_BAR_HERO = "search_bar_hero_element";
        public static final String SEARCH_BAR_FIRST_NON_BLANK = "search_bar_first_non_blank";
        public static final String LORD_OF_THE_RINGS_SEARCH_ACTION = "lord_of_the_rings_search_action";
        public static final String IMAGE_FIRST_NON_BLANK = "image_first_non_blank";
        public static final String LAST_HERO = "last_hero";
        public static final String TOP_STORIES_HERO = "hero_element";
        public static final String ACCESS_IMAGES = "Access_Images";

        private Elements() {
        }
    }

    public final class Patterns {

        public static final String AMAZON_IMAGE_FOLDER = "C:\\Git\\Benchmark\\Ui_Tests\\60FpsVideos\\Video_Frames\\Amazon0.41200816949697131.mp4";
        public static final String AMAZON_PATTERN_FOLDER = "C:\\Users\\andrei.filip\\IdeaProjects\\SikuliCompare\\Patterns\\Amazon_Patterns";

        private Patterns() {
        }
    }

    public final class PageObjects {

        //Gmail constants
        public static final String YOUTUBE_LINK = "http://benjamin.smedbergs.us/tests/youtube-notification-mail/youtube-notification-mail-complete.html";
        public static final String GMAIL_URL = "https://accounts.google.com/";
        //credentials
        public static final String USER_NAME = "andrei.filip@softvision.ro";
        public static final String PASS = "slackwarE112";

        //Facebook constants
        public static final String FACEBOOK_URL = "https://www.facebook.com";
        //credentials
        public static final String FACEBOOK_USER_NAME = "benchmarkautomation_1@yahoo.com";
        public static final String FACEBOOK_PASS = "Softvision10";

        //GoogleSearch constants
        public static final String GSEARCH_URL = "https://google.com";
        public static final String SEARCH_ITEM = "barack Obama";

        //Youtube constants
        public static final String YOUTUBE_ITEM = "sarah jeffery recorder";
        public static final String YOUTUBE_URL = "https://www.Youtube.com";

        //Amazon constants
        public static final String AMAZON_URL = "https://www.Amazon.com";
        public static final String AMAZON_SEARCH_ITEM = "lord of the rings";

        private PageObjects() {
        }
    }

    public final class System {

        public static final int MAX_CPU_RETRIES = 10;
        public static final int CPU_CHECK_REFRESH_RATE = 1000;
        public static final double CPU_DESIRED = 20.0;

        private System() {
        }
    }

    private Constants() {
    }
}
