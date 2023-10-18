package com.malksbier.MopedDigital.data.mopedStatus;

import java.util.Objects;

public class MopedStatus {
    private boolean authorized = false;

    private int speed = 0;
    private int rpm = 0;
    
    private boolean hazardLights = false;
    private String fault = "";

    private MopedStatusInput input;
    private MopedStatusOutput output;

    public MopedStatus() {
        input =  new MopedStatusInput();
        output = new MopedStatusOutput();
    }

    public MopedStatus(boolean authorized, int speed, int rpm, boolean hazardLights, String fault, MopedStatusInput input, MopedStatusOutput output) {
        this.authorized = authorized;
        this.speed = speed;
        this.rpm = rpm;
        this.hazardLights = hazardLights;
        this.fault = fault;
        this.input = input;
        this.output = output;
    }

    public boolean isAuthorized() {
        return this.authorized;
    }

    public boolean getAuthorized() {
        return this.authorized;
    }

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getRpm() {
        return this.rpm;
    }

    public void setRpm(int rpm) {
        this.rpm = rpm;
    }

    public boolean isHazardLights() {
        return this.hazardLights;
    }

    public boolean getHazardLights() {
        return this.hazardLights;
    }

    public void setHazardLights(boolean hazardLights) {
        this.hazardLights = hazardLights;
    }

    public String getFault() {
        return this.fault;
    }

    public void setFault(String fault) {
        this.fault = fault;
    }

    public MopedStatusInput getInput() {
        return this.input;
    }

    public void setInput(MopedStatusInput input) {
        this.input = input;
    }

    public MopedStatusOutput getOutput() {
        return this.output;
    }

    public void setOutput(MopedStatusOutput output) {
        this.output = output;
    }

    public MopedStatus authorized(boolean authorized) {
        setAuthorized(authorized);
        return this;
    }

    public MopedStatus speed(int speed) {
        setSpeed(speed);
        return this;
    }

    public MopedStatus rpm(int rpm) {
        setRpm(rpm);
        return this;
    }

    public MopedStatus hazardLights(boolean hazardLights) {
        setHazardLights(hazardLights);
        return this;
    }

    public MopedStatus fault(String fault) {
        setFault(fault);
        return this;
    }

    public MopedStatus input(MopedStatusInput input) {
        setInput(input);
        return this;
    }

    public MopedStatus output(MopedStatusOutput output) {
        setOutput(output);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof MopedStatus)) {
            return false;
        }
        MopedStatus mopedStatus = (MopedStatus) o;
        return authorized == mopedStatus.authorized && speed == mopedStatus.speed && rpm == mopedStatus.rpm && hazardLights == mopedStatus.hazardLights && Objects.equals(fault, mopedStatus.fault) && Objects.equals(input, mopedStatus.input) && Objects.equals(output, mopedStatus.output);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorized, speed, rpm, hazardLights, fault, input, output);
    }

    @Override
    public String toString() {
        return "{" +
            " authorized='" + isAuthorized() + "'" +
            ", speed='" + getSpeed() + "'" +
            ", rpm='" + getRpm() + "'" +
            ", hazardLights='" + isHazardLights() + "'" +
            ", fault='" + getFault() + "'" +
            ", input='" + getInput() + "'" +
            ", output='" + getOutput() + "'" +
            "}";
    }
}
