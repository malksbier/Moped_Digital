package com.malksbier.MopedDigital.data.mopedStatus;

import java.util.Objects;

public class MopedStatusInput {
    private boolean IndicatorLeft = false;
    private boolean IndicatorRight = false;
    private boolean signalHorn = false;
    private boolean highBeam = false;
    private boolean signalBeam = false;


    public MopedStatusInput() {
    }

    public MopedStatusInput(boolean IndicatorLeft, boolean IndicatorRight, boolean signalHorn, boolean highBeam, boolean signalBeam) {
        this.IndicatorLeft = IndicatorLeft;
        this.IndicatorRight = IndicatorRight;
        this.signalHorn = signalHorn;
        this.highBeam = highBeam;
        this.signalBeam = signalBeam;
    }

    public boolean isIndicatorLeft() {
        return this.IndicatorLeft;
    }

    public boolean getIndicatorLeft() {
        return this.IndicatorLeft;
    }

    public void setIndicatorLeft(boolean IndicatorLeft) {
        this.IndicatorLeft = IndicatorLeft;
    }

    public boolean isIndicatorRight() {
        return this.IndicatorRight;
    }

    public boolean getIndicatorRight() {
        return this.IndicatorRight;
    }

    public void setIndicatorRight(boolean IndicatorRight) {
        this.IndicatorRight = IndicatorRight;
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

    public boolean isSignalBeam() {
        return this.signalBeam;
    }

    public boolean getSignalBeam() {
        return this.signalBeam;
    }

    public void setSignalBeam(boolean signalBeam) {
        this.signalBeam = signalBeam;
    }

    public MopedStatusInput IndicatorLeft(boolean IndicatorLeft) {
        setIndicatorLeft(IndicatorLeft);
        return this;
    }

    public MopedStatusInput IndicatorRight(boolean IndicatorRight) {
        setIndicatorRight(IndicatorRight);
        return this;
    }

    public MopedStatusInput signalHorn(boolean signalHorn) {
        setSignalHorn(signalHorn);
        return this;
    }

    public MopedStatusInput highBeam(boolean highBeam) {
        setHighBeam(highBeam);
        return this;
    }

    public MopedStatusInput signalBeam(boolean signalBeam) {
        setSignalBeam(signalBeam);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof MopedStatusInput)) {
            return false;
        }
        MopedStatusInput mopedStatusInput = (MopedStatusInput) o;
        return IndicatorLeft == mopedStatusInput.IndicatorLeft && IndicatorRight == mopedStatusInput.IndicatorRight && signalHorn == mopedStatusInput.signalHorn && highBeam == mopedStatusInput.highBeam && signalBeam == mopedStatusInput.signalBeam;
    }

    @Override
    public int hashCode() {
        return Objects.hash(IndicatorLeft, IndicatorRight, signalHorn, highBeam, signalBeam);
    }

    @Override
    public String toString() {
        return "{" +
            " IndicatorLeft='" + isIndicatorLeft() + "'" +
            ", IndicatorRight='" + isIndicatorRight() + "'" +
            ", signalHorn='" + isSignalHorn() + "'" +
            ", highBeam='" + isHighBeam() + "'" +
            ", signalBeam='" + isSignalBeam() + "'" +
            "}";
    }    
}
