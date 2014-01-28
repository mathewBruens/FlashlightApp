package com.spotlightppm;


import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
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
    private String screenshotResolution; //"LOW" "MEDIUM" "HIGH"
    //boolean useUserStats;
    private long userStatsMinInterval;
    private long userStatsMaxInterval;
    Map<String, Dimension> screenSizeMap = new HashMap<>();
    String[] screenSizeQuality = {"HIGH", "MEDIUM", "LOW"};
    final Dimension QQVGA = new Dimension(176, 144);
    final Dimension QVGA = new Dimension(320, 240);
    final Dimension VGA = new Dimension(640, 480);
    final Dimension XGA = new Dimension(1024, 768);
    final Dimension HD720 = new Dimension(1280, 720);

    public RemoteUserSettings() {
        webcamMinInterval = 5000;
        webcamMaxInterval = 10000;
        webcamResolution = "HIGH";

        screenshotMinInterval = 5000;
        screenshotMaxInterval = 10000;
        screenshotResolution = "HIGH";

        userStatsMinInterval = 5000;
        userStatsMaxInterval = 10000;
        
        initScreenSizeMap();
    }

    private void initScreenSizeMap() {

        screenSizeMap.put("LOW", QQVGA);
        screenSizeMap.put("MEDIUM", QVGA);
        screenSizeMap.put("HIGH", VGA);

    }

//
//    public RemoteUserSettings(long webcamMinInterval, long webcamMaxInterval,
//            String webcamResolution, long screenshotMinInterval,
//            long screenshotMaxInterval, String screenshotResolution, boolean useUserStats,
//            long userStatsMinInterval, long userStatsMaxInterval) {
//
//        this.useWebcam = useWebcam;
//        this.webcamMinInterval = webcamMinInterval;
//        this.webcamMaxInterval = webcamMaxInterval;
//        this.webcamResolution = webcamResolution;
//        this.useScreenshot = useScreenshot;
//        this.screenshotMinInterval = screenshotMinInterval;
//        this.screenshotMaxInterval = screenshotMaxInterval;
//        this.screenshotResolution = screenshotResolution;
//        this.useUserStats = useUserStats;
//        this.userStatsMinInterval = userStatsMinInterval;
//        this.userStatsMaxInterval = userStatsMaxInterval;
//    }
//
//    public boolean isUseScreenshot() {
//        return useScreenshot;
//    }
//
//    public void setUseScreenshot(boolean useScreenshot) {
//        this.useScreenshot = useScreenshot;
//    }
//
//    public long getScreenshotMinInterval() {
//        return screenshotMinInterval;
//    }
//
//    public void setScreenshotMinInterval(long screenshotMinInterval) {
//        this.screenshotMinInterval = screenshotMinInterval;
//    }
//
//    public long getScreenshotMaxInterval() {
//        return screenshotMaxInterval;
//    }
//
//    public void setScreenshotMaxInterval(long screenshotMaxInterval) {
//        this.screenshotMaxInterval = screenshotMaxInterval;
//    }
//
//    public String getScreenshotResolution() {
//        return screenshotResolution;
//    }
//
//    public void setScreenshotResolution(String screenshotResolution) {
//        this.screenshotResolution = screenshotResolution;
//    }
//
//    public boolean isUseUserStats() {
//        return useUserStats;
//    }
//
//    public void setUseUserStats(boolean useUserStats) {
//        this.useUserStats = useUserStats;
//    }
//
//    public long getUserStatsMinInterval() {
//        return userStatsMinInterval;
//    }
//
//    public void setUserStatsMinInterval(long userStatsMinInterval) {
//        this.userStatsMinInterval = userStatsMinInterval;
//    }
//
//    public long getUserStatsMaxInterval() {
//        return userStatsMaxInterval;
//    }
//
//    public void setUserStatsMaxInterval(long userStatsMaxInterval) {
//        this.userStatsMaxInterval = userStatsMaxInterval;
//    }
//
//    public boolean isUseWebcam() {
//        return useWebcam;
//    }
//
//    public void setUseWebcam(boolean useWebcam) {
//        this.useWebcam = useWebcam;
//    }
//
//    public long getWebcamMinInterval() {
//        return webcamMinInterval;
//    }
//
//    public void setWebcamMinInterval(long webcamMinInterval) {
//        this.webcamMinInterval = webcamMinInterval;
//    }
//
//    public long getWebcamMaxInterval() {
//        return webcamMaxInterval;
//    }
//
//    public void setWebcamMaxInterval(long webcamMaxInterval) {
//        this.webcamMaxInterval = webcamMaxInterval;
//    }
//
//    public String getWebcamResolution() {
//        return webcamResolution;
//    }
//
//    public void setWebcamResolution(String webcamResolution) {
//        this.webcamResolution = webcamResolution;
//    }
    public long getScreenshotMaxInterval() {
        return screenshotMaxInterval;
    }

    public long getScreenshotMinInterval() {
        return screenshotMinInterval;
    }

    public Dimension getScreenshotDimension() {
        return screenSizeMap.get(screenshotResolution);
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
