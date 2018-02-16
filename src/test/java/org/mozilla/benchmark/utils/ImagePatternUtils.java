package org.mozilla.benchmark.utils;

import org.mozilla.benchmark.objects.ImagePattern;

/**
 * Created by silviu.checherita on 1/31/2018.
 */
public class ImagePatternUtils {

    private static ImagePattern instance = null;

    public static ImagePattern getInstance() {
        if (instance == null) {
            synchronized (ImagePatternUtils.class) {
                instance = new ImagePattern();
            }
        }
        return instance;
    }

    public static void closeInstance() {
        instance = null;
    }
}
