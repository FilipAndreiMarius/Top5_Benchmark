package org.mozilla.benchmark;

import org.mozilla.benchmark.constants.ExecutionConstants;
import org.mozilla.benchmark.objects.LoggerManagerLevel;
import org.mozilla.benchmark.objects.ScenarioRunner;
import org.mozilla.benchmark.utils.ErrorManager;
import org.mozilla.benchmark.utils.LoggerManager;
import org.mozilla.benchmark.utils.PropertiesManager;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by silviu.checherita on 12/13/2017.
 */
public class Runner {

    private static final LoggerManager logger = new LoggerManager(Runner.class.getName());

    public static void main(String args[]) throws IOException {

        String[] scenarios = ExecutionConstants.EXECUTED_SCENARIOS;
        if (scenarios.length == 0) {
            logger.log(LoggerManagerLevel.WARN, "There are no scenarios to execute !!!", false);
            if (PropertiesManager.getExitIfErrorsFound()) {
                System.exit(1);
            }
        } else {
            logger.log(LoggerManagerLevel.INFO, String.format("List of scenarios to execute: %s", Arrays.toString(scenarios)), false);

        }

        Thread[] threads = new Thread[scenarios.length];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new ScenarioRunner(scenarios[i]);
            threads[i].start();
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                logger.log(LoggerManagerLevel.FATAL, String.format("[%s] was interrupted: [%s]", threads[i], ErrorManager.getErrorMessage(e.getStackTrace())), PropertiesManager.getEmailNotification());
            }
        }
    }
}