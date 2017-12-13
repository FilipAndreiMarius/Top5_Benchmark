package org.mozilla.benchmark.objects;
/**
 * Created by andrei.filip on 10/4/2017.
 */

import org.mozilla.benchmark.utils.Constants;
import org.mozilla.benchmark.utils.Scenarios;

public class Google extends ImageAnalyzer {

    public Google() {
        super(Constants.GOOGLE_PATTERN_CATEGORIES, Scenarios.GOOGLE.getName());
    }

}
