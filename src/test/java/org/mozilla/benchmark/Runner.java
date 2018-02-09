package org.mozilla.benchmark;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mozilla.benchmark.objects.*;
import org.mozilla.benchmark.utils.Constants;
import org.mozilla.benchmark.utils.PropertiesManager;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by silviu.checherita on 12/13/2017.
 */
public class Runner {

    private static final Logger logger = LogManager.getLogger(Runner.class.getName());

    public static void main(String args[]) throws IOException {

        String[] scenarios = Constants.Execution.EXECUTED_SCENARIOS;
        if (scenarios.length == 0) {
            logger.warn("There are no scenarios to execute !!!");
            if (PropertiesManager.getExitIfErrorsFound()) {
                System.exit(1);
            }
        } else {
            logger.info(String.format("List of scenarios to execute: %s", Arrays.toString(scenarios)));
        }

        Thread[] threads = new Thread[scenarios.length];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new ScenarioRunner(scenarios[i]);
            threads[i].start();
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                logger.fatal(String.format("[%s] was interrupted: [%s]", threads[i], e));
            }
        }
    }
}