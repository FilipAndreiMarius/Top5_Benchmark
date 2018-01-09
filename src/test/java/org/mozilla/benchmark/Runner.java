package org.mozilla.benchmark;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mozilla.benchmark.objects.ScenarioRunner;
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
            logger.info("Executing scenarios " + scenarios);
        }

/*        String[] scenarios = Constants.Execution.EXECUTED_SCENARIOS;
        Thread[] threads = new Thread[scenarios.length];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new ScenarioRunner(ScenarioManager.getScenarioName(scenarios[i]));
            threads[i].start();
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
    }
}