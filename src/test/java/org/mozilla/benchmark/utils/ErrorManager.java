package org.mozilla.benchmark.utils;

/**
 * Created by silviu.checherita on 2/23/2018.
 */
public class ErrorManager {
    public static String getErrorMessage(StackTraceElement[] stackTraceElements) {
        StringBuilder messageBuilder = new StringBuilder();
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            messageBuilder.append(stackTraceElement.toString()).append("\n\t");
        }
        return messageBuilder.toString();
    }
}
