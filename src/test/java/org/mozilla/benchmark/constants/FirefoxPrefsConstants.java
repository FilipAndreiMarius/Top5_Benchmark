package org.mozilla.benchmark.constants;

import org.mozilla.benchmark.utils.PropertiesManager;

/**
 * Created by Silviu on 14/02/2018.
 */
public final class FirefoxPrefsConstants {

    public static final String STARTUP_HOMEPAGE_PREFERENCE = "browser.startup.homepage";
    public static final String GFX_WEBRENDER_ENABLED_PREFERENCE = "gfx.webrender.enabled";
    public static final String GFX_WEBRENDER_BLOB_IMAGES_PREFERENCE = "gfx.webrender.blob-images";
    public static final Boolean GFX_WEBRENDER_ENABLED = PropertiesManager.getGfxWebrenderEnabled();
    public static final Boolean GFX_WEBRENDER_BLOB_IMAGES = PropertiesManager.getGfxWebrenderBlobImages();

    private FirefoxPrefsConstants() {
    }
}
