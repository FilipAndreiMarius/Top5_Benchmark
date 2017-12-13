package org.mozilla.benchmark.videoProcessor;

import org.mozilla.benchmark.objects.TimestampContainer;
import org.mozilla.benchmark.utils.Constants;
import org.mozilla.benchmark.utils.FileManager;

import java.io.File;
import java.sql.Timestamp;

public class VideoCapture extends Thread {
    private static final String START_VIDEO = "runVideo";
    private static final String COMPRESS_VIDEO = "compressVideo";
    private static final String SPLIT_VIDEO_TO_FRAMES = "splitVideo";
    private static final String VIDEOS = "Videos";
    private static final String FPS60VIDEOS = "60FpsVideos";
    private static final String SPLITED_VIDEOS = "SplitedVideos";
    private static final String REMOVE_FRAMES = "removeFrames";


    private String frames;
    private String duration;
    private String location;
    private String command;
    private String testName;

    public VideoCapture(String frames, String duration, String command, String testName) {
        this.frames = frames;
        this.duration = duration;
        this.command = command;
        this.testName = testName;
    }

    public VideoCapture(String command, String testName) {
        this.testName = testName;
        this.command = command;
    }

    @Override
    public void run() {

        String videoName = this.testName + ".mp4";

        try {
            System.out.println("================================================");
            System.out.println("Start Video Process ...");
            System.out.println("================================================");
            Process p;

            switch (this.command) {

                case REMOVE_FRAMES:
                    System.out.println("Start removing unnecessary images  ...");
                    Timestamp ffmpeg = TimestampContainer.getInstance().getFfmpeg();
                    Timestamp maximize = TimestampContainer.getInstance().getMaximize();
                    int secondsToRemove = TimestampContainer.getInstance().getTimestampDifference(maximize, ffmpeg);
                    FileManager.removeFiles(Constants.Paths.SPLIT_VIDEO_PATH + "\\" + getTestName(), secondsToRemove);
                    System.out.println("Removing unnecessary images done !!!");
                    break;

                case START_VIDEO:
                    String videoOutputPath = Constants.Paths.VIDEOS_PATH + "\\" + getTestName();
                    if (FileManager.createDirectories(videoOutputPath)) {
                        System.out.println("Start recording video ...");
                        System.out.println("Executing FFMPEG command: " + ffmpegStartVideoCommand(videoOutputPath, videoName));
                        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", ffmpegStartVideoCommand(videoOutputPath, videoName));
                        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT).command();
                        pb.redirectError(ProcessBuilder.Redirect.INHERIT);
                        Process p1 = pb.start();
                        p1.waitFor();
                        System.out.println("Video recording done !!!");
                    }
                    break;

                case COMPRESS_VIDEO:
                    String video60FpsOutputPath = Constants.Paths.FPS_60_VIDEO_PATH + "\\" + getTestName();
                    if (FileManager.createDirectories(video60FpsOutputPath)) {
                        System.out.println("Start video compression ... ");
                        String inputPath = Constants.Paths.VIDEOS_PATH + "\\" + getTestName() + "\\" + videoName;
                        String outputPath = video60FpsOutputPath + "\\" + videoName;
                        String convertCommand = convertTo60Fps(inputPath, outputPath);
                        System.out.println("Executing conversion command:" + convertCommand);
                        ProcessBuilder BuilderCompress = new ProcessBuilder("cmd.exe", "/c", convertCommand);
                        BuilderCompress.redirectOutput(ProcessBuilder.Redirect.INHERIT).command();
                        BuilderCompress.redirectError(ProcessBuilder.Redirect.INHERIT);
                        p = BuilderCompress.start();
                        p.waitFor();
                        System.out.println("Video compression done !!!");
                    }
                    break;

                case SPLIT_VIDEO_TO_FRAMES:
                    System.out.println("Start image split ...");
                    String input = Constants.Paths.FPS_60_VIDEO_PATH + "\\" + getTestName() + "\\" + videoName;
                    String output = Constants.Paths.SPLIT_VIDEO_PATH + "\\" + getTestName();
                    if (FileManager.createDirectories(output)) {
                        String splitCommand = splitIntoFrames(input, output);
                        System.out.println("Executing split command: " + splitCommand);
                        ProcessBuilder splitFrames = new ProcessBuilder("cmd.exe", "/c", splitCommand);
                        splitFrames.redirectOutput(ProcessBuilder.Redirect.INHERIT).command();
                        splitFrames.redirectError(ProcessBuilder.Redirect.INHERIT);
                        Process split = splitFrames.start();
                        split.waitFor();
                        System.out.println("Image split done !!!");
                    }
                    break;
            }

        } catch (Exception e) {
            System.out.print(e);

        } finally {
            System.out.println("================================================");
            System.out.println("Video Processing done !!!");
            System.out.println("================================================");
        }
    }

    private String ffmpegStartVideoCommand(String path, String videoName) {
        StringBuilder command = new StringBuilder();
        command.append("ffmpeg -f dshow -i video=")
                .append("screen-capture-recorder")
                .append(" -vcodec libx264")
                .append(" -preset ultrafast")
                .append(" -crf 0")
                .append(" -acodec pcm_s16le")
                .append(" -r " + this.frames)
                .append(" -t " + this.duration)
                .append(" ")
                .append(path)
                .append("\\")
                .append(videoName);
        return command.toString();
    }

    private String convertTo60Fps(String fileInput, String fileOutput) {
        StringBuilder command = new StringBuilder();
        command.append("ffmpeg  -i " + fileInput)
                .append(" -vcodec h264 -an -vf fps=60 ")
                .append(fileOutput);
        return command.toString();
    }

    private String splitIntoFrames(String fileInput, String fileOutput) {
        StringBuilder command = new StringBuilder();
        command.append("ffmpeg  -i " + fileInput)
                .append(" -qscale -1 ")
                .append(fileOutput)
                .append("\\image.%6d.png");
        return command.toString();
    }

    public String getFrames() {
        return this.frames;
    }

    public void setFrames(String frames) {
        this.frames = frames;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

}


