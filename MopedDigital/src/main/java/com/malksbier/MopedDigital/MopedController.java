package com.malksbier.MopedDigital;

import com.malksbier.MopedDigital.data.mopedStatus.MopedStatus;
import com.malksbier.MopedDigital.io.IOUpdater;
import com.malksbier.MopedDigital.io.bus.SerialHelper;
import com.malksbier.MopedDigital.io.pins.GpioHelper;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.RaspiGpioProvider;
import com.pi4j.io.gpio.RaspiPinNumberingScheme;
import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialFactory;

public class MopedController {
    private MopedStatus mopedStatus;

    private final Serial serial = SerialFactory.createInstance();
    private GpioController gpioController; 

    private SerialHelper serialHelper;
    private GpioHelper gpioHelper;

    private IOUpdater ioUpdater;

    public MopedController() {
        mopedStatus = new MopedStatus();
        
        GpioFactory.setDefaultProvider(new RaspiGpioProvider(RaspiPinNumberingScheme.DEFAULT_PIN_NUMBERING));
        gpioController = GpioFactory.getInstance();

        serialHelper = new SerialHelper(serial);
        gpioHelper = new GpioHelper(gpioController);

        ioUpdater = new IOUpdater(750, 250, mopedStatus, serialHelper, gpioHelper);

        ioUpdater.start();
    }
}
