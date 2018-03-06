package org.mozilla.benchmark.constants;

import org.mozilla.benchmark.utils.PropertiesManager;

/**
 * Created by Silviu on 14/02/2018.
 */
public final class FirefoxPrefsConstants {

    public static final String STARTUP_HOMEPAGE_PREFERENCE = "browser.startup.homepage";
    public static final String GFX_WEBRENDER_ALL_PREFERENCE = "gfx.webrender.all";
    public static final Boolean GFX_WEBRENDER_ALL = PropertiesManager.getGfxWebrenderAll();

    private FirefoxPrefsConstants() {
    }
}
