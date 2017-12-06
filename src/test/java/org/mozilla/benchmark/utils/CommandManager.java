package org.mozilla.benchmark.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Created by Silviu on 06/12/2017.
 */
public class CommandManager {

    public static void runCmd(String command) throws IOException, InterruptedException {

        ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", command);
        builder.redirectErrorStream(true);
        Process p = builder.start();

        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        OutputStream out = p.getOutputStream();
        out.write("q\n".getBytes());
        out.flush();
        Thread.sleep(5000);

        String line;
        while (true) {
            line = r.readLine();
            if (line == null) {
                break;
            }
            System.out.print("\n" + line);
        }
    }
}
