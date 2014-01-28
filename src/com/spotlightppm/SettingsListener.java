package com.spotlightppm;


import com.github.sarxos.webcam.Webcam;
import java.awt.GraphicsDevice;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mbruens
 */
public interface SettingsListener {
public void SettingsExit(Webcam activeWebcam, GraphicsDevice activeScreen);   
}
