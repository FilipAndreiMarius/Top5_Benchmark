package org.mozilla.benchmark.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Created by Silviu on 13/12/2017.
 */
public class TimeManager {
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH_mm_ss";

    public static int getTimestampDifference(Timestamp one, Timestamp two) {

        if (one == null || two == null) {
            return 0;
        }

        long milliseconds = one.getTime() - two.getTime();
        return (int) milliseconds / 1000;
    }

    public static String getFormattedTimestamp(Timestamp t) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        return dateFormat.format(t);
    }

    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}
