package org.mozilla.benchmark.pageObjects;

import java.awt.*;

/**
 * Created by andrei.filip on 11/6/2017.
 */
public class Notifications extends Thread {

    static TrayIcon trayIcon;
    String Title = "default";
    String Message = "default message";

    public Notifications() throws AWTException, java.net.MalformedURLException {}

    public void run() {

        //Obtain only one instance of the SystemTray object
        SystemTray tray = SystemTray.getSystemTray();
        //If the icon is a file
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        trayIcon = new TrayIcon(image, "Tray ");
        //Let the system resizes the image if needed
        trayIcon.setImageAutoSize(true);
        //Set tooltip text for the tray icon
        trayIcon.setToolTip("System tray icon");
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }
        trayIcon.displayMessage(this.Title, this.Message, TrayIcon.MessageType.INFO);
    }
}

