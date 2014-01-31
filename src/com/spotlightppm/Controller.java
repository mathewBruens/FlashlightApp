package com.spotlightppm;

import com.github.sarxos.webcam.Webcam;
import java.awt.AWTException;
import java.io.IOException;
import java.util.logging.Logger;
import com.github.sarxos.webcam.WebcamException;
import java.awt.GraphicsDevice;
import java.util.Timer;

import java.util.TimerTask;
import java.util.logging.Level;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

public class Controller implements LoginSuccessListener, SettingsListener {

    Thread inputTrackerThread;
    Thread screenCaptureThread;
    Thread webcamCaptureThread;
    GraphicsDevice activeScreen;
    Webcam activeWebcam;
    FlashliteSystemTray tray;
    Timer timer;
    InputTracker tracker;

    public static void main(String[] args) {
        new Controller();

    }

    public Controller() {
        new LoginGUI(this);

    }

    public void startInputTracker() {
//        
//               
        //Records/tracks user keystrokes and user mouse actions

        tracker = new InputTracker();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                
                tracker.unRegister();
            }
        });

        long randInterval = (long) ((Math.random() * (RemoteUserSettings.getInstance().getUserStatsMaxInterval() - RemoteUserSettings.getInstance().getUserStatsMinInterval())) + RemoteUserSettings.getInstance().getUserStatsMinInterval());

        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                tracker.setEndTime();
                System.out.println(tracker.toString());
                tracker.makeBarChart();
                //tracker.unRegister();
                startInputTracker();
            }
        }, randInterval);

    }

    public void startScreenCapture() {
        Thread screenCaptureThread = new Thread(new Runnable() {

            ScreenshotCapture screenshotCapture;

            @Override
            public void run() {
                while (true) {
                    //long randInterval = (long) ((Math.random() * (MAX_INTERVAL - MIN_INTERVAL)) + MIN_INTERVAL);
                    long randInterval = (long) ((Math.random() * (RemoteUserSettings.getInstance().getScreenshotMaxInterval() - RemoteUserSettings.getInstance().getScreenshotMinInterval())) + RemoteUserSettings.getInstance().getScreenshotMinInterval());
                    try {
                        Thread.sleep(randInterval);
                        screenshotCapture = new ScreenshotCapture();
                        screenshotCapture.takeScreenshot(activeScreen, RemoteUserSettings.getInstance().getScreenshotDimension(), true);

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
                    long randInterval = (long) ((Math.random() * (RemoteUserSettings.getInstance().getWebcamMaxInterval() - RemoteUserSettings.getInstance().getWebcamMinInterval())) + RemoteUserSettings.getInstance().getWebcamMinInterval());
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

        new LocalSettingsGUI(this);
        getRemoteUserSettings();
    }

    public boolean getRemoteUserSettings() {
        //add webservice here
        //parse xml or call individual web service methods

        return true;
    }

    public void setRemoteUserSettings() {
        activeWebcam.setViewSize(RemoteUserSettings.getInstance().getWebcamDimension());
    }

    @Override
    public void SettingsExit(Webcam activeWebcam, GraphicsDevice activeScreen) {
        this.activeWebcam = activeWebcam;
        this.activeScreen = activeScreen;
        setRemoteUserSettings();
        startSystemTray();
        startInputTracker();
        startScreenCapture();
        startWebcamCapture();
    }
}
