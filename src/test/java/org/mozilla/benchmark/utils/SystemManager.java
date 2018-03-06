package org.mozilla.benchmark.utils;

import com.sun.management.OperatingSystemMXBean;
import org.apache.xalan.xsltc.runtime.InternalRuntimeError;
import org.mozilla.benchmark.constants.SystemConstants;
import org.mozilla.benchmark.objects.LoggerManagerLevel;

import javax.management.MBeanServerConnection;
import java.lang.management.ManagementFactory;

public class SystemManager {

    private static final LoggerManager logger = new LoggerManager(SystemManager.class.getName());

    private static OperatingSystemMXBean getOperatingSystemMXBean() {
        OperatingSystemMXBean opSys = null;
        try {
            MBeanServerConnection server = ManagementFactory.getPlatformMBeanServer();
            opSys = ManagementFactory.newPlatformMXBeanProxy(server, ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME, OperatingSystemMXBean.class);
        } catch (Exception e) {
            logger.log(LoggerManagerLevel.FATAL, String.format("Exception! [%s]", ErrorManager.getErrorMessage(e.getStackTrace())), PropertiesManager.getEmailNotification());
        }
        return opSys;
    }

    private static double getCpuLoad() {
        double cpuLoad = 0;
        try {
            OperatingSystemMXBean osMBean = getOperatingSystemMXBean();
            cpuLoad = osMBean.getSystemCpuLoad() * 100;
        } catch (InternalRuntimeError e) {
            logger.log(LoggerManagerLevel.FATAL, String.format("InternalRuntimeError! [%s] ", ErrorManager.getErrorMessage(e.getStackTrace())), PropertiesManager.getEmailNotification());
        }
        return cpuLoad;
    }

    public static Boolean checkCpuLoad() {
        int iteration = 0;
        while (iteration < SystemConstants.MAX_CPU_RETRIES) {
            double cpuLoad = getCpuLoad();
            if (cpuLoad > SystemConstants.CPU_DESIRED) {
                try {
                    logger.log(LoggerManagerLevel.WARN, String.format("Cpu Usage is too high for the moment: [%s]", cpuLoad), false);
                    Thread.sleep(SystemConstants.CPU_CHECK_REFRESH_RATE);
                    iteration++;
                } catch (InterruptedException e) {
                    logger.log(LoggerManagerLevel.FATAL, String.format("Interrupted! [%s]", ErrorManager.getErrorMessage(e.getStackTrace())), PropertiesManager.getEmailNotification());
                    return false;
                }
            }
            if (cpuLoad <= SystemConstants.CPU_DESIRED && cpuLoad > 0.0) {
                return true;
            }
        }
        return false;
    }

    public static long getAvailableMemory() {
        OperatingSystemMXBean osMBean = getOperatingSystemMXBean();
        return ((osMBean.getFreePhysicalMemorySize() * 100) / osMBean.getTotalPhysicalMemorySize());
    }
}

