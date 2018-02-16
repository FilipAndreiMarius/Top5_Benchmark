package org.mozilla.benchmark.constants;

import org.mozilla.benchmark.utils.PropertiesManager;

/**
 * Created by Silviu on 14/02/2018.
 */
public final class FileExtensionsConstants {

    public static final String VIDEO_EXTENSION = "." + PropertiesManager.getVideoExtension();
    public static final String IMAGE_EXTENSION = PropertiesManager.getImageExtension();
    public static final String JSON_EXTENSION = ".json";

    private FileExtensionsConstants() {
    }
}
