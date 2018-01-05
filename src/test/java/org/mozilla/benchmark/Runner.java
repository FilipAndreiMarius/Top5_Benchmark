package org.mozilla.benchmark;

import org.mozilla.benchmark.objects.ScenarioRunner;
import org.mozilla.benchmark.utils.Constants;

import java.io.IOException;

/**
 * Created by silviu.checherita on 12/13/2017.
 */
public class Runner {

    private static String getScenarioName(String jsonName){
        return jsonName.replace(".json", "");

    }

    public static void main(String args[]) throws IOException {

        String[] executedScenarios = Constants.Execution.EXECUTED_SCENARIOS;
        for (String scenario : executedScenarios) {
            Thread[] threads = new Thread[executedScenarios.length];
            for (int i = 0; i < threads.length; i++) {
                threads[i] = new ScenarioRunner(getScenarioName(scenario));
                threads[i].start();
            }
        }
    }
}