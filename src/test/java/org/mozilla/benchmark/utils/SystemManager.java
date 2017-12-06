package org.mozilla.benchmark.utils;

import com.sun.management.HotSpotDiagnosticMXBean;
import com.sun.management.OperatingSystemMXBean;
import org.apache.xalan.xsltc.runtime.InternalRuntimeError;

import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;
import java.io.IOException;
import java.lang.management.ManagementFactory;

public class SystemManager {

    private static OperatingSystemMXBean getOperatingSystemMXBean() {

        try {
            MBeanServerConnection server = ManagementFactory.getPlatformMBeanServer();
            OperatingSystemMXBean osMBean = ManagementFactory.newPlatformMXBeanProxy(server, ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME, OperatingSystemMXBean.class);

            return osMBean;
        } catch (Exception exp) {
            throw new RuntimeException(exp);
        }
    }


    private static double getCpuLoad() throws InterruptedException {
        double cpuLoad = 0;
        try {
            Thread.sleep(500);
            OperatingSystemMXBean osMBean = getOperatingSystemMXBean();
            cpuLoad = osMBean.getSystemCpuLoad() * 100;
        } catch (InternalRuntimeError O) {
            System.out.print(O);
        }
        return cpuLoad;
    }

    public static Boolean checkCpuLoad() throws IOException, InterruptedException {

        Boolean run = false;

        while (run != true) {
            System.out.println("da");
            double cpuLoad = getCpuLoad();

            if (cpuLoad > 20.0) {
                System.out.print("Cpu Usage is too high for the moment: " + cpuLoad + "\n");
                Thread.sleep(2000);
            }
            if (cpuLoad <= 20.0 && cpuLoad > 0.0) {
                System.out.print("Cpu is under 20% usage: " + cpuLoad + "\n");
                run = true;
            }
        }
        return run;
    }

    public static int getAvailableMemory() {
        OperatingSystemMXBean osMBean = getOperatingSystemMXBean();
        int result = (int) ((osMBean.getFreePhysicalMemorySize() * 100) / osMBean.getTotalPhysicalMemorySize());
        System.out.print("Total available memory available:" + result + "%");
        return result;
    }
}

