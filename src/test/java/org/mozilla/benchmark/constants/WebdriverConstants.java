package org.mozilla.benchmark.constants;

import java.io.File;

/**
 * Created by Silviu on 14/02/2018.
 */
public final class WebdriverConstants {

    public static final String WEBDRIVER_PROPERTY = "webdriver.gecko.driver";
    public static final String WEBDRIVER_PATH = PathConstants.PROJECT_LOCATION + File.separator + "libs" + File.separator + "geckodriver.exe";

    private WebdriverConstants() {
    }
}
