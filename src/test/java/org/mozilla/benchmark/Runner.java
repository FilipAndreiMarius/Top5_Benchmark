package org.mozilla.benchmark;

import org.mozilla.benchmark.objects.ScenarioRunner;
import org.mozilla.benchmark.utils.Constants;

import java.io.IOException;

/**
 * Created by silviu.checherita on 12/13/2017.
 */
public class Runner {

    private static String getScenarioName(String jsonName) {
        return jsonName.replace(Constants.Extensions.JSON_EXTENSION, "");

    }

    public static void main(String args[]) throws IOException {

        String[] scenarios = Constants.Execution.EXECUTED_SCENARIOS;
        Thread[] threads = new Thread[scenarios.length];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new ScenarioRunner(getScenarioName(scenarios[i]));
            threads[i].start();
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}