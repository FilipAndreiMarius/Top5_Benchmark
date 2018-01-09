package org.mozilla.benchmark.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.xpath.operations.Bool;

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

    public static ArrayList<String> validateScenarios(String[] scenarios, String directoryName){
        ArrayList<String> scenarioList = new ArrayList<>();
        logger.info("Validating scenarios ...");
        for (String scenario : scenarios) {
            if (FileManager.fileFound(scenario, directoryName)){
                logger.info("[" + scenario + "] found!");
                scenarioList.add(scenario);
            }
            else {
                logger.warn("[" + scenario + "] NOT found!!!");
            }
        }
        return scenarioList;
    }
}


