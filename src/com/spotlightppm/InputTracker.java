package com.spotlightppm;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;

public class InputTracker implements NativeKeyListener, NativeMouseListener {

    private int numOfMousePress;
    private int numOfKeyPress;
    private final Timestamp start;
    private Timestamp end;
    private long longestKeyPressInterval; // millis
    private long longestMousePressInterval;
    private Timestamp lastKeyPress;
    private Timestamp lastMousePress;
    private final static Logger LOGGER = Logger
            .getLogger(Logger.GLOBAL_LOGGER_NAME);
    ArrayList<Timestamp> keyDataList;
    ArrayList<Timestamp> mouseDataList;

    public InputTracker() {
        keyDataList = new ArrayList();
        mouseDataList = new ArrayList();
        numOfMousePress = 0;
        numOfKeyPress = 0;
        start = new Timestamp(System.currentTimeMillis());
        lastKeyPress = start;
        lastMousePress = start;
        longestKeyPressInterval = 0;
        longestMousePressInterval = 0;
        register();
    }

    private void register() {
        try {
            GlobalScreen.registerNativeHook();
            GlobalScreen.getInstance().addNativeKeyListener(this);
            GlobalScreen.getInstance().addNativeMouseListener(this);
        } catch (NativeHookException ex) {
            Logger.getLogger(InputTracker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


@Override
        public void nativeKeyPressed(NativeKeyEvent e) {
      
        Timestamp now = new Timestamp(System.currentTimeMillis());
        keyDataList.add(now);
        long timeBetweenKeysPressed = now.getTime() - lastKeyPress.getTime();

        if (timeBetweenKeysPressed >= longestKeyPressInterval) {
            longestKeyPressInterval = timeBetweenKeysPressed;
        }
        lastKeyPress = now;
        numOfKeyPress++;

    }

    @Override
        public void nativeKeyReleased(NativeKeyEvent arg0) {
    }

    @Override
        public void nativeKeyTyped(NativeKeyEvent arg0) {
    }

    @Override
        public void nativeMouseClicked(NativeMouseEvent arg0) {
    }

    @Override
        public void nativeMousePressed(NativeMouseEvent arg0) {
  
        Timestamp now = new Timestamp(System.currentTimeMillis());
        mouseDataList.add(now);

        long timeBetweenMouseMov = now.getTime() - lastMousePress.getTime();

        if (timeBetweenMouseMov >= longestMousePressInterval) {
            longestMousePressInterval = timeBetweenMouseMov;
        }

        lastMousePress = now;
        numOfMousePress++;

    }

    @Override
        public void nativeMouseReleased(NativeMouseEvent arg0) {
    }

    public void setEndTime() {
        end = new Timestamp(System.currentTimeMillis());
    }

    @Override
        public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("InputTracker [numOfMousePress=");
        builder.append(numOfMousePress);
        builder.append(", numOfKeyPress=");
        builder.append(numOfKeyPress);
        builder.append(", start=");
        builder.append(start);
        builder.append(", end=");
        builder.append(end);
        builder.append(", longestKeyPressInterval=");
        builder.append(longestKeyPressInterval);
        builder.append(", longestMousePressInterval=");
        builder.append(longestMousePressInterval);
        builder.append(", lastKeyPress=");
        builder.append(lastKeyPress);
        builder.append(", lastMousePress=");
        builder.append(lastMousePress);
        builder.append("]");
        LOGGER.info(builder.toString() + "\n");
        return builder.toString();
    }

    public void unRegister() {
        GlobalScreen.unregisterNativeHook();
        GlobalScreen.getInstance().removeNativeKeyListener(this);
        GlobalScreen.getInstance().removeNativeMouseListener(this);
    }

    void makeBarChart() {
        new BarChart(mouseDataList, keyDataList, start, end).createBarChart();
    

}

    public class mouseData {

    Timestamp timestamp;

    public mouseData(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

}

public class keyData {

    Timestamp timestamp;

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public keyData(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
}
