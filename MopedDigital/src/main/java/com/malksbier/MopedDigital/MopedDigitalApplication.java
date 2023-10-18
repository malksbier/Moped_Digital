package com.malksbier.MopedDigital;

import java.io.IOException;

import com.malksbier.MopedDigital.util.FancyPrinter;
import com.malksbier.MopedDigital.util.timer.IntervallUpdateTimer;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.serial.Baud;
import com.pi4j.io.serial.DataBits;
import com.pi4j.io.serial.FlowControl;
import com.pi4j.io.serial.Parity;
import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialDataEvent;
import com.pi4j.io.serial.SerialDataEventListener;
import com.pi4j.io.serial.SerialFactory;
import com.pi4j.io.serial.StopBits;

public class MopedDigitalApplication {
    public static String TAG = "[App] ";
    private static MopedController controller;
    private GpioController gpioController;
    private Serial serialController;

	public static void main(String[] args) {
        FancyPrinter.sayHello();

        controller = new MopedController();

       
        /* 
        // create an instance of the serial communications class
        final Serial serial = SerialFactory.createInstance();

        // create and register the serial data listener
        serial.addListener((SerialDataEventListener) (SerialDataEvent event) -> {
            try {
                //System.out.println("[BYTES RECEIVED (" + event.length() + ")]  " + event.getHexByteString());
                String s = event.getAsciiString();
                System.out.println("ESP wrote" + s);
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        });

        try {
            serial.open(Serial.DEFAULT_COM_PORT, Baud._115200, DataBits._8, 
                        Parity.NONE, StopBits._1, FlowControl.HARDWARE);

            for (;;) {
                // write a formatted string to the serial transmit buffer
                serial.writeln("41,5500,1,1,1,1,;");

                // wait 1 second before continuing
                try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				serial.writeln("61,1500,0,0,0,0,;");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }
        catch(IllegalStateException|IOException ex) {
            System.err.println(ex.getMessage());
        }
        */
	}

}
