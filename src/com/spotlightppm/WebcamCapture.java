package com.spotlightppm;



import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamException;
import java.io.ByteArrayOutputStream;

public class WebcamCapture {
	

	public void takeWebcamPhoto(Webcam activeWebcam) throws IOException, WebcamException {
		BufferedImage bi = null;

		if (activeWebcam.isOpen()) {
			bi = activeWebcam.getImage();
		}

		else {
			activeWebcam.open(true);
			bi = activeWebcam.getImage();
			activeWebcam.close();
		}

		if (bi == null) {
			throw new WebcamException("Unable to capture webcam image");
		}

		Calendar now = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat(
				"MM_dd_yyyy@hh_mm_ssa_z");
		String fileSeparator = System.getProperty("file.separator");
		String userHomeFolder = System.getProperty("user.home");
		String desktopPath = userHomeFolder.concat(fileSeparator).concat(
				"Desktop\\FlashlightDocs");
                      String path = "FlashlightData".concat(fileSeparator);
        
		String screenShotFileName = formatter.format(now.getTime()).concat(".jpg");
		String prefix = "Webcam_";
		screenShotFileName = prefix + screenShotFileName;

		//ImageIO.write(bi, "JPG", new File(desktopPath, screenShotFileName));
                ImageIO.write(bi, "JPG", new File(path, screenShotFileName));
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bi, "JPG", baos);
                baos.flush();
            //    (new ServerPush()).pushWebcamImage(baos.toByteArray());
               
	}

}
