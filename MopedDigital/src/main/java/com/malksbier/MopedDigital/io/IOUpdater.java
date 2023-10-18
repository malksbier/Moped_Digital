package com.malksbier.MopedDigital.io;

import java.time.LocalDateTime;
import java.util.Date;

import com.malksbier.MopedDigital.data.mopedStatus.MopedStatus;
import com.malksbier.MopedDigital.io.bus.SerialHelper;
import com.malksbier.MopedDigital.io.pins.GpioHelper;
import com.malksbier.MopedDigital.io.virtual.HazardRecognizer;
import com.malksbier.MopedDigital.util.timer.IntervallUpdateTimer;

public class IOUpdater extends IntervallUpdateTimer{
    public static String TAG = "[IOUpdater] ";
    private static long minSendingIntervall = 100;
    private long lastSend = 0;

    private MopedStatus mopedStatus;
    private SerialHelper serialHelper;
    private GpioHelper gpioHelper;
    private HazardRecognizer hazardRecognizer;


    public IOUpdater(long intervall, long checkDelay, MopedStatus mopedStatus,SerialHelper serialHelper,GpioHelper gpioHelper) {
        super(intervall, checkDelay);

        this.mopedStatus = mopedStatus;
        this.gpioHelper = gpioHelper;
        this.serialHelper = serialHelper;
        this.hazardRecognizer = new HazardRecognizer();
    }

    @Override
    public void trigger() { 

        //System.out.println();
        //System.out.println(TAG + "triggered at: " + LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute() + ":" + LocalDateTime.now().getSecond());
        // TODO AUTHENTICATION
        mopedStatus.setAuthorized(true);

        mopedStatus = gpioHelper.updateReadSignals(mopedStatus);
        //mopedStatus = hazardRecognizer.update(mopedStatus);


        mopedStatus = serialHelper.analyseSignals(mopedStatus);
        mopedStatus = gpioHelper.analyseInputupdateOutput(mopedStatus);

        // TODO 
        mopedStatus.setSpeed((int)(Math.random() * 90));
        mopedStatus.setRpm((int)(Math.random() * 9000));

        
        // SEND
        if(minSendingIntervall + lastSend < System.currentTimeMillis()) {
            System.out.println(TAG + "update at: " + LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute() + ":" + LocalDateTime.now().getSecond());
            serialHelper.updateSignals(mopedStatus);
            gpioHelper.writeMopedStatusSignals(mopedStatus);

            lastSend = System.currentTimeMillis();
        } 
    }
    
}
