package com.spotlightppm;


import com.github.sarxos.webcam.Webcam;
import java.awt.AWTException;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import com.github.sarxos.webcam.WebcamException;
import java.awt.GraphicsDevice;

public class Controller implements LoginSuccessListener, SettingsListener {

    RemoteUserSettings remoteUserSettings;
    Thread inputTrackerThread;
    Thread screenCaptureThread;
    Thread webcamCaptureThread;
    GraphicsDevice activeScreen;
    Webcam activeWebcam;
    FlashliteSystemTray tray;

    public static void main(String[] args) {
        new Controller();
    }

    public Controller() {
        new LoginGUI(this);
        remoteUserSettings = new RemoteUserSettings();
//        login();
//        initSettings();
//        startSettingsGUI();
//        startSystemTrayGUI();
//        runFlashlight();
    }

    private void login() {
    }

    private void initSettings() {
    }

//    Settings settings;
//    UserSettings userSettings;
//    FlashliteSystemTray tray;
//
//    private final long MIN_INTERVAL = 5000;
//    private final static Logger LOGGER = Logger
//            .getLogger(Logger.GLOBAL_LOGGER_NAME);
//    private FileHandler log;
//
//    private final long MAX_INTERVAL = 15000;
//    
//    public Controller() {
//        try {
//            String fileSeparator = System.getProperty("file.separator");
//            String userHomeFolder = System.getProperty("user.home");
//            String desktopPath = userHomeFolder.concat(fileSeparator).concat(
//                    "Desktop\\FlashlightDocs\\Log.txt");
//            log = new FileHandler(desktopPath);
//            LOGGER.addHandler(log);
//            settings = new Settings();
//        } catch (WebcamException e) {
//            TODO Auto
//            -generated  catch block e 
//            .printStackTrace();
//        } catch (TimeoutException e) {
//            TODO Auto
//            -generated  catch block e 
//            .printStackTrace();
//        } catch (SecurityException e) {
//            TODO Auto
//            -generated  catch block e 
//            .printStackTrace();
//        } catch (IOException e) {
//            TODO Auto
//            -generated  catch block e 
//            .printStackTrace();
//        }
//
//        addShutdownHook();
//
//        new LoginGUI(settings, this);
//
//    }
//
//    public void addShutdownHook() {
//        Runtime.getRuntime().addShutdownHook(new Thread() {
//
//            @Override
//            public void run() {
//                System.out.println("Flashlite exited.");
//            }
//        });
//    }
    public void startInputTracker() {

        inputTrackerThread = new Thread(new Runnable() {

            InputTracker tracker;

            @Override
            public void run() {

                while (true) {
                    //long randInterval = (long) ((Math.random() * (MAX_INTERVAL - MIN_INTERVAL)) + MIN_INTERVAL);
                    long randInterval = (long) ((Math.random() * (remoteUserSettings.getUserStatsMaxInterval() - remoteUserSettings.getUserStatsMinInterval())) + remoteUserSettings.getUserStatsMinInterval());
                    tracker = new InputTracker();
                    try {
                        Thread.sleep(randInterval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(tracker.toString());
                    tracker.unRegister();
                }
            }
        });
        inputTrackerThread.start();

    }

    public void startScreenCapture() {
        Thread screenCaptureThread = new Thread(new Runnable() {

            ScreenshotCapture screenshotCapture;

            @Override
            public void run() {
                while (true) {
                    //long randInterval = (long) ((Math.random() * (MAX_INTERVAL - MIN_INTERVAL)) + MIN_INTERVAL);
                    long randInterval = (long) ((Math.random() * (remoteUserSettings.getScreenshotMaxInterval() - remoteUserSettings.getScreenshotMinInterval())) + remoteUserSettings.getScreenshotMinInterval());
                    try {
                        Thread.sleep(randInterval);
                        screenshotCapture = new ScreenshotCapture();
                        screenshotCapture.takeScreenshot(activeScreen, remoteUserSettings.getScreenshotDimension());

                    } catch (InterruptedException | SecurityException | IOException | AWTException e) {
                    }
                }
            }
        });
        screenCaptureThread.start();
    }

    public void startSystemTray() {
        try {
            tray = new FlashliteSystemTray();
        } catch (AWTException e) {
            e.printStackTrace();
        }

    }

    public void startWebcamCapture() {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                WebcamCapture webcam;
                while (true) {
                    //long randInterval = (long) ((Math.random() * (MAX_INTERVAL - MIN_INTERVAL)) + MIN_INTERVAL);
                    long randInterval = (long) ((Math.random() * (remoteUserSettings.getWebcamMaxInterval() - remoteUserSettings.getWebcamMinInterval())) + remoteUserSettings.getWebcamMinInterval());
                    try {
                        Thread.sleep(randInterval);
                        webcam = new WebcamCapture();
                        webcam.takeWebcamPhoto(activeWebcam);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (WebcamException e) {
//                        LOGGER.info("Error! Unable to webcam, will try again later."
//                                + e.getMessage() + "\n");
//                        tray.displayErrorMessage("Error!",
//                                "Unable to open webcam, will try again later."
//                                + e.getMessage());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    public void LoginSuccess() {
        // userSettings = (new ServerPull()).getUserSettings();
        getRemoteUserSettings();
        new LocalSettingsGUI(this);

        //startSystemTray();
//        startInputTracker();
//        startScreenCapture();
//        startWebcamCapture();
    }

    public void getRemoteUserSettings() {
        //add webservice here
        remoteUserSettings = new RemoteUserSettings();
    }
//
//    public void setRemoteSettings(UserSettings userSettings) {
//    }

    @Override
    public void SettingsExit(Webcam activeWebcam, GraphicsDevice activeScreen) {
        this.activeWebcam = activeWebcam;
        this.activeScreen = activeScreen;
        
        startSystemTray();
        startInputTracker();
        startScreenCapture();
        startWebcamCapture();
    }
}
