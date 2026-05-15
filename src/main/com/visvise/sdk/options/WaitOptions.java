package com.visvise.sdk.options;

/**
 * WaitOptions represents the options for waiting for model completion
 */
public class WaitOptions {
    private double interval = 2.0;
    private int timeout = 600;

    public WaitOptions() {
    }

    public double getInterval() {
        return interval;
    }

    public WaitOptions setInterval(double interval) {
        this.interval = interval;
        return this;
    }

    public int getTimeout() {
        return timeout;
    }

    public WaitOptions setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public long getIntervalMillis() {
        return (long) (interval * 1000);
    }

    public static WaitOptions defaults() {
        return new WaitOptions();
    }

    public static WaitOptions create() {
        return new WaitOptions();
    }
}
