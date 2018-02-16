package org.mozilla.benchmark.constants;

import org.mozilla.benchmark.utils.PropertiesManager;

/**
 * Created by Silviu on 14/02/2018.
 */
public final class VideoConstants {

    public static final int FFMPEG_FINAL_FPS = PropertiesManager.getFfmpegFinalFps();
    public static final int FFMPEG_INITIAL_FPS = PropertiesManager.getFfmpegInitialFps();
    public static final int FFMPEG_RECORD_DURATION = PropertiesManager.getFfmpegRecordDuration();

    private VideoConstants() {
    }
}
