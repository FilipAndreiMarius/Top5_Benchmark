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

    public LoggerManager(String className) {
        this.logger = LogManager.getLogger(className);
    }

    public void log(LoggerManagerLevel level, String message, Boolean emailNotification) {
        System.out.println(logger);

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
                break;
            }
            case FATAL: {
                this.logger.fatal(message);
                break;
            }
            case ERROR: {
                logger.error(message);
                break;
            }
        }

        if (emailNotification){
            MailBuilder mail = new MailBuilder(MailConstants.TITLE_ERROR, message, PropertiesManager.getErrorEmailRecipients());
            mail.sendMail();
        }
        if (PropertiesManager.getExitIfErrorsFound() && (LoggerManagerLevel.FATAL.equals(level) || LoggerManagerLevel.ERROR.equals(level))) {
            System.exit(1);
        }
    }
}
