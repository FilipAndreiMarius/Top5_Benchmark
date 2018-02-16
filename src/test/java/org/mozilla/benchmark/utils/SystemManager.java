package org.mozilla.benchmark.utils;

import com.sun.management.OperatingSystemMXBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.xalan.xsltc.runtime.InternalRuntimeError;
import org.mozilla.benchmark.constants.SystemConstants;

import javax.management.MBeanServerConnection;
import java.lang.management.ManagementFactory;

public class SystemManager {

    private static final Logger logger = LogManager.getLogger(SystemManager.class.getName());

    private static OperatingSystemMXBean getOperatingSystemMXBean() {
        OperatingSystemMXBean opSys = null;
        try {
            MBeanServerConnection server = ManagementFactory.getPlatformMBeanServer();
            opSys = ManagementFactory.newPlatformMXBeanProxy(server, ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME, OperatingSystemMXBean.class);
        } catch (Exception exp) {
            logger.fatal("Exception! ", exp);
        }
        return opSys;
    }

    private static double getCpuLoad() {
        double cpuLoad = 0;
        try {
            OperatingSystemMXBean osMBean = getOperatingSystemMXBean();
            cpuLoad = osMBean.getSystemCpuLoad() * 100;
        } catch (InternalRuntimeError e) {
            logger.fatal("InternalRuntimeError! ", e);
        }
        return cpuLoad;
    }

    public static Boolean checkCpuLoad() {
        int iteration = 0;
        while (iteration < SystemConstants.MAX_CPU_RETRIES) {
            double cpuLoad = getCpuLoad();
            if (cpuLoad > SystemConstants.CPU_DESIRED) {
                try {
                    logger.warn("Cpu Usage is too high for the moment: " + cpuLoad + "\n");
                    Thread.sleep(SystemConstants.CPU_CHECK_REFRESH_RATE);
                    iteration++;
                } catch (InterruptedException e) {
                    logger.fatal("Interrupted!", e);
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

