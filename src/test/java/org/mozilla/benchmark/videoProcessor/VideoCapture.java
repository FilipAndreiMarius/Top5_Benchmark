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
                    int difference = TimestampContainer.getInstance().getTimestampDifference(maximize, ffmpeg);
                    FileManager.removeFiles(Constants.SPLIT_VIDEO_PATH + "\\" + this.testName, difference);
                    System.out.println("Removing unnecessary images done !!!");
                    break;

                case START_VIDEO:
                    System.out.println("Start recording video ...");
                    System.out.println("Executing FFMPEG command: " + ffmpegStartVideoCommand());
                    ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", ffmpegStartVideoCommand());

                    pb.redirectOutput(ProcessBuilder.Redirect.INHERIT).command();
                    pb.redirectError(ProcessBuilder.Redirect.INHERIT);
                    Process p1 = pb.start();
                    p1.waitFor();
                    System.out.println("Video recording done !!!");
                    break;

                case COMPRESS_VIDEO:
                    System.out.println("Start video compression ... ");
                    File inputPath = new File(VIDEOS);
                    File outputPath = new File(FPS60VIDEOS);
                    File[] VideoDirs = inputPath.listFiles();
                    System.out.println("Searching video ...");

                    for (File file : VideoDirs) {
                        if (file.getName().contains(testName)) {
                            File[] Videos = file.listFiles();
                            for (File movieFile : Videos) {
                                String convertCommand = convertTo60Fps(movieFile.getAbsolutePath(), outputPath.getAbsolutePath() + "\\" + this.testName + "\\" + movieFile.getName());
                                System.out.println("Video found. Starting compression ...");
                                System.out.println("Executing conversion command:" + convertCommand);
                                ProcessBuilder BuilderCompress = new ProcessBuilder("cmd.exe", "/c", convertCommand);
                                BuilderCompress.redirectOutput(ProcessBuilder.Redirect.INHERIT).command();
                                BuilderCompress.redirectError(ProcessBuilder.Redirect.INHERIT);
                                p = BuilderCompress.start();
                                p.waitFor();
                            }
                        } else {
                            System.out.println("Video file not found !!!");
                        }
                    }
                    System.out.println("Video compression done !!!");
                    break;

                case SPLIT_VIDEO_TO_FRAMES:
                    System.out.println("Start image split ...");
                    File output = new File(SPLITED_VIDEOS);
                    File dir = new File(FPS60VIDEOS + "\\" + testName);
                    File[] files = dir.listFiles();

                    for (File file : files) {
                        String outputFolder = FileManager.createDirectory(output + "/" + this.testName);
                        String splitCommand = splitIntoFrames(file.getAbsolutePath(), outputFolder);
                        System.out.println("Executing split command: " + splitCommand);
                        ProcessBuilder splitFrames = new ProcessBuilder("cmd.exe", "/c", splitCommand);
                        splitFrames.redirectOutput(ProcessBuilder.Redirect.INHERIT).command();
                        splitFrames.redirectError(ProcessBuilder.Redirect.INHERIT);
                        Process split = splitFrames.start();
                        split.waitFor();
                    }
                    System.out.println("Image split done !!!");
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

    private String ffmpegStartVideoCommand() {
        StringBuilder command = new StringBuilder();
        String path = FileManager.setTestPath(this.testName);
        command.append("ffmpeg -f dshow -i video=")
                .append("screen-capture-recorder")
                .append(" -vcodec libx264")
                .append(" -preset ultrafast")
                .append(" -crf 0")
                .append(" -acodec pcm_s16le")
                .append(" -r " + this.frames)
                .append(" -t " + this.duration)
                .append(" Videos\\")
                .append(path)
                .append(".mp4");
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


