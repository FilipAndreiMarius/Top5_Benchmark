package VideoProcessor;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class VideoCapture extends Thread {
    public static final String StartVideo = "runVideo";
    public static final String Compress = "compress";
    public static final String SplitVideoToFrames = "splitVideo";
    public static final String VIDEOS = "Videos";
    public static final String FPS60VIDEOS = "60FpsVideos";
    public static final String SPLITED_VIDEOS = "SplitedVideos";


    static String frames;
    static String duration;
    static String location;
    static String command;
    static String testName;


    public VideoCapture(String frames, String duration, String command, String testName) throws IOException {
        this.command = command;
        this.frames = frames;
        this.duration = duration;
        this.testName = testName;
        setFrames(frames);
        setDuration(duration);
    }

    public VideoCapture(String command, String testName) throws IOException {
        this.testName = testName;
        this.command = command;

    }

    @Override
    public void run() {

        try {
            Process p;
            switch (command) {
                case StartVideo:
                    System.out.println("Video-Record-Thread-Started"
                            + Thread.currentThread().getName());
                    ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", ffmpegStartVideoCommand());
                    pb.redirectOutput(ProcessBuilder.Redirect.INHERIT).command();
                    pb.redirectError(ProcessBuilder.Redirect.INHERIT);
                    Process p1 = pb.start();
                    p1.waitFor();
                    System.out.println("Video-Record-Thread-Started"
                            + Thread.currentThread().getName());
                    break;


                case Compress:
                    File inputPath = new File(VIDEOS);
                    File outputPath = new File(FPS60VIDEOS);
                    File[] VideoDirs = inputPath.listFiles();
                    for (File file : VideoDirs) {
                        if (file.getName().contains(testName)) {
                            File[] Videos = file.listFiles();
                            for (File movieFile : Videos) {
                                System.out.println("Video-Compressing-Started"
                                        + Thread.currentThread().getName());
                                ProcessBuilder BuilderCompress = new ProcessBuilder("cmd.exe", "/c", convertTo60Fps(movieFile.getAbsolutePath(), outputPath.getAbsolutePath() + "\\" + file.getName() + "\\" + movieFile.getName()));
                                BuilderCompress.redirectOutput(ProcessBuilder.Redirect.INHERIT).command();
                                BuilderCompress.redirectError(ProcessBuilder.Redirect.INHERIT);
                                p = BuilderCompress.start();
                                p.waitFor();
                                System.out.println("Video-Compressing-Started"
                                        + Thread.currentThread().getName());
                            }
                        } else {
                            System.out.println("Keep Searching..:)");
                        }

                    }
                    break;


                case SplitVideoToFrames:
                    File output = new File(SPLITED_VIDEOS);
                    File dir = new File(FPS60VIDEOS + "\\" + testName);
                    File[] files = dir.listFiles();

                    for (File file : files) {

                        System.out.println("Video-Splitting-Started"
                                + Thread.currentThread().getName());
                        String outputFolder = Utils.Utils.createDirectory(output + "/" + file.getName());
                        ProcessBuilder splitFrames = new ProcessBuilder("cmd.exe", "/c", splitIntoFrames(file.getAbsolutePath(), outputFolder));
                        splitFrames.redirectOutput(ProcessBuilder.Redirect.INHERIT).command();
                        splitFrames.redirectError(ProcessBuilder.Redirect.INHERIT);
                        Process split = splitFrames.start();
                        split.waitFor();
                        System.out.println("Video-Splitting-Ended"
                                + Thread.currentThread().getName());

                    }
                    break;

            }


        } catch (Exception e) {
            System.out.print(e);

        } finally {
            System.out.println("Video-Thread-Ended"
                    + Thread.currentThread().getName());
        }

    }

    //generates a random Number for the VideoName
    public static int generateNumber(int min, int max) {
        Random random = new Random();
        int randomNum = random.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    //FFMPEG Video command that will be inputed in terminal
    public String ffmpegStartVideoCommand() {
        StringBuilder command = new StringBuilder();
        String path = setTestPath(testName);
        command.append("ffmpeg -f dshow -i video=")
                .append("screen-capture-recorder")
                .append(" -r " + getFrames())
                .append(" -t " + getDuration())
                .append(" Videos\\")
                .append(path)
                .append(generateNumber(1, 1000))
                .append(".mp4");
        return command.toString();
    }

    //contructs a test path based on a test name
    public static String setTestPath(String testNm) {
        String testPath = null;
        switch (testNm) {
            case "Gsearch":
                testPath = "Gsearch" + "\\" + testNm;
                break;
            case "Gmail":
                testPath = "Gmail" + "\\" + testNm;
                break;
            case "Amazon":
                testPath = "Amazon" + "\\" + testNm;
                break;
            case "Facebook":
                testPath = "Facebook" + "\\" + testNm;
                break;
            case "Youtube":
                testPath = "Youtube" + "\\" + testNm;
                break;
        }
        return testPath;
    }


    public String convertTo60Fps(String fileInput, String fileOutput) {
        StringBuilder command = new StringBuilder();
        command.append("ffmpeg  -i " + fileInput)
                .append(" -vcodec h264 -an -vf fps=60 ")
                .append(fileOutput);
        return command.toString();
    }

    public String splitIntoFrames(String fileInput, String fileOutput) {
        StringBuilder command = new StringBuilder();
        command.append("ffmpeg  -i " + fileInput)
                .append(" -qscale -1 ")
                .append(fileOutput)
                .append("\\image.%6d.jpg");
        return command.toString();
    }

    public static String getFrames() {
        return frames;
    }

    public static void setFrames(String frames) {
        VideoCapture.frames = frames;
    }

    public static String getDuration() {
        return duration;
    }

    public static void setDuration(String duration) {
        VideoCapture.duration = duration;
    }

    public static String getLocation() {
        return location;
    }

    public static void setLocation(String location) {
        VideoCapture.location = location;
    }


}


