package com.malksbier.MopedDigital.io.bus;
import java.io.IOException;
import java.util.ArrayList;

import com.malksbier.MopedDigital.data.Command;
import com.malksbier.MopedDigital.data.mopedStatus.MopedStatus;
import com.pi4j.io.serial.Baud;
import com.pi4j.io.serial.DataBits;
import com.pi4j.io.serial.FlowControl;
import com.pi4j.io.serial.Parity;
import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialDataEvent;
import com.pi4j.io.serial.SerialDataEventListener;
import com.pi4j.io.serial.StopBits;

public class SerialHelper {
    public static String TAG = "[SerialHelper] ";
    private Serial serial;
    private Command oldCommand;

    private ArrayList<Command> commands;

    public SerialHelper(Serial serial) {
        this.serial = serial;

        this.oldCommand = new Command();
        commands = new ArrayList<>();

        serial.addListener((SerialDataEventListener) (SerialDataEvent event) -> {
            try {
                String s = event.getAsciiString();
                System.out.println(TAG + "ESP wrote" + s);

                commands.add(Command.fromString(s));

            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        });

        try {
            serial.open(Serial.DEFAULT_COM_PORT, Baud._115200, DataBits._8, Parity.NONE, StopBits._1, FlowControl.HARDWARE);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    public void write(Command command) {
        write(command.buildCommand());
    }
    protected void write(String command) {
        System.out.println(TAG + "printing to DEFAULT_COM_PORT at 115200 baud: " + command);
        try {
            serial.writeln(command);
        }
        catch(IllegalStateException|IOException ex) {
            System.err.println(TAG + ex.getMessage());
        }
    }

    public MopedStatus analyseSignals(MopedStatus mopedStatus) {
        // TODO
        for(int i=0;i<commands.size();i++) {
            String command = commands.get(i).buildCommand().replaceAll(";", "");
            String[] tasks = command.split(",");
            for(int j=0;j<tasks.length;j++) {
                int speed = 0;
                int rpm = 0;
                try {
                    switch (j) {
                    case 0: speed = Integer.parseInt(tasks[j]);
                            break;
                    case 1: rpm = Integer.parseInt(tasks[j]);
                            break;
                    }
                } catch (Exception e) {
                    // skip command
                }
                mopedStatus.setSpeed(speed);
                mopedStatus.setRpm(rpm);
            
            }
        }
        return mopedStatus;
    }


    public void updateSignals(MopedStatus mopedStatus) {
        String rawCommand = "";

        //System.out.println(mopedStatus.toString());

        rawCommand += mopedStatus.getSpeed() + ",";
        rawCommand += mopedStatus.getRpm() + ",";
        if(mopedStatus.getOutput().isIndicatorLeft()) {
            rawCommand += "1" + ",";
        } else {
            rawCommand += "0" + ",";
        }
        if(mopedStatus.getOutput().isIndicatorRight()) {
            rawCommand += "1" + ",";
        } else {
            rawCommand += "0" + ",";
        }
        if(mopedStatus.getOutput().getHighBeam()) {
            rawCommand += "1" + ",";
        } else {
            rawCommand += "0" + ",";
        }
        if(mopedStatus.getHazardLights()) {
            rawCommand += "1" + ",";
        } else {
            rawCommand += "0" + ",";
        }

        Command toEspCommand = Command.fromString(rawCommand);

        if(! toEspCommand.equals(oldCommand)) {
            System.out.println(TAG + "send to esp: " + toEspCommand);
            oldCommand = toEspCommand;
            write(toEspCommand);
        }
        

        return;
    }
    
}
