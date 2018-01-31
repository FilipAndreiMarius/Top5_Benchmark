package org.mozilla.benchmark.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mozilla.benchmark.objects.ImagePattern;

/**
 * Created by silviu.checherita on 1/31/2018.
 */
public class ImagePatternUtils {

    private static final Logger logger = LogManager.getLogger(ImagePatternUtils.class.getName());
    private static ImagePattern instance = null;

    public static ImagePattern getInstance() {
        if (instance == null) {
            synchronized (ImagePatternUtils.class) {
                logger.info("Initializing Image Pattern Object ...");
                instance = new ImagePattern();
            }
        }
        logger.info("Image Pattern Object initialized !");
        return instance;
    }
}
