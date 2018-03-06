package org.mozilla.benchmark.utils;

import org.mozilla.benchmark.constants.PathConstants;
import org.mozilla.benchmark.objects.LoggerManagerLevel;
import org.mozilla.benchmark.objects.PageNavigationTypes;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by silviu.checherita on 2/12/2018.
 */
public class ThreadManager {

    private static final LoggerManager logger = new LoggerManager(ThreadManager.class.getName());

    public static Thread getPageObjectThread(String testName, int numberOfExecutions, PageNavigationTypes type) {
        String className = PathConstants.PAGE_OBJECT_CLASS_PATH + ScenarioManager.getClassNameFromTestName(testName);

        Class<?> clazz;
        try {
            clazz = Class.forName(className);
            Constructor<?> constructor = clazz.getConstructor(int.class, String.class, PageNavigationTypes.class);
            Object instance = constructor.newInstance(numberOfExecutions, testName, type);
            return (Thread) instance;
        } catch (ClassNotFoundException e) {
            logger.log(LoggerManagerLevel.FATAL, String.format("Class [%s] not found ! [%s]", className, ErrorManager.getErrorMessage(e.getStackTrace())), PropertiesManager.getEmailNotification());
        } catch (NoSuchMethodException e) {
            logger.log(LoggerManagerLevel.FATAL, String.format("Method not found ! [%s]", ErrorManager.getErrorMessage(e.getStackTrace())), PropertiesManager.getEmailNotification());
        } catch (InstantiationException e) {
            logger.log(LoggerManagerLevel.FATAL, String.format("Could not instantiate [%s] ! [%s]", className, ErrorManager.getErrorMessage(e.getStackTrace())), PropertiesManager.getEmailNotification());
        } catch (IllegalAccessException e) {
            logger.log(LoggerManagerLevel.FATAL, String.format("Illegal access ! [%s]", ErrorManager.getErrorMessage(e.getStackTrace())), PropertiesManager.getEmailNotification());
        } catch (InvocationTargetException e) {
            logger.log(LoggerManagerLevel.FATAL, String.format("Invocation target exception ! [%s]", ErrorManager.getErrorMessage(e.getStackTrace())), PropertiesManager.getEmailNotification());
        }
        return null;
    }
}
