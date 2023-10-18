package com.malksbier.MopedDigital.io.pins;

import com.malksbier.MopedDigital.data.mopedStatus.MopedStatus;
import com.malksbier.MopedDigital.data.mopedStatus.MopedStatusInput;
import com.malksbier.MopedDigital.data.mopedStatus.MopedStatusOutput;
import com.malksbier.MopedDigital.io.virtual.IndicatorFlasher;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class GpioHelper {
    private static String TAG = "MopedIO ";

    private GpioController gpioController;

    private GpioPinDigitalInput indicatorLeftDigitalInput;
    private GpioPinDigitalInput indicatorRightDigitalInput;
    private GpioPinDigitalInput highBeamDigitalInput;
    private GpioPinDigitalInput signalBeamDigitalInput;
    private GpioPinDigitalInput signalHornDigitalInput;

    private GpioPinDigitalOutput indicatorLeftFrontDigitalOutput;
    private GpioPinDigitalOutput indicatorLeftBackDigitalOutput;
    private GpioPinDigitalOutput indicatorRightFrontDigitalOutput;
    private GpioPinDigitalOutput indicatorRightBackDigitalOutput;
    private GpioPinDigitalOutput ignitionDigitalOutput;

    private GpioPinDigitalOutput parkingLightDigitalOutput;
    private GpioPinDigitalOutput lowBeamDigitalOutput;
    private GpioPinDigitalOutput highBeamDigitalOutput;
    private GpioPinDigitalOutput backLightDigitalOutput;
    private GpioPinDigitalOutput breakingDigitalOutput;
    
    private GpioPinDigitalOutput signalHornDigitalOutput;


    private IndicatorFlasher indicatorFlasher;
    public GpioHelper(GpioController gpio) {
        gpioController = gpio;
        indicatorFlasher = new IndicatorFlasher();

        if(gpioController != null) {
            //System.out.println(TAG + "created Controller and Pins");

            indicatorLeftDigitalInput = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_01, PinPullResistance.PULL_DOWN);
            indicatorRightDigitalInput = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_04,PinPullResistance.PULL_DOWN);
            highBeamDigitalInput = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_05,PinPullResistance.PULL_DOWN);
            signalBeamDigitalInput= gpioController.provisionDigitalInputPin(RaspiPin.GPIO_27,PinPullResistance.PULL_DOWN);
            signalHornDigitalInput = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_26,PinPullResistance.PULL_DOWN);
    
            indicatorLeftBackDigitalOutput = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_02, PinState.LOW);
            indicatorLeftFrontDigitalOutput = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_07, PinState.LOW);
            indicatorRightBackDigitalOutput = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_03, PinState.LOW);
            indicatorRightFrontDigitalOutput = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_00, PinState.LOW);
            ignitionDigitalOutput = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_25, PinState.LOW);
            parkingLightDigitalOutput = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_22, PinState.LOW);
            lowBeamDigitalOutput = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_29, PinState.LOW);
            highBeamDigitalOutput = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_28, PinState.LOW);
            backLightDigitalOutput = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_23, PinState.LOW);
            breakingDigitalOutput = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_24, PinState.LOW);
            signalHornDigitalOutput = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_21, PinState.LOW);

            /*
            indicatorLeftDigitalInput = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_18, PinPullResistance.PULL_DOWN);
            indicatorRightDigitalInput = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_23,PinPullResistance.PULL_DOWN);
            highBeamDigitalInput = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_24,PinPullResistance.PULL_DOWN);
            signalBeamDigitalInput= gpioController.provisionDigitalInputPin(RaspiPin.GPIO_16,PinPullResistance.PULL_DOWN);
            signalHornDigitalInput = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_12,PinPullResistance.PULL_DOWN);
    
            indicatorLeftBackDigitalOutput = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_27, PinState.LOW);
            indicatorLeftFrontDigitalOutput = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_04, PinState.LOW);
            indicatorRightBackDigitalOutput = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_22, PinState.LOW);
            indicatorRightFrontDigitalOutput = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_17, PinState.LOW);
            ignitionDigitalOutput = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_26, PinState.LOW);
            parkingLightDigitalOutput = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_06, PinState.LOW);
            lowBeamDigitalOutput = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_21, PinState.LOW);
            highBeamDigitalOutput = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_20, PinState.LOW);
            backLightDigitalOutput = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_13, PinState.LOW);
            breakingDigitalOutput = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_19, PinState.LOW);
            signalHornDigitalOutput = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_05, PinState.LOW);
            */
        } else {
            System.out.println(TAG + "did not create Controller and Pins");
        }
        
    }

    public MopedStatus updateReadSignals(MopedStatus status) {
        if(status.getInput() == null) {
            status.setInput(new MopedStatusInput(false, false, false, false, false));
        }
        
        status.getInput().setIndicatorLeft(indicatorLeftDigitalInput.isHigh());
        status.getInput().setIndicatorRight(indicatorRightDigitalInput.isHigh());
        status.getInput().setHighBeam(highBeamDigitalInput.isHigh());
        status.getInput().setSignalBeam(signalBeamDigitalInput.isHigh());
        status.getInput().setSignalHorn(signalHornDigitalInput.isHigh());
        
        return status;
    }

    public MopedStatus analyseInputupdateOutput(MopedStatus status) {
        MopedStatusOutput newOutput = new MopedStatusOutput();
        if(status.isAuthorized()) {
            newOutput.setIgnition(true);
        }
        if(status.getInput().getHighBeam() || status.getInput().isSignalBeam()) {
            newOutput.setHighBeam(true);
        }


        boolean canFlash = indicatorFlasher.updateFlashSignal();
        //boolean canFlash = true;

        if(canFlash && status.getInput().getIndicatorLeft()) {
            newOutput.setIndicatorLeft(true);
        }
        if(canFlash && status.getInput().getIndicatorRight()) {
            newOutput.setIndicatorRight(true);
        }

        if(!status.getInput().getIndicatorRight() && !status.getInput().getIndicatorLeft()) {
            indicatorFlasher.reset();
        }


        // Digital
        newOutput.setBackLight(true);
        newOutput.setLowBeam(true);




        status.setOutput(newOutput);
        return status;
    }

    public void writeMopedStatusSignals(MopedStatus status) {
        if(status.getOutput().getBackLight()) {
            backLightDigitalOutput.setState(PinState.HIGH);
        } else {
            backLightDigitalOutput.setState(PinState.LOW);
        }

        if(status.getOutput().getBreakingLight()) {
            breakingDigitalOutput.setState(PinState.HIGH);
        }else {
            breakingDigitalOutput.setState(PinState.LOW);
        }

        if(status.getOutput().getHighBeam()) {
            highBeamDigitalOutput.setState(PinState.HIGH);
        }else {
            highBeamDigitalOutput.setState(PinState.LOW);
        }

        if(status.getOutput().getIgnition()) {
            ignitionDigitalOutput.setState(PinState.HIGH);
        }else {
            ignitionDigitalOutput.setState(PinState.LOW);
        }

        if(status.getOutput().getIndicatorLeftBack()) {
            indicatorLeftBackDigitalOutput.setState(PinState.HIGH);
        }else {
            indicatorLeftBackDigitalOutput.setState(PinState.LOW);
        }

        if(status.getOutput().getIndicatorLeftFront()) {
            indicatorLeftFrontDigitalOutput.setState(PinState.HIGH);
        }else {
            indicatorLeftFrontDigitalOutput.setState(PinState.LOW);
        }

        if(status.getOutput().getIndicatorRightBack()) {
            indicatorRightBackDigitalOutput.setState(PinState.HIGH);
        }else {
            indicatorRightBackDigitalOutput.setState(PinState.LOW);
        }

        if(status.getOutput().getIndicatorRightFront()) {
            indicatorRightFrontDigitalOutput.setState(PinState.HIGH);
        }else {
            indicatorRightFrontDigitalOutput.setState(PinState.LOW);
        }

        if(status.getOutput().getLowBeam()) {
            lowBeamDigitalOutput.setState(PinState.HIGH);
        }else {
            lowBeamDigitalOutput.setState(PinState.LOW);
        }

        if(status.getOutput().getParkingLight()) {
            parkingLightDigitalOutput.setState(PinState.HIGH);
        }else {
            parkingLightDigitalOutput.setState(PinState.LOW);
        }

        if(status.getOutput().getSignalHorn()) {
            signalHornDigitalOutput.setState(PinState.HIGH);
        }else {
            signalHornDigitalOutput.setState(PinState.LOW);
        }
    }
}
