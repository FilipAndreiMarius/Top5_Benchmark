package org.mozilla.benchmark;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mozilla.benchmark.objects.TimestampContainer;
import org.mozilla.benchmark.pageObjects.GooglePage;
import org.mozilla.benchmark.utils.Constants;
import org.mozilla.benchmark.utils.Scenarios;
import org.mozilla.benchmark.utils.TimeManager;
import org.mozilla.benchmark.videoProcessor.VideoCapture;

import java.io.IOException;

/**
 * Created by silviu.checherita on 12/13/2017.
 */
public class Runner {

    private static final Logger logger = LogManager.getLogger(Runner.class.getName());

    public static void main(String args[]) throws IOException {

        TimestampContainer.getInstance().setStartRunningTime(TimeManager.getCurrentTimestamp());
        TimestampContainer.getInstance().setFfmpeg(TimeManager.getCurrentTimestamp());

        logger.info("Start Video Process ...");

        Thread recordVideo = new VideoCapture("30", "50", "runVideo", Scenarios.GOOGLE.getName());
        recordVideo.start();

        Thread a = new GooglePage(Constants.General.NUMBER_OF_RUNS);
        a.start();

        try {
            recordVideo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread compress = new VideoCapture("compressVideo", Scenarios.GOOGLE.getName());
        compress.start();

        try {
            compress.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Thread splitVideo = new VideoCapture("splitVideo", Scenarios.GOOGLE.getName());
        splitVideo.start();

        try {
            splitVideo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread removeFrames = new VideoCapture("removeFrames", Scenarios.GOOGLE.getName());
        removeFrames.start();

        try {
            removeFrames.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.info("Video Processing done !!!");

/*        Google g = new Google();
        System.out.println("Google search results: " + g.getResults());*/
    }
}
