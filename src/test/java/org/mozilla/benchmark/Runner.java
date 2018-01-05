package org.mozilla.benchmark;

import org.mozilla.benchmark.objects.ScenarioRunner;
import org.mozilla.benchmark.utils.Scenarios;

import java.io.IOException;

/**
 * Created by silviu.checherita on 12/13/2017.
 */
public class Runner {

    public static void main(String args[]) throws IOException {

        Thread googleRunner = new ScenarioRunner(Scenarios.GOOGLE.getName());
        googleRunner.start();
    }
}