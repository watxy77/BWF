package com.bwf.core.bootstrap;

public interface Lifecycle {
    void start();

    void stop();

    boolean isRunning();
}
