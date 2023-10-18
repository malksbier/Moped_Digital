package com.malksbier.MopedDigital.util.timer;
import java.util.Objects;
import java.util.function.Function;

public class IntervallUpdateTimer extends Timer {
    public static String TAG = "[IntervallUpdateTimer] ";

    private long lastTimeTriggered = -1;
    private boolean dead = false;

    private long intervall = 500;
    private long checkDelay = 100;

    @Override
    public void run() {
        while(!dead) {
            if(lastTimeTriggered + intervall <= System.currentTimeMillis()) {
                this.trigger();
            }
        }
        try {
            Thread.sleep(checkDelay);
        } catch (InterruptedException e) {
            System.out.println(TAG + "could not sleep");
            e.printStackTrace();
        }
        
    }

    public IntervallUpdateTimer(long intervall, long checkDelay) {
        this.intervall = intervall;
        this.checkDelay = checkDelay;
    }

}