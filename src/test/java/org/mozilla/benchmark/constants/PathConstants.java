package org.mozilla.benchmark.constants;

import org.mozilla.benchmark.utils.TimeManager;

import java.io.File;

/**
 * Created by Silviu on 14/02/2018.
 */
public final class PathConstants {

    static final String PROJECT_LOCATION = java.lang.System.getProperty("user.dir");

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
    private static final String GMAIL_LOADING_FILE_NAME = "gmail_loading.png";
    private static final String ROOT_PATH = PROJECT_LOCATION + File.separator + RUNS_FOLDER + File.separator + TimeManager.getFormattedTimestamp(TimeManager.getCurrentTimestamp());

    public static final String RESOURCES_PATH = PROJECT_LOCATION + File.separator + SRC_FOLDER + File.separator + TEST_FOLDER + File.separator + RESOURCES_FOLDER;
    public static final String SPLIT_VIDEO_PATH = ROOT_PATH + File.separator + IMAGES_FOLDER;
    public static final String VIDEOS_PATH = ROOT_PATH + File.separator + VIDEOS_FOLDER;
    public static final String FPS_60_VIDEO_PATH = ROOT_PATH + File.separator + FPS_60_VIDEO_FOLDER;
    public static final String PATTERNS_PATH = ROOT_PATH + File.separator + PATTERNS_FOLDER;
    public static final String PROP_FILE_PATH = RESOURCES_PATH + File.separator + CONFIG_PROPERTIES_FILE_NAME;
    public static final String LOAD_DONE_PATH = RESOURCES_PATH + File.separator + LOAD_DONE_FILE_NAME;
    public static final String LOAD_PENDING_PATH = RESOURCES_PATH + File.separator + LOAD_PENDING_FILE_NAME;
    public static final String GMAIL_LOADING_PATH = RESOURCES_PATH + File.separator + GMAIL_LOADING_FILE_NAME;
    public static final String PAGE_OBJECT_CLASS_PATH = "org.mozilla.benchmark.pageObjects.";

    private PathConstants() {
    }
}
