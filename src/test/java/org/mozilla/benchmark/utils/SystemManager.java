package org.mozilla.benchmark.utils;

import com.sun.management.OperatingSystemMXBean;
import org.apache.xalan.xsltc.runtime.InternalRuntimeError;

import javax.management.MBeanServerConnection;
import java.lang.management.ManagementFactory;

public class SystemManager {

    private static OperatingSystemMXBean getOperatingSystemMXBean() {

        try {
            MBeanServerConnection server = ManagementFactory.getPlatformMBeanServer();
            return ManagementFactory.newPlatformMXBeanProxy(server, ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME, OperatingSystemMXBean.class);
        } catch (Exception exp) {
            throw new RuntimeException(exp);
        }
    }

    private static double getCpuLoad() {
        double cpuLoad = 0;
        try {
            OperatingSystemMXBean osMBean = getOperatingSystemMXBean();
            cpuLoad = osMBean.getSystemCpuLoad() * 100;
        } catch (InternalRuntimeError e) {
            e.printStackTrace();
        }
        return cpuLoad;
    }

    public static Boolean checkCpuLoad() {
        int iteration = 0;
        while (iteration < Constants.System.MAX_CPU_RETRIES) {
            double cpuLoad = getCpuLoad();
            if (cpuLoad > Constants.System.CPU_DESIRED) {
                try {
                    System.out.print("Cpu Usage is too high for the moment: " + cpuLoad + "\n");
                    Thread.sleep(Constants.System.CPU_CHECK_REFRESH_RATE);
                    iteration++;
                } catch (InterruptedException e){
                    e.printStackTrace();
                    return false;
                }
            }
            if (cpuLoad <= Constants.System.CPU_DESIRED && cpuLoad > 0.0) {
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

