package org.mozilla.benchmark.objects;

import java.sql.Timestamp;

/**
 * Created by silviu.checherita on 12/13/2017.
 */
public class TimestampContainer {

    private static TimestampContainer instance = null;

    private Timestamp startRunningTime;
    private Timestamp ffmpeg;
    private Timestamp maximize;

    private TimestampContainer() {
    }

    public static TimestampContainer getInstance() {
        if (instance == null) {
            synchronized (TimestampContainer.class) {
                instance = new TimestampContainer();
            }
        }
        return instance;
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

    public Timestamp getStartRunningTime() {
        return startRunningTime;
    }

    public void setStartRunningTime(Timestamp startRunningTime) {
        this.startRunningTime = startRunningTime;
    }
}
