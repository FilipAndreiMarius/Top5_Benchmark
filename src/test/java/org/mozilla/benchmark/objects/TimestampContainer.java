package org.mozilla.benchmark.objects;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Created by silviu.checherita on 12/13/2017.
 */
public class TimestampContainer {

    private static TimestampContainer instance = null;

    private String startRunningTime;
    private Timestamp ffmpeg;
    private Timestamp maximize;

    protected TimestampContainer(){
    }

    public Timestamp getFfmpeg() {
        return ffmpeg;
    }

    public void setFfmpeg(Timestamp ffmpeg) {
        this.ffmpeg = ffmpeg;
    }

    public Timestamp getMaximize() {
        return maximize;
    }

    public void setMaximize(Timestamp maximize) {
        this.maximize = maximize;
    }

    public static TimestampContainer getInstance() {
        if(instance == null) {
            synchronized(TimestampContainer.class) {
                instance = new TimestampContainer();
            }
        }
        return instance;
    }

    public int getTimestampDifference(Timestamp one, Timestamp two){

        if (one == null || two == null){
            return 0;
        }

        long milliseconds = one.getTime() - two.getTime();
        return  (int) milliseconds / 1000;
    }

    public static String getCurrentTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH_mm_ss");
        return dateFormat.format(new Timestamp(System.currentTimeMillis()));
    }

    public String getStartRunningTime() {
        return startRunningTime;
    }

    public void setStartRunningTime(String startRunningTime) {
        this.startRunningTime = startRunningTime;
    }
}
