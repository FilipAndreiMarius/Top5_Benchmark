package org.mozilla.benchmark.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mozilla.benchmark.constants.PathConstants;
import org.mozilla.benchmark.objects.PageNavigationTypes;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by silviu.checherita on 2/12/2018.
 */
public class ThreadManager {

    private static final Logger logger = LogManager.getLogger(ThreadManager.class.getName());

    public static Thread getPageObjectThread(String testName, int numberOfExecutions, PageNavigationTypes type) {
        String className = PathConstants.PAGE_OBJECT_CLASS_PATH + ScenarioManager.getClassNameFromTestName(testName);

        Class<?> clazz;
        try {
            clazz = Class.forName(className);
            Constructor<?> constructor = clazz.getConstructor(int.class, PageNavigationTypes.class);
            Object instance = constructor.newInstance(numberOfExecutions, type);
            return (Thread) instance;
        } catch (ClassNotFoundException e) {
            logger.error("Class " + className + " not found ! " + e);
            if (PropertiesManager.getExitIfErrorsFound()) {
                System.exit(1);
            }
        } catch (NoSuchMethodException e) {
            logger.error("Method not found ! " + e);
            if (PropertiesManager.getExitIfErrorsFound()) {
                System.exit(1);
            }
        } catch (InstantiationException e) {
            logger.error("Could not instantiate " + className + " ! " + e);
            if (PropertiesManager.getExitIfErrorsFound()) {
                System.exit(1);
            }
        } catch (IllegalAccessException e) {
            logger.error("Illegal access ! " + e);
            if (PropertiesManager.getExitIfErrorsFound()) {
                System.exit(1);
            }
        } catch (InvocationTargetException e) {
            logger.error("Invocation target exception ! " + e);
            if (PropertiesManager.getExitIfErrorsFound()) {
                System.exit(1);
            }
        }
        return null;
    }
}
