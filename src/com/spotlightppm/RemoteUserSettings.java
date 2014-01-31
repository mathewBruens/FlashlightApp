/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.spotlightppm;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Mathew
 */
public class RemoteUserSettings {
    
     //boolean useWebcam;
    private long webcamMinInterval;
    private long webcamMaxInterval;
    private String webcamResolution; //"LOW" "MEDIUM" "HIGH"
    //boolean useScreenshot;
    private long screenshotMinInterval;
    private long screenshotMaxInterval;
    private String screenshotDimension; //"LOW" "MEDIUM" "HIGH"
    //boolean useUserStats;
    private long userStatsMinInterval;
    private long userStatsMaxInterval;
    private Map<String, Dimension> screenSizeMap = new HashMap<>();
    private String[] screenSizeQuality = {"HIGH", "MEDIUM", "LOW"};
    final Dimension QQVGA = new Dimension(176, 144);
    final Dimension QVGA = new Dimension(320, 240);
    final Dimension VGA = new Dimension(640, 480);
    final Dimension XGA = new Dimension(1024, 768);
    final Dimension HD720 = new Dimension(1280, 720);

    
    private RemoteUserSettings() {
        
        webcamMinInterval = 5000;
        webcamMaxInterval = 10000;
        webcamResolution = "LOW";

        screenshotMinInterval = 5000;
        screenshotMaxInterval = 10000;
        screenshotDimension = "LOW";

        userStatsMinInterval = 10000;
        userStatsMaxInterval = 20000;
        
        initScreenSizeMap();
    }
    
    public static RemoteUserSettings getInstance() {
        return NewSingletonHolder.INSTANCE;
    }
    
    private static class NewSingletonHolder {

        private static final RemoteUserSettings INSTANCE = new RemoteUserSettings();
    }
    
       


    private void initScreenSizeMap() {

        screenSizeMap.put("LOW", QQVGA);
        screenSizeMap.put("MEDIUM", QVGA);
        screenSizeMap.put("HIGH", VGA);

    }


    public long getScreenshotMaxInterval() {
        return screenshotMaxInterval;
    }

    public long getScreenshotMinInterval() {
        return screenshotMinInterval;
    }

    public Dimension getScreenshotDimension() {
        return screenSizeMap.get(screenshotDimension);
    }

    public long getUserStatsMaxInterval() {
        return userStatsMaxInterval;
    }

    public long getUserStatsMinInterval() {
        return userStatsMinInterval;
    }

    public long getWebcamMaxInterval() {
        return webcamMaxInterval;
    }

    public long getWebcamMinInterval() {
        return webcamMinInterval;
    }

    public Dimension getWebcamDimension() {
        return screenSizeMap.get(webcamResolution);
    }
}
