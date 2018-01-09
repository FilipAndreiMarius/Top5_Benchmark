package org.mozilla.benchmark;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mozilla.benchmark.objects.*;
import org.mozilla.benchmark.utils.Constants;
import org.mozilla.benchmark.utils.FileManager;
import org.mozilla.benchmark.utils.ScenarioManager;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by silviu.checherita on 12/13/2017.
 */
public class Runner {

    private static final Logger logger = LogManager.getLogger(Runner.class.getName());
    public static void main(String args[]) throws IOException {

        ArrayList<String> scenarios = ScenarioManager.validateScenarios(Constants.Execution.EXECUTED_SCENARIOS, Constants.Paths.RESOURCES_PATH);
        if (scenarios.size() == 0){
            logger.warn("There are no scenarios to execute !!!");
        }else{
            logger.info("List of scenarios to execute: " + scenarios);
        }

        Thread[] threads = new Thread[scenarios.size()];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new ScenarioRunner(ScenarioManager.getScenarioName(scenarios.get(i)));
            threads[i].start();
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}