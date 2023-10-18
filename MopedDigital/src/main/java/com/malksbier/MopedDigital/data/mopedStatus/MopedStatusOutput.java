package com.malksbier.MopedDigital.data.mopedStatus;

import java.util.Objects;

public class MopedStatusOutput {
    private boolean IndicatorLeftFront = false; 
    private boolean IndicatorLeftBack = false;
    private boolean IndicatorRightFront = false;
    private boolean IndicatorRightBack = false;
    
    private boolean ignition = false;
    private boolean signalHorn = false;

    private boolean highBeam = false;
    private boolean lowBeam = false;
    private boolean parkingLight = false;
    private boolean backLight = false;
    private boolean breakingLight = false;


    public MopedStatusOutput() {
    }

    public MopedStatusOutput(boolean IndicatorLeftFront, boolean IndicatorLeftBack, boolean IndicatorRightFront, boolean IndicatorRightBack, boolean ignition, boolean signalHorn, boolean highBeam, boolean lowBeam, boolean parkingLight, boolean backLight, boolean breakingLight) {
        this.IndicatorLeftFront = IndicatorLeftFront;
        this.IndicatorLeftBack = IndicatorLeftBack;
        this.IndicatorRightFront = IndicatorRightFront;
        this.IndicatorRightBack = IndicatorRightBack;
        this.ignition = ignition;
        this.signalHorn = signalHorn;
        this.highBeam = highBeam;
        this.lowBeam = lowBeam;
        this.parkingLight = parkingLight;
        this.backLight = backLight;
        this.breakingLight = breakingLight;
    }

    public void setIndicatorLeft(boolean val) {
        setIndicatorLeftBack(val);
        setIndicatorLeftFront(val);
    }

    public boolean isIndicatorLeft() {
        return getIndicatorLeftBack() || getIndicatorLeftFront();
    }
    public boolean isIndicatorRight() {
        return getIndicatorRightBack() || getIndicatorRightFront();
    }

    public void setIndicatorRight(boolean val) {
        setIndicatorRightBack(val);
        setIndicatorRightFront(val);
    }

    public boolean isIndicatorLeftFront() {
        return this.IndicatorLeftFront;
    }

    public boolean getIndicatorLeftFront() {
        return this.IndicatorLeftFront;
    }

    public void setIndicatorLeftFront(boolean IndicatorLeftFront) {
        this.IndicatorLeftFront = IndicatorLeftFront;
    }

    public boolean isIndicatorLeftBack() {
        return this.IndicatorLeftBack;
    }

    public boolean getIndicatorLeftBack() {
        return this.IndicatorLeftBack;
    }

    public void setIndicatorLeftBack(boolean IndicatorLeftBack) {
        this.IndicatorLeftBack = IndicatorLeftBack;
    }

    public boolean isIndicatorRightFront() {
        return this.IndicatorRightFront;
    }

    public boolean getIndicatorRightFront() {
        return this.IndicatorRightFront;
    }

    public void setIndicatorRightFront(boolean IndicatorRightFront) {
        this.IndicatorRightFront = IndicatorRightFront;
    }

    public boolean isIndicatorRightBack() {
        return this.IndicatorRightBack;
    }

    public boolean getIndicatorRightBack() {
        return this.IndicatorRightBack;
    }

    public void setIndicatorRightBack(boolean IndicatorRightBack) {
        this.IndicatorRightBack = IndicatorRightBack;
    }

    public boolean isIgnition() {
        return this.ignition;
    }

    public boolean getIgnition() {
        return this.ignition;
    }

    public void setIgnition(boolean ignition) {
        this.ignition = ignition;
    }

    public boolean isSignalHorn() {
        return this.signalHorn;
    }

    public boolean getSignalHorn() {
        return this.signalHorn;
    }

    public void setSignalHorn(boolean signalHorn) {
        this.signalHorn = signalHorn;
    }

    public boolean isHighBeam() {
        return this.highBeam;
    }

    public boolean getHighBeam() {
        return this.highBeam;
    }

    public void setHighBeam(boolean highBeam) {
        this.highBeam = highBeam;
    }

    public boolean isLowBeam() {
        return this.lowBeam;
    }

    public boolean getLowBeam() {
        return this.lowBeam;
    }

    public void setLowBeam(boolean lowBeam) {
        this.lowBeam = lowBeam;
    }

    public boolean isParkingLight() {
        return this.parkingLight;
    }

    public boolean getParkingLight() {
        return this.parkingLight;
    }

    public void setParkingLight(boolean parkingLight) {
        this.parkingLight = parkingLight;
    }

    public boolean isBackLight() {
        return this.backLight;
    }

    public boolean getBackLight() {
        return this.backLight;
    }

    public void setBackLight(boolean backLight) {
        this.backLight = backLight;
    }

    public boolean isBreakingLight() {
        return this.breakingLight;
    }

    public boolean getBreakingLight() {
        return this.breakingLight;
    }

    public void setBreakingLight(boolean breakingLight) {
        this.breakingLight = breakingLight;
    }

    public MopedStatusOutput IndicatorLeftFront(boolean IndicatorLeftFront) {
        setIndicatorLeftFront(IndicatorLeftFront);
        return this;
    }

    public MopedStatusOutput IndicatorLeftBack(boolean IndicatorLeftBack) {
        setIndicatorLeftBack(IndicatorLeftBack);
        return this;
    }

    public MopedStatusOutput IndicatorRightFront(boolean IndicatorRightFront) {
        setIndicatorRightFront(IndicatorRightFront);
        return this;
    }

    public MopedStatusOutput IndicatorRightBack(boolean IndicatorRightBack) {
        setIndicatorRightBack(IndicatorRightBack);
        return this;
    }

    public MopedStatusOutput ignition(boolean ignition) {
        setIgnition(ignition);
        return this;
    }

    public MopedStatusOutput signalHorn(boolean signalHorn) {
        setSignalHorn(signalHorn);
        return this;
    }

    public MopedStatusOutput highBeam(boolean highBeam) {
        setHighBeam(highBeam);
        return this;
    }

    public MopedStatusOutput lowBeam(boolean lowBeam) {
        setLowBeam(lowBeam);
        return this;
    }

    public MopedStatusOutput parkingLight(boolean parkingLight) {
        setParkingLight(parkingLight);
        return this;
    }

    public MopedStatusOutput backLight(boolean backLight) {
        setBackLight(backLight);
        return this;
    }

    public MopedStatusOutput breakingLight(boolean breakingLight) {
        setBreakingLight(breakingLight);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof MopedStatusOutput)) {
            return false;
        }
        MopedStatusOutput mopedStatusOutput = (MopedStatusOutput) o;
        return IndicatorLeftFront == mopedStatusOutput.IndicatorLeftFront && IndicatorLeftBack == mopedStatusOutput.IndicatorLeftBack && IndicatorRightFront == mopedStatusOutput.IndicatorRightFront && IndicatorRightBack == mopedStatusOutput.IndicatorRightBack && ignition == mopedStatusOutput.ignition && signalHorn == mopedStatusOutput.signalHorn && highBeam == mopedStatusOutput.highBeam && lowBeam == mopedStatusOutput.lowBeam && parkingLight == mopedStatusOutput.parkingLight && backLight == mopedStatusOutput.backLight && breakingLight == mopedStatusOutput.breakingLight;
    }

    @Override
    public int hashCode() {
        return Objects.hash(IndicatorLeftFront, IndicatorLeftBack, IndicatorRightFront, IndicatorRightBack, ignition, signalHorn, highBeam, lowBeam, parkingLight, backLight, breakingLight);
    }

    @Override
    public String toString() {
        return "{" +
            " IndicatorLeftFront='" + isIndicatorLeftFront() + "'" +
            ", IndicatorLeftBack='" + isIndicatorLeftBack() + "'" +
            ", IndicatorRightFront='" + isIndicatorRightFront() + "'" +
            ", IndicatorRightBack='" + isIndicatorRightBack() + "'" +
            ", ignition='" + isIgnition() + "'" +
            ", signalHorn='" + isSignalHorn() + "'" +
            ", highBeam='" + isHighBeam() + "'" +
            ", lowBeam='" + isLowBeam() + "'" +
            ", parkingLight='" + isParkingLight() + "'" +
            ", backLight='" + isBackLight() + "'" +
            ", breakingLight='" + isBreakingLight() + "'" +
            "}";
    }
}
