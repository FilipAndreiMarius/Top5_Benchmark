package org.mozilla.benchmark.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by andrei.filip on 11/22/2017.
 */


public class ScenarioManager {

    private static final Logger logger = LogManager.getLogger(ScenarioManager.class.getName());

    public static String getScenarioName(String jsonName) {
        return jsonName.replace(Constants.Extensions.JSON_EXTENSION, "");
    }

    public static String getClassNameFromTestName(String testName) {
        return testName.substring(0, 1).toUpperCase() + testName.substring(1) + "Page";
    }

    public static ArrayList<String> validateScenarios(String[] scenarios, String directoryName) {
        ArrayList<String> scenarioList = new ArrayList<>();
        logger.info("Validating scenarios ...");
        for (String scenario : scenarios) {
            if (FileManager.fileFound(scenario, directoryName)) {
                logger.debug("[" + scenario + "] found!");
                scenarioList.add(scenario);
            } else {
                logger.warn("[" + scenario + "] NOT found in " + Constants.Paths.RESOURCES_PATH + " folder !!! Skipping scenario ...");
            }
        }
        return scenarioList;
    }

    public static String getPatternPathForTest(String testName) {
        StringBuffer patternPath = new StringBuffer().append(Constants.Paths.PATTERNS_PATH)
                .append(File.separator)
                .append(testName)
                .append(File.separator);
        return patternPath.toString();
    }

    public static String getPatternName(String imageElementName, String testName) {
        String patternPath = getPatternPathForTest(testName);
        int elementIndex = FileManager.filesFoundCount(imageElementName, patternPath) + 1;
        StringBuffer patternName = new StringBuffer().append(patternPath)
                .append(imageElementName)
                .append(elementIndex)
                .append(".")
                .append(Constants.Extensions.IMAGE_EXTENSION);
        return patternName.toString();
    }

    public static String getPatternNameShort(String imageElementName, String testName) {
        String patternPath = getPatternPathForTest(testName);
        int elementIndex = FileManager.filesFoundCount(imageElementName, patternPath) + 1;
        StringBuffer patternName = new StringBuffer().append(imageElementName)
                .append(elementIndex)
                .append(".")
                .append(Constants.Extensions.IMAGE_EXTENSION);
        return patternName.toString();
    }
}


