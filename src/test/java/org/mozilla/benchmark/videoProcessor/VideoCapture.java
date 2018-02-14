package org.mozilla.benchmark.videoProcessor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mozilla.benchmark.objects.PageNavigationTypes;
import org.mozilla.benchmark.objects.TimestampContainer;
import org.mozilla.benchmark.objects.VideoCaptureCommands;
import org.mozilla.benchmark.utils.*;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;

public class VideoCapture extends Thread {

    private static final Logger logger = LogManager.getLogger(SystemManager.class.getName());

    private int frames;
    private int duration;
    private String location;
    private VideoCaptureCommands command;
    private String testName;

    public VideoCapture(int frames, int duration, VideoCaptureCommands command, String testName) {
        this.frames = frames;
        this.duration = duration;
        this.command = command;
        this.testName = testName;
    }

    public VideoCapture(VideoCaptureCommands command, String testName) {
        this.testName = testName;
        this.command = command;
    }

    @Override
    public void run() {
        String videoName = getTestName() + Constants.Extensions.VIDEO_EXTENSION;
        try {
            Process p;
            switch (this.command) {
                case START_VIDEO:
                    TimestampContainer.getInstance().setFfmpeg(TimeManager.getCurrentTimestamp());
                    System.out.println("FFMPEG START: " +TimestampContainer.getInstance().getFfmpeg());
                    String videoOutputPath = Constants.Paths.VIDEOS_PATH + File.separator + getTestName();
                    if (FileManager.createDirectories(videoOutputPath)) {
                        logger.info("Start recording video ...");
                        logger.info("Executing FFMPEG command: [" + ffmpegStartVideoCommand(videoOutputPath, videoName) + "]");
                        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", ffmpegStartVideoCommand(videoOutputPath, videoName));
                        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT).command();
                        pb.redirectError(ProcessBuilder.Redirect.INHERIT);
                        Process p1 = pb.start();
                        p1.waitFor();
                        logger.info("Video recording done !!!");
                    }
                    break;

                case COMPRESS_VIDEO:
                    String video60FpsOutputPath = Constants.Paths.FPS_60_VIDEO_PATH + File.separator + getTestName();
                    if (FileManager.createDirectories(video60FpsOutputPath)) {
                        logger.info("Start video compression ... ");
                        String inputPath = Constants.Paths.VIDEOS_PATH + File.separator + getTestName() + File.separator + videoName;
                        String outputPath = video60FpsOutputPath + File.separator + videoName;
                        String convertCommand = convertTo60Fps(inputPath, outputPath);
                        logger.info("Executing conversion command: [" + convertCommand + "]");
                        ProcessBuilder BuilderCompress = new ProcessBuilder("cmd.exe", "/c", convertCommand);
                        BuilderCompress.redirectOutput(ProcessBuilder.Redirect.INHERIT).command();
                        BuilderCompress.redirectError(ProcessBuilder.Redirect.INHERIT);
                        p = BuilderCompress.start();
                        p.waitFor();
                        logger.info("Video compression done !!!");
                    }
                    break;

                case SPLIT_VIDEO_TO_FRAMES:
                    logger.info("Start image split ...");
                    String input = Constants.Paths.FPS_60_VIDEO_PATH + File.separator + getTestName() + File.separator + videoName;
                    String output = Constants.Paths.SPLIT_VIDEO_PATH + File.separator + getTestName();
                    if (FileManager.createDirectories(output)) {
                        String splitCommand = splitIntoFrames(input, output);
                        logger.info("Executing split command: [" + splitCommand + "]");
                        ProcessBuilder splitFrames = new ProcessBuilder("cmd.exe", "/c", splitCommand);
                        splitFrames.redirectOutput(ProcessBuilder.Redirect.INHERIT).command();
                        splitFrames.redirectError(ProcessBuilder.Redirect.INHERIT);
                        Process split = splitFrames.start();
                        split.waitFor();
                        logger.info("Image split done !!!");
                    }
                    break;

                case REMOVE_FRAMES:
                    logger.info("Start removing unnecessary images  ...");
                    Timestamp ffmpeg = TimestampContainer.getInstance().getFfmpeg();
                    Timestamp maximize = TimestampContainer.getInstance().getMaximize();
                    int secondsToRemove = TimeManager.getTimestampDifference(maximize, ffmpeg) - 1;
                    System.out.println("DIFFERENCE: " + secondsToRemove);
                    FileManager.removeFiles(Constants.Paths.SPLIT_VIDEO_PATH + File.separator + getTestName(), secondsToRemove);
                    logger.info("Removing unnecessary images done !!!");
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String ffmpegStartVideoCommand(String path, String videoName) {
        StringBuilder command = new StringBuilder();
        command.append("ffmpeg -f dshow -rtbufsize 2G -i video=screen-capture-recorder -vcodec libx264 -preset ultrafast -crf 0")
                .append(" -r ").append(getFrames())
                .append(" -t ").append(getDuration())
                .append(" ")
                .append(path)
                .append(File.separator)
                .append(videoName);
        return command.toString();
    }

    private String convertTo60Fps(String fileInput, String fileOutput) {
        StringBuilder command = new StringBuilder();
        command.append("ffmpeg  -i ").append(fileInput)
                .append(" -vcodec h264 -an -vf fps=60 ")
                .append(fileOutput);
        return command.toString();
    }

    private String splitIntoFrames(String fileInput, String fileOutput) {
        StringBuilder command = new StringBuilder();
        command.append("ffmpeg  -i ").append(fileInput)
                .append(" -qscale -1 ")
                .append(fileOutput)
                .append(File.separator)
                .append("image.%6d.png");
        return command.toString();
    }

    public int getFrames() {
        return this.frames;
    }

    public void setFrames(int frames) {
        this.frames = frames;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public VideoCaptureCommands getCommand() {
        return command;
    }

    public void setCommand(VideoCaptureCommands command) {
        this.command = command;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

}


