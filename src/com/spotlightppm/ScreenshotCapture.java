package com.spotlightppm;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;

public class ScreenshotCapture {

    public ScreenshotCapture() {
    }

    private BufferedImage getScaledImage(BufferedImage srcImg, int w, int h) {

         //float aspectRatio = (float)srcImg.getWidth() / (float) srcImg.getHeight();

        BufferedImage resizedImg = new BufferedImage(w, h, 1);
        // BufferedImage.TRANSLUCENT);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h , null);

        g2.dispose();
        return resizedImg;
    }

    public BufferedImage takeScreenshot(GraphicsDevice activeScreen, Dimension dimension, Boolean IOWrite) throws IOException, AWTException,
            SecurityException {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "MM_dd_yyyy@hh_mm_ssa_z");
        Robot robot;
        Calendar now = Calendar.getInstance();
        String fileSeparator = System.getProperty("file.separator");
        String userHomeFolder = System.getProperty("user.home");
        String desktopPath = userHomeFolder.concat(fileSeparator).concat(
                "Desktop").concat(fileSeparator).concat("FlashlightData");
        
        String path = "FlashlightData".concat(fileSeparator);
        

        robot = new Robot(activeScreen);
        BufferedImage screenShot = robot.createScreenCapture(new Rectangle(
                Toolkit.getDefaultToolkit().getScreenSize()));

        //System.out.println(Toolkit.getDefaultToolkit().getScreenSize());
        //System.out.println(settings.getActiveScreenDimension());

        // I think equals needs to be overridded to compare two dimensions
        //This doesn't scale well for images on a 1680 X 1050 screen
        //if (Toolkit.getDefaultToolkit().getScreenSize() != (dimension)) {
            screenShot = getScaledImage(screenShot, (int) dimension.getWidth(), (int) dimension.getHeight());
        //}

        String screenShotFileName = formatter.format(now.getTime()).concat(
                ".jpg");
        
//        ImageIO.write(screenShot, "JPG", new File(desktopPath,
//                screenShotFileName));
        if(IOWrite){
        ImageIO.write(screenShot, "JPG", new File(path, screenShotFileName));
        }
        
        return screenShot;
    }
}
