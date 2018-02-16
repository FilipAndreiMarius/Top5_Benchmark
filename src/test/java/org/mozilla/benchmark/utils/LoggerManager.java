package org.mozilla.benchmark.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mozilla.benchmark.constants.MailConstants;
import org.mozilla.benchmark.mail.MailBuilder;
import org.mozilla.benchmark.objects.LoggerManagerLevel;

/**
 * Created by silviu.checherita on 2/16/2018.
 */
public class LoggerManager {

    private final Logger logger;
    private static final Boolean EXIT_IF_ERROR_FOUND = PropertiesManager.getExitIfErrorsFound();

    public LoggerManager(String className) {
        this.logger = LogManager.getLogger(className);
    }

    public void log(LoggerManagerLevel level, String message, Boolean emailNotification) {

        switch (level){
            case DEBUG: {
                this.logger.debug(message);
                break;
            }
            case WARN: {
                this.logger.warn(message);
                break;
            }
            case TRACE: {
                this.logger.trace(message);
                break;
            }
            case INFO: {
                this.logger.info(message);
                createAndSendMail(MailConstants.INFO_TITLE, message, emailNotification);
                break;
            }
            case FATAL: {
                this.logger.fatal(message);
                createAndSendMail(MailConstants.ERROR_TITLE, message, emailNotification);
                exit();
                break;
            }
            case ERROR: {
                logger.error(message);
                createAndSendMail(MailConstants.ERROR_TITLE, message, emailNotification);
                exit();
                break;
            }
        }
    }

    private static void exit(){
        if (EXIT_IF_ERROR_FOUND) {
            System.exit(1);
        }
    }

    private static void createAndSendMail(String title, String message, Boolean emailNotification){
        if(emailNotification) {
            MailBuilder mail = new MailBuilder(title, message, PropertiesManager.getErrorEmailRecipients());
            mail.sendMail();
        }
    }
}
