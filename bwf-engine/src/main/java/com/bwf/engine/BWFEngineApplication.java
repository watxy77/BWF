package com.bwf.engine;

import com.bwf.common.annotation.bootstrap.BWFApplication;
import com.bwf.common.annotation.bootstrap.BWFGlobalConfigScan;
import com.bwf.core.bootstrap.BWFApplicationContext;

@BWFApplication
@BWFGlobalConfigScan({"engine.yml"})
public class BWFEngineApplication {
    public static void main(String[] args) {
        BWFApplicationContext.run(BWFEngineApplication.class, args);
    }
}
