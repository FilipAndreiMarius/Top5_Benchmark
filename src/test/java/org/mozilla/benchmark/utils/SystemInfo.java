package org.mozilla.benchmark.utils;

import com.sun.management.OperatingSystemMXBean;
import org.apache.xalan.xsltc.runtime.InternalRuntimeError;

import javax.management.MBeanServerConnection;
import java.io.IOException;
import java.lang.management.ManagementFactory;

public class SystemInfo {

    MBeanServerConnection mbsc = ManagementFactory.getPlatformMBeanServer();
    OperatingSystemMXBean osMBean = ManagementFactory.newPlatformMXBeanProxy(
            mbsc, ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME, OperatingSystemMXBean.class);

    public SystemInfo() throws IOException, InterruptedException {
    }

    public double getCpuLoad() throws InterruptedException {
        double cpuLoad = 0;

        try {
            Thread.sleep(500);
            cpuLoad = osMBean.getSystemCpuLoad() * 100;
        } catch (InternalRuntimeError O) {
            System.out.print(O);
        }
        return cpuLoad;
    }

    public int getAvailableMemory() {
        int result = 0;
        result = (int) ((osMBean.getFreePhysicalMemorySize() * 100) / osMBean.getTotalPhysicalMemorySize());
        System.out.print("Total available memory available:" + result + "%");
        return result;
    }

    public static void main(String args[]) throws IOException, InterruptedException {
        SystemInfo info = new SystemInfo();
        int a = 5000;
        while (a > 0) {
            info.getCpuLoad();
            a--;
        }
    }
}

