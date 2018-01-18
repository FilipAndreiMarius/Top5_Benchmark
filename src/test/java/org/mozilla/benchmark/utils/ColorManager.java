package org.mozilla.benchmark.utils;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by silviu.checherita on 1/18/2018.
 */
public class ColorManager {

    private static final String HEX_PATTERN = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";
    private static final String RGB_PATTERN = "rgb *\\( *([0-9]+), *([0-9]+), *([0-9]+) *\\)";

    public static Color getColorFromString(String input) {

        Pattern rgbPattern = Pattern.compile(RGB_PATTERN);
        Matcher rgbMatcher = rgbPattern.matcher(input);

        if (rgbMatcher.matches()) {
            return new Color(Integer.valueOf(rgbMatcher.group(1)),
                    Integer.valueOf(rgbMatcher.group(2)),
                    Integer.valueOf(rgbMatcher.group(3)));
        } else {
            Pattern hexPattern = Pattern.compile(HEX_PATTERN);
            Matcher hexMatcher = hexPattern.matcher(input);

            if (hexMatcher.matches()) {
                return Color.decode(input);
            }
        }

        return null;
    }
}
