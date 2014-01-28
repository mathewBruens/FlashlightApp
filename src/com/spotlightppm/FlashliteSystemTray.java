package com.spotlightppm;



import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingWorker;

import com.github.sarxos.webcam.WebcamException;

public class FlashliteSystemTray {

	final int NEW_WIDTH = 16;
	final int NEW_HEIGHT = 16;
	SystemTray tray;
	TrayIcon trayIcon;
	

	public FlashliteSystemTray() throws AWTException {

		

		Image icon = Toolkit.getDefaultToolkit().getImage(
				FlashliteSystemTray.class.getResource(System.getProperty("file.separator")+"Flashlight-32x32.png"));
		Image scaledIcon = icon.getScaledInstance(NEW_WIDTH, NEW_HEIGHT,
				java.awt.Image.SCALE_SMOOTH);

		PopupMenu popup = new PopupMenu();

		trayIcon = new TrayIcon(scaledIcon, null, popup);
		tray = SystemTray.getSystemTray();

		MenuItem exitItem = new MenuItem("Logout");
		MenuItem webcamSettings = new MenuItem("Webcam Settings");

		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(1);
                                //add logout webservice
			}
		});

		webcamSettings.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//new SettingsGUI();
			}
		});

		popup.add(webcamSettings);
		popup.add(exitItem);

		trayIcon.setPopupMenu(popup);

		tray.add(trayIcon);

	}

	public void displayErrorMessage(final String caption, final String message) {
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				trayIcon.displayMessage(caption, message, MessageType.ERROR);
				return null;
			}

		};
		worker.execute();
	}

	public void openSettings() {
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				try {
//					new SettingsGUI(settings).run();
				} catch (WebcamException e) {
					System.out.println("Webcam in use from open.");
					displayErrorMessage("Error",
							"Webcam in use." + e.getMessage());
				}
				return null;
			}
		};
		worker.execute();
	}
}
