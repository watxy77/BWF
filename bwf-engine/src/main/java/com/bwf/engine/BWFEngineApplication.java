package com.bwf.engine;

import com.bwf.common.annotation.bootstrap.annotation.BWFApplication;
import com.bwf.common.annotation.bootstrap.annotation.BWFGlobalConfigScan;
import com.bwf.core.bootstrap.BWFApplicationContext;

@BWFApplication
@BWFGlobalConfigScan({"engine.yml"})
public class BWFEngineApplication {
    public static void main(String[] args) {
        BWFApplicationContext.run(BWFEngineApplication.class, args);
    }
}
