package org.mozilla.benchmark.videoProcessor;

import org.mozilla.benchmark.constants.FileExtensionsConstants;
import org.mozilla.benchmark.constants.PathConstants;
import org.mozilla.benchmark.constants.VideoConstants;
import org.mozilla.benchmark.objects.LoggerManagerLevel;
import org.mozilla.benchmark.objects.TimestampContainer;
import org.mozilla.benchmark.objects.VideoCaptureCommands;
import org.mozilla.benchmark.utils.*;

import java.io.File;
import java.sql.Timestamp;

public class VideoCapture extends Thread {

    private static final LoggerManager logger = new LoggerManager(VideoCapture.class.getName());

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
        String videoName = getTestName() + FileExtensionsConstants.VIDEO_EXTENSION;
        try {
            Process p;
            switch (this.command) {
                case START_VIDEO:
                    TimestampContainer.getInstance().setFfmpeg(TimeManager.getCurrentTimestamp());
                    String videoOutputPath = PathConstants.VIDEOS_PATH + File.separator + getTestName();
                    if (FileManager.createDirectories(videoOutputPath)) {
                        logger.log(LoggerManagerLevel.INFO, "Start recording video ...", false);
                        logger.log(LoggerManagerLevel.INFO, String.format("Executing FFMPEG command: [%s]", ffmpegStartVideoCommand(videoOutputPath, videoName)), false);
                        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", ffmpegStartVideoCommand(videoOutputPath, videoName));
                        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT).command();
                        pb.redirectError(ProcessBuilder.Redirect.INHERIT);
                        Process p1 = pb.start();
                        p1.waitFor();
                        logger.log(LoggerManagerLevel.INFO, "Video recording done !!!", false);
                    }
                    break;

                case COMPRESS_VIDEO:
                    String video60FpsOutputPath = PathConstants.FPS_60_VIDEO_PATH + File.separator + getTestName();
                    if (FileManager.createDirectories(video60FpsOutputPath)) {
                        logger.log(LoggerManagerLevel.INFO, "Start video compression ... ", false);
                        String inputPath = PathConstants.VIDEOS_PATH + File.separator + getTestName() + File.separator + videoName;
                        String outputPath = video60FpsOutputPath + File.separator + videoName;
                        String convertCommand = convertTo60Fps(inputPath, outputPath);
                        logger.log(LoggerManagerLevel.INFO, String.format("Executing conversion command: [%s]", convertCommand), false);
                        ProcessBuilder BuilderCompress = new ProcessBuilder("cmd.exe", "/c", convertCommand);
                        BuilderCompress.redirectOutput(ProcessBuilder.Redirect.INHERIT).command();
                        BuilderCompress.redirectError(ProcessBuilder.Redirect.INHERIT);
                        p = BuilderCompress.start();
                        p.waitFor();
                        logger.log(LoggerManagerLevel.INFO, "Video compression done !!!", false);
                    }
                    break;

                case SPLIT_VIDEO_TO_FRAMES:
                    logger.log(LoggerManagerLevel.INFO, "Start image split ...", false);
                    Boolean isCompressionNeeded = VideoConstants.FFMPEG_FINAL_FPS != VideoConstants.FFMPEG_INITIAL_FPS;
                    String input = (isCompressionNeeded ? PathConstants.FPS_60_VIDEO_PATH : PathConstants.VIDEOS_PATH)
                            + File.separator + getTestName() + File.separator + videoName;
                    String output = PathConstants.SPLIT_VIDEO_PATH + File.separator + getTestName();
                    if (FileManager.createDirectories(output)) {
                        String splitCommand = splitIntoFrames(input, output);
                        logger.log(LoggerManagerLevel.INFO, String.format("Executing split command: [%s]", splitCommand), false);
                        ProcessBuilder splitFrames = new ProcessBuilder("cmd.exe", "/c", splitCommand);
                        splitFrames.redirectOutput(ProcessBuilder.Redirect.INHERIT).command();
                        splitFrames.redirectError(ProcessBuilder.Redirect.INHERIT);
                        Process split = splitFrames.start();
                        split.waitFor();
                        logger.log(LoggerManagerLevel.INFO, "Image split done !!!", false);
                    }
                    break;

                case REMOVE_FRAMES:
                    logger.log(LoggerManagerLevel.INFO, "Start removing unnecessary images  ...", false);
                    Timestamp ffmpeg = TimestampContainer.getInstance().getFfmpeg();
                    Timestamp maximize = TimestampContainer.getInstance().getMaximize();
                    int secondsToRemove = TimeManager.getTimestampDifference(maximize, ffmpeg) - 1;
                    FileManager.removeFiles(PathConstants.SPLIT_VIDEO_PATH + File.separator + getTestName(), secondsToRemove);
                    logger.log(LoggerManagerLevel.INFO, "Removing unnecessary images done !!!", false);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String ffmpegStartVideoCommand(String path, String videoName) {
        StringBuilder command = new StringBuilder();
        command.append("ffmpeg -f dshow -rtbufsize 2G -i video=screen-capture-recorder -vcodec h264 -preset ultrafast -crf 0")
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


