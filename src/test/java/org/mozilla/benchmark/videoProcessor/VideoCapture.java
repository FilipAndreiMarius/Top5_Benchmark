package org.mozilla.benchmark.videoProcessor;

import org.mozilla.benchmark.utils.FileManager;

import java.io.File;

public class VideoCapture extends Thread {
    private static final String START_VIDEO = "runVideo";
    private static final String COMPRESS_VIDEO = "compressVideo";
    private static final String SPLIT_VIDEO_TO_FRAMES = "splitVideo";
    private static final String VIDEOS = "Videos";
    private static final String FPS60VIDEOS = "60FpsVideos";
    private static final String SPLITED_VIDEOS = "SplitedVideos";

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

    @Override
    public void run() {

        try {
            Process p;
            switch (this.command) {
                case START_VIDEO:
                    System.out.println("Video-Record-Thread-Started" + Thread.currentThread().getName());
                    ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", ffmpegStartVideoCommand());
                    pb.redirectOutput(ProcessBuilder.Redirect.INHERIT).command();
                    pb.redirectError(ProcessBuilder.Redirect.INHERIT);
                    Process p1 = pb.start();
                    p1.waitFor();
                    System.out.println("Video-Record-Thread-Ended"
                            + Thread.currentThread().getName());
                    break;

                case COMPRESS_VIDEO:
                    File inputPath = new File(VIDEOS);
                    File outputPath = new File(FPS60VIDEOS);
                    File[] VideoDirs = inputPath.listFiles();

                    for (File file : VideoDirs) {
                        if (file.getName().contains(testName)) {
                            File[] Videos = file.listFiles();
                            for (File movieFile : Videos) {
                                System.out.println("Video_file_found-Compressing-Started" + Thread.currentThread().getName());
                                ProcessBuilder BuilderCompress = new ProcessBuilder("cmd.exe", "/c", convertTo60Fps(movieFile.getAbsolutePath(), outputPath.getAbsolutePath() + "\\" + file.getName() + "\\" + movieFile.getName()));
                                BuilderCompress.redirectOutput(ProcessBuilder.Redirect.INHERIT).command();
                                BuilderCompress.redirectError(ProcessBuilder.Redirect.INHERIT);
                                p = BuilderCompress.start();
                                p.waitFor();
                                System.out.println("Video-Compressing-Ended" + Thread.currentThread().getName());
                            }
                        } else {
                            System.out.println("Video file not found!...Keep Searching..:)");
                        }
                    }
                    break;

                case SPLIT_VIDEO_TO_FRAMES:
                    File output = new File(SPLITED_VIDEOS);
                    File dir = new File(FPS60VIDEOS + "\\" + testName);
                    File[] files = dir.listFiles();

                    for (File file : files) {

                        System.out.println("Video-Splitting-Started" + Thread.currentThread().getName());
                        String outputFolder = FileManager.createDirectory(output + "/" + file.getName());
                        ProcessBuilder splitFrames = new ProcessBuilder("cmd.exe", "/c", splitIntoFrames(file.getAbsolutePath(), outputFolder));
                        splitFrames.redirectOutput(ProcessBuilder.Redirect.INHERIT).command();
                        splitFrames.redirectError(ProcessBuilder.Redirect.INHERIT);
                        Process split = splitFrames.start();
                        split.waitFor();
                        System.out.println("Video-Splitting-Ended" + Thread.currentThread().getName());
                    }
                    break;
            }

        } catch (Exception e) {
            System.out.print(e);

        } finally {
            System.out.println("Video-Processing-DONE!!!!!" + Thread.currentThread().getName());
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


