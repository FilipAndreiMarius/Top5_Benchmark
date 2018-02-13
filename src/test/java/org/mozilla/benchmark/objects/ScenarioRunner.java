package org.mozilla.benchmark.objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mozilla.benchmark.utils.*;
import org.mozilla.benchmark.videoProcessor.VideoCapture;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by silviu.checherita on 1/5/2018.
 */
public class ScenarioRunner extends Thread {

    private static final Logger logger = LogManager.getLogger(ScenarioRunner.class.getName());

    public ScenarioRunner(String testName) {

        TimestampContainer.getInstance().setStartRunningTime(TimeManager.getCurrentTimestamp());
        logger.info("Start creating patterns ...");

        Thread createPatterns = ThreadManager.getPageObjectThread(testName, 1, PageNavigationTypes.SAVE_PATTERN);
        if (createPatterns != null) {
            createPatterns.run();
            try {
                createPatterns.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            logger.fatal("Cannot create patterns !!!");
        }
        logger.info("Creating patterns done !!!");

        /*logger.info("Start Video Process ...");
        Thread recordVideo = new VideoCapture(Constants.Video.FFMPEG_INITIAL_FPS, Constants.Video.FFMPEG_RECORD_DURATION, VideoCaptureCommands.START_VIDEO, testName);
        recordVideo.start();

        Thread executeFlows = ThreadManager.getPageObjectThread(testName, Constants.Execution.NUMBER_OF_RUNS, PageNavigationTypes.EXECUTE_FLOW);
        if (executeFlows != null) {
            executeFlows.run();
        }

        try {
            recordVideo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread compress = new VideoCapture(VideoCaptureCommands.COMPRESS_VIDEO, testName);
        compress.start();

        try {
            compress.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
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

        logger.info("Video Processing done !!!");

        ImageAnalyzer imgAnalyzer = new ImageAnalyzer(testName);
        System.out.println(testName + " search results: " + imgAnalyzer.getResults());*/
    }
}
