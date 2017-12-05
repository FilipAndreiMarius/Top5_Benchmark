package Utils;


import org.sikuli.script.Finder;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by andrei.filip on 10/4/2017.
 */
public class Utils {


    public static ArrayList<Object> getImages(String path) throws IOException {

        ArrayList<Object> images = new ArrayList<>();
        Iterator it = org.apache.commons.io.FileUtils.iterateFiles(new File(path), null, false);
        while (it.hasNext()) {
            Object o = it.next();
            //Add Pattern to an array of patterns
            images.add(o);
        }
        return images;

    }


    public static Boolean searchImage(String imagePath1, String imagePath2,float similarity) throws IOException {
        Match m;
        ArrayList<Match> list = null;
        Finder finder = new Finder(imagePath1, new Region(286, 164, 108, 23));

        Pattern pattern = new Pattern(imagePath2).similar(similarity);

        finder.find(pattern);

        if (finder.hasNext()) {
            return true;
        } else {
            return false;
        }

    }


/*
    public static void run(String inFile, String templateFile) {
        System.out.println("\nRunning Template Matching");
        //System.load( "C:\\OpenCvs\\opencv\\build\\java\\x64\\opencv_java330.dll" );
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        int match_method=Imgproc.TM_SQDIFF;

        Mat img = Imgcodecs.imread(inFile);
        Mat templ = Imgcodecs.imread(templateFile);

        // / Create the result matrix
        int result_cols = img.cols() - templ.cols() + 1;
        int result_rows = img.rows() - templ.rows() + 1;
        Mat result = new Mat(result_rows, result_cols, CvType.CV_32FC1);

        // / Do the Matching and Normalize
        Imgproc.matchTemplate(img, templ, result, match_method);
        Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1, new Mat());

        // / Localizing the best match with minMaxLoc
        Core.MinMaxLocResult mmr = Core.minMaxLoc(result);
        Double maxVal=mmr.maxVal;


        System.out.print(maxVal+"object:");



        Point matchLoc;
        if (match_method == Imgproc.TM_SQDIFF || match_method == Imgproc.TM_SQDIFF_NORMED) {
            matchLoc = mmr.minLoc;
        } else {
            matchLoc = mmr.maxLoc;
        }
        // / Show me what you got
        Imgproc.rectangle(img, matchLoc, new Point(matchLoc.x + templ.cols(),
                matchLoc.y + templ.rows()), new Scalar(0, 255, 0));
        //906:1005
        Double x=mmr.maxLoc.x;
        System.out.print("Max val="+mmr.maxVal+"\n"+"Min val="+mmr.minVal+"\n"+"MinLoc="+mmr.minLoc+"\n"+"MaxLoc="+mmr.maxLoc+"\n");

        // Save the visualized detection.
        System.out.println("Writing " + "C:\\test");

        Imgcodecs.imwrite("match1.png", img);

    }*/






    public static void runCmd(String command,Boolean runTests) throws IOException, InterruptedException {


        ProcessBuilder builder=new ProcessBuilder("cmd.exe","/c",command);
        builder.redirectErrorStream(true);
        Process p=builder.start();


        BufferedReader r=new BufferedReader(new InputStreamReader(p.getInputStream()));
        OutputStream out=p.getOutputStream();
        out.write("q\n".getBytes());
        out.flush();
        Thread.sleep(5000);

        String line;
        while(true){
            line=r.readLine();
            if(line==null){
                break;
            }
            System.out.print("\n"+line);

        }

    }







    public static String createDirectory(String directoryName){

        File file=new File(directoryName);
        String PATH = null;

        if(!file.exists()) {
            System.out.println("Creating directory:" + file.getName());
            Boolean result = false;

            try {
                file.mkdir();
                result=true;
                PATH=file.getAbsolutePath();

            }
            catch (SecurityException se){
            se.getLocalizedMessage();
            }
            if(result){
                System.out.print("DIR created");

                return PATH;
            }

        }
        else{
            System.out.println("A directory with this name already exists:");
        }
        return PATH;
    }


    public static Boolean checkCpuLoad() throws IOException, InterruptedException {
        Boolean run=false;
        double cpuLoad = 0;
        SystemInfo info=new SystemInfo();

        while(run!=true){
            cpuLoad =info.getCpuLoad();
            if(cpuLoad>20.0) {
                System.out.print("Cpu Usage is too high for the moment: " + cpuLoad + "\n");
                Thread.sleep(2000);
            }
             if(cpuLoad<=20.0){
                 System.out.print("Cpu is under 20% usage: "+cpuLoad+"\n");
                 run=true;
             }

        }
         return run;

    }



}