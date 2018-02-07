package org.mozilla.benchmark.utils;

import java.io.File;

/**
 * Created by Silviu on 06/12/2017.
 */
public class Constants {

    public static final class Paths {

        public static final String PROJECT_LOCATION = java.lang.System.getProperty("user.dir");
        private static final String RUNS_FOLDER = "runs";
        private static final String IMAGES_FOLDER = "images";
        private static final String VIDEOS_FOLDER = "videos";
        private static final String FPS_60_VIDEO_FOLDER = "60FpsVideos";
        private static final String PATTERNS_FOLDER = "patterns";
        private static final String SRC_FOLDER = "src";
        private static final String TEST_FOLDER = "test";
        private static final String RESOURCES_FOLDER = "resources";
        private static final String CONFIG_PROPERTIES_FILE_NAME = "config.properties";
        private static final String LOAD_DONE_FILE_NAME = "load_done.png";
        private static final String LOAD_PENDING_FILE_NAME = "load_pending.png";

        public static final String ROOT_PATH = PROJECT_LOCATION + File.separator + RUNS_FOLDER + File.separator + TimeManager.getFormattedTimestamp(TimeManager.getCurrentTimestamp());
        public static final String RESOURCES_PATH = PROJECT_LOCATION + File.separator + SRC_FOLDER + File.separator +
                TEST_FOLDER + File.separator + RESOURCES_FOLDER;
        public static final String SPLIT_VIDEO_PATH = ROOT_PATH + File.separator + IMAGES_FOLDER;
        public static final String VIDEOS_PATH = ROOT_PATH + File.separator + VIDEOS_FOLDER;
        public static final String FPS_60_VIDEO_PATH = ROOT_PATH + File.separator + FPS_60_VIDEO_FOLDER;

        public static final String PATTERNS_PATH = ROOT_PATH + File.separator + PATTERNS_FOLDER;
        public static final String PROP_FILE_PATH = RESOURCES_PATH + File.separator + CONFIG_PROPERTIES_FILE_NAME;
        public static final String LOAD_DONE_PATH = RESOURCES_PATH + File.separator + LOAD_DONE_FILE_NAME;
        public static final String LOAD_PENDING_PATH = RESOURCES_PATH + File.separator + LOAD_PENDING_FILE_NAME;
        public static final String PAGE_OBJECT_CLASS_PATH = "org.mozilla.benchmark.pageObjects.";

        private Paths() {
        }
    }

    public static final class Execution {

        public static final int NUMBER_OF_RUNS = PropertiesManager.getNumberOfRuns();
        public static final String[] EXECUTED_SCENARIOS = PropertiesManager.getScenariosToExecute();

        private Execution() {
        }
    }

    public static final class Video {

        public static final int FFMPEG_FINAL_FPS = PropertiesManager.getFfmpegFinalFps();
        public static final int FFMPEG_INITIAL_FPS = PropertiesManager.getFfmpegInitialFps();
        public static final int FFMPEG_RECORD_DURATION = PropertiesManager.getFfmpegRecordDuration();

        private Video() {
        }
    }

    public static final class Extensions {

        public static final String VIDEO_EXTENSION = "." + PropertiesManager.getVideoExtension();
        public static final String IMAGE_EXTENSION = PropertiesManager.getImageExtension();
        public static final String JSON_EXTENSION = ".json";

        private Extensions() {
        }
    }

    public static final class FirefoxPrefs {

        public static final String GFX_WEBRENDER_ENABLED_PREFERENCE = "gfx.webrender.enabled";
        public static final String GFX_WEBRENDER_BLOB_IMAGES_PREFERENCE = "gfx.webrender.blob-images";
        public static final Boolean GFX_WEBRENDER_ENABLED = PropertiesManager.getGfxWebrenderEnabled();
        public static final Boolean GFX_WEBRENDER_BLOB_IMAGES = PropertiesManager.getGfxWebrenderBlobImages();

        private FirefoxPrefs() {
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
        public static final String SEARCH_ITEM = "barack obama";

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
