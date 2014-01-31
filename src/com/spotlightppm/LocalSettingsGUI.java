/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spotlightppm;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamException;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamPicker;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 *
 * @author Mathew
 */
public class LocalSettingsGUI extends JFrame implements ActionListener {

    JComboBox screenComboBox;
    WebcamPanel webcamPanel;
    JPanel mainPanel;
    static Webcam activeWebcam;
    WebcamPicker webcamPicker;
    JComboBox webcamComboBox;
    GraphicsEnvironment ge;
    GraphicsDevice[] screens;
    GraphicsDevice activeScreen;
    JButton okButton;
    List<SettingsListener> listeners;
    JPanel screenPanel;
    JLabel screenLabel;
    ScreenshotCapture screenshotCapture;
    Timer timer;
    ImageIcon imageIcon = new ImageIcon();
    BufferedImage image;
    Dimension dimension;
    ScreenPaintContainer screenPaintContainer;

    public LocalSettingsGUI(SettingsListener listener) {

        listeners = new ArrayList<>();
        listeners.add(listener);
        setSize(700, 325);
        dimension = new Dimension(320, 240);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        screenPaintContainer = new ScreenPaintContainer();
        screenPaintContainer.setPreferredSize(new Dimension(320, 240));
        screenshotCapture = new ScreenshotCapture();
        screenPanel = new JPanel();
        screenPanel.setPreferredSize(new Dimension(320, 240));
        screenLabel = new JLabel();
        screenPanel.add(screenLabel);
        mainPanel = new JPanel();

        mainPanel.add(new JLabel("Select Screen:"));

        screenComboBox = new JComboBox();

        mainPanel.add(screenComboBox);

        ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        screens = ge.getScreenDevices();

        for (GraphicsDevice screen : screens) {
            screenComboBox.addItem(screen.toString());
        }

        mainPanel.add(new JLabel("Select Webcam:"));

        webcamPicker = new WebcamPicker();

        mainPanel.add(webcamPicker);
        setLastSettingsAsDefault();

        //createScreenLabel();

        okButton = new JButton("OK");

        mainPanel.add(okButton);

        //mainPanel.add(screenPanel);
        mainPanel.add(screenPaintContainer);
        createWebcamPanel();

        mainPanel.add(webcamPanel);

        add(mainPanel);
        runScreenPreview();
        setVisible(true);

        okButton.addActionListener(this);
        webcamPicker.addActionListener(this);
        screenComboBox.addActionListener(this);

    }

    public void setLastSettingsAsDefault() {
        //Set the webcam and screen comboboxs to their last selection
        //which is saved in the XML
        String webcamName = LocalUserSettings.getInstance().getWebcamName();
        String screenName = LocalUserSettings.getInstance().getScreenName();
        if (!"".equals(webcamName)) {

            List<Webcam> webcams = Webcam.getWebcams();

            if (webcams != null) {
                int x = 0;
                //compare the names of the webcams with the saved webcam names
                // in the xml file
                for (Webcam webcam : webcams) {
                    if (webcam.getName().equals(webcamName)) {
                        webcamPicker.setSelectedIndex(x);
                    }
                    x++;
                }
            }

        }

        if (!"".equals(screenName)) {

            int x = 0;
            for (GraphicsDevice g : screens) {
                if (g.toString().equals(screenName)) {
                    screenComboBox.setSelectedIndex(x);
                    activeScreen = screens[x];
                }
                x++;

            }
        }

    }

    public void runScreenPreview() {
        //Every 1/5 second the timer sends an action event to the actionlistener
        //which repaints the the JPanel screenPaintContainer.
        //Having a problem with flickering.
        timer = new Timer(200, this);
        timer.start();
    }
        //How can I stop the mouse flickering?
        //createLabel() updates the screen image in a 
        //different way but still doesn't remove the mouse flickering
    public class ScreenPaintContainer extends JPanel {

        protected void paintComponent(Graphics g) {
            try {
                image = screenshotCapture.takeScreenshot(activeScreen, dimension, false);
                g.drawImage(image, 0, 0, null);
            } catch (IOException ex) {
                Logger.getLogger(LocalSettingsGUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (AWTException ex) {
                Logger.getLogger(LocalSettingsGUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(LocalSettingsGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void createScreenLabel() {
        try {

            image = screenshotCapture.takeScreenshot(activeScreen, dimension, false);
            imageIcon.setImage(image);
            screenLabel.setIcon(imageIcon);
           
            screenPanel.repaint();
            
        } catch (IOException ex) {
            Logger.getLogger(LocalSettingsGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AWTException ex) {
            Logger.getLogger(LocalSettingsGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(LocalSettingsGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void createWebcamPanel() {

        try {
            WebcamPanel temp = new WebcamPanel(webcamPicker.getSelectedWebcam());
            temp.setPreferredSize(dimension);
            temp.setFillArea(true);
            if (webcamPanel != null) {
                webcamPanel.stop();
                mainPanel.remove(webcamPanel);
            }
            webcamPanel = temp;

            activeWebcam = webcamPicker.getSelectedWebcam();

            mainPanel.add(webcamPanel);

            mainPanel.validate();

        } catch (WebcamException e) {

            JOptionPane.showMessageDialog(this, "Unable to open this webcam, please make sure it is not in use.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource().equals(timer)) {
            //createScreenLabel();
            screenPaintContainer.repaint();
        }

        if (ae.getSource().equals(okButton)) {
            timer.stop();
            LocalUserSettings.getInstance().writeWebcamName(activeWebcam.getName());
            LocalUserSettings.getInstance().writeScreenName(activeScreen.toString());

            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    webcamPanel.stop();
                    for (SettingsListener l : listeners) {

                        l.SettingsExit(activeWebcam, activeScreen);
                        dispose();
                    }
                }

            });

        }

        if (ae.getSource().equals(screenComboBox)) {
            activeScreen = screens[screenComboBox.getSelectedIndex()];
        }

        if (ae.getSource().equals(webcamPicker)) {
            //webcamPanel.stop();

            Thread t = new Thread() {
                @Override
                public void run() {
                    createWebcamPanel();
                }
            };
            SwingUtilities.invokeLater(t);

        }
    }

}
