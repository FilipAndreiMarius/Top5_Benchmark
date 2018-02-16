package org.mozilla.benchmark.constants;

import org.mozilla.benchmark.utils.PropertiesManager;

/**
 * Created by Silviu on 14/02/2018.
 */
public final class ExecutionConstants {

    public static final int NUMBER_OF_RUNS = PropertiesManager.getNumberOfRuns();
    public static final String[] EXECUTED_SCENARIOS = PropertiesManager.getScenariosToExecute();

    private ExecutionConstants() {
    }
}
