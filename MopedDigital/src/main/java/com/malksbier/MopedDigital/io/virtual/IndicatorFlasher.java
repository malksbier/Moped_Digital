package com.malksbier.MopedDigital.io.virtual;

/*
90 pro minute § 54 StVZ
1,5 pro sekunde

700ms an
600ms aus
 */

public class IndicatorFlasher {
    public static String TAG = "[IndicatorFlasher] ";

    private long lastTimeFlashed = 0;
    private final long pause = 750;
    private final long on = 750;

    public IndicatorFlasher() {

    }

    public boolean updateFlashSignal() {    

        long currentTime = System.currentTimeMillis();
        /* 
        System.out.println();
        System.out.println(lastTimeFlashed + ", now: " + currentTime);
        System.out.println();
        */
        // wenn der letzte Intervall lange her ist
        if(lastTimeFlashed + (pause + on) < currentTime) {
            //System.out.println(TAG + "turn on");

            lastTimeFlashed = currentTime;
            return true;
        }
        // wenn die on ist
        // bis da anbleiben     
        if(lastTimeFlashed + on > currentTime) {
            //System.out.println(TAG + "stay on");
            return true;
        } else {
            //System.out.println(TAG + "stay off");
            return false;
        }
    }
    public void reset() {
        //System.out.println(TAG + "reset");
        lastTimeFlashed = System.currentTimeMillis() - (pause + on);
    }
}
