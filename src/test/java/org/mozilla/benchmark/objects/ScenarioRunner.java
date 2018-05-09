package org.mozilla.benchmark.objects;

import org.mozilla.benchmark.constants.ExecutionConstants;
import org.mozilla.benchmark.constants.MailConstants;
import org.mozilla.benchmark.constants.VideoConstants;
import org.mozilla.benchmark.mail.MailBuilder;
import org.mozilla.benchmark.utils.LoggerManager;
import org.mozilla.benchmark.utils.PropertiesManager;
import org.mozilla.benchmark.utils.ThreadManager;
import org.mozilla.benchmark.utils.TimeManager;
import org.mozilla.benchmark.videoProcessor.VideoCapture;

/**
 * Created by silviu.checherita on 1/5/2018.
 */
public class ScenarioRunner extends Thread {

    private static final LoggerManager logger = new LoggerManager(ImageAnalyzer.class.getName());

    public ScenarioRunner(String testName, String buildID) {

        TimestampContainer.getInstance().setStartRunningTime(TimeManager.getCurrentTimestamp());
        logger.log(LoggerManagerLevel.INFO, "Start creating patterns ...", false);

        Thread createPatterns = ThreadManager.getPageObjectThread(testName, 1, PageNavigationTypes.SAVE_PATTERN);
        if (createPatterns != null) {
            createPatterns.run();
            try {
                createPatterns.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            logger.log(LoggerManagerLevel.FATAL, "Cannot create patterns !!!", PropertiesManager.getEmailNotification());
        }
        logger.log(LoggerManagerLevel.INFO, "Creating patterns done !!!", false);

        logger.log(LoggerManagerLevel.INFO, "Start Video Process ...", false);
        Thread recordVideo = new VideoCapture(VideoConstants.FFMPEG_INITIAL_FPS, VideoConstants.FFMPEG_RECORD_DURATION, VideoCaptureCommands.START_VIDEO, testName);
        recordVideo.start();

        Thread executeFlows = ThreadManager.getPageObjectThread(testName, ExecutionConstants.NUMBER_OF_RUNS, PageNavigationTypes.EXECUTE_FLOW);
        if (executeFlows != null) {
            executeFlows.run();
        }

        try {
            recordVideo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (VideoConstants.FFMPEG_INITIAL_FPS != VideoConstants.FFMPEG_FINAL_FPS) {
            Thread compress = new VideoCapture(VideoCaptureCommands.COMPRESS_VIDEO, testName);
            compress.start();

            try {
                compress.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Thread splitVideo = new VideoCapture(VideoCaptureCommands.SPLIT_VIDEO_TO_FRAMES, testName);
        splitVideo.start();

        try {
            splitVideo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread removeFrames = new VideoCapture(VideoCaptureCommands.REMOVE_FRAMES, testName);
        removeFrames.start();

        try {
            removeFrames.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.log(LoggerManagerLevel.INFO, "Video Processing done !!!", false);

        ImageAnalyzer imgAnalyzer = new ImageAnalyzer(testName, buildID);
        logger.log(LoggerManagerLevel.INFO, imgAnalyzer.getResults().toString(), true);
    }
}
