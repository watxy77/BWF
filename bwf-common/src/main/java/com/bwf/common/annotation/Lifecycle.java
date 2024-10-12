package com.bwf.common.annotation;

public interface Lifecycle {
    void start();

    void stop();

    boolean isRunning();
}
