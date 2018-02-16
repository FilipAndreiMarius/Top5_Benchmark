package org.mozilla.benchmark;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mozilla.benchmark.constants.ExecutionConstants;
import org.mozilla.benchmark.constants.MailConstants;
import org.mozilla.benchmark.mail.MailBuilder;
import org.mozilla.benchmark.objects.ScenarioRunner;
import org.mozilla.benchmark.utils.PropertiesManager;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by silviu.checherita on 12/13/2017.
 */
public class Runner {

    private static final Logger logger = LogManager.getLogger(Runner.class.getName());

    public static void main(String args[]) throws IOException {

        String[] scenarios = ExecutionConstants.EXECUTED_SCENARIOS;
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
                String error = String.format("[%s] was interrupted: [%s]", threads[i], e);
                logger.fatal(error);
                if (PropertiesManager.getEmailNotification()){
                    MailBuilder mail = new MailBuilder(MailConstants.TITLE_ERROR, error, PropertiesManager.getErrorEmailRecipients());
                    mail.sendMail();
                }
            }
        }
    }
}