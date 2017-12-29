package org.mozilla.benchmark.utils;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by Silviu on 06/12/2017.
 */
public class Constants {

    public static final Properties PROPERTIES = PropertiesManager.loadProperties(Paths.PROP_FILE_PATH);

    public static final class Paths {

        public static final String PROJECT_LOCATION = java.lang.System.getProperty("user.dir");
        private static final String RUNS_FOLDER = "runs";
        private static final String IMAGES_FOLDER = "Images";
        private static final String VIDEOS_FOLDER = "Videos";
        private static final String FPS_60_VIDEO_FOLDER = "60FpsVideos";
        private static final String PATTERNS_FOLDER = "Patterns";

        public static final String ROOT_PATH = PROJECT_LOCATION + File.separator + RUNS_FOLDER + File.separator + TimeManager.getFormattedTimestamp(TimeManager.getCurrentTimestamp());
        public static final String SPLIT_VIDEO_PATH = ROOT_PATH + File.separator + IMAGES_FOLDER;
        public static final String VIDEOS_PATH = ROOT_PATH + File.separator + VIDEOS_FOLDER;
        public static final String FPS_60_VIDEO_PATH = ROOT_PATH + File.separator + FPS_60_VIDEO_FOLDER;
        public static final String PATTERNS_PATH = PROJECT_LOCATION + File.separator + PATTERNS_FOLDER;

        private static final String PROP_FILE_PATH = PropertiesManager.setConfigPath("config.properties");


        private Paths() {
        }
    }

    public static final class Execution {

        public static final int NUMBER_OF_RUNS = PropertiesManager.getInteger(PROPERTIES, "numberOfRuns");

        private Execution() {
        }
    }

    public static final class Video {

        public static final int FPS = PropertiesManager.getInteger(PROPERTIES, "fps");
        public static final String VIDEO_EXTENSION = PropertiesManager.getString(PROPERTIES, "videoExtension");

        private Video() {
        }
    }

    public static final List<String> GOOGLE_PATTERN_CATEGORIES = Arrays.asList(Elements.ZERO, Elements.FIRST_NON_BLANK,
            Elements.FIRST_HERO, Elements.ACCESS_IMAGES, Elements.IMAGES_NON_BLANK, Elements.LAST_HERO);

    public final class Elements {

        public static final String ZERO = "zero";
        public static final String FIRST_NON_BLANK = "firstNonBlank";
        public static final String FIRST_HERO = "firstHero";
        public static final String ACCESS_IMAGES = "accessImages";
        public static final String IMAGES_NON_BLANK = "imagesNonBlank";
        public static final String LAST_HERO = "lastHero";
        public static final String SEARCH_BAR_HERO = "search_bar_hero_element";
        public static final String SEARCH_BAR_FIRST_NON_BLANK = "search_bar_first_non_blank";
        public static final String LORD_OF_THE_RINGS_SEARCH_ACTION = "lord_of_the_rings_search_action";
        public static final String IMAGE_FIRST_NON_BLANK = "image_first_non_blank";

        private Elements() {
        }
    }

    public static final class Driver {

        public static final String WEBDRIVER_PROPERTY = "webdriver.gecko.driver";
        public static final String WEBDRIVER_PATH = Paths.PROJECT_LOCATION + "\\libs\\geckodriver.exe";

        private Driver() {
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
