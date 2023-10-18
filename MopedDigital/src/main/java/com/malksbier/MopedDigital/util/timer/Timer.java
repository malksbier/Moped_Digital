package com.malksbier.MopedDigital.util.timer;

import java.util.function.Function;

public class Timer extends Thread implements ITrigger{
    @Override
    public void trigger() {
        // exec the function given
        // cause functions as arguments are always a mess :)
        throw new UnsupportedOperationException("Unimplemented method 'trigger'");
    }
}
