package com.bwf.core.bootstrap;

import com.bwf.core.bootstrap.utils.StartupInfoLogger;
import com.bwf.core.io.ResourceLoader;

import java.time.Duration;

/**
 * @Author bjweijiannan
 * @description
 */
public class BWFApplicationContext {
    protected Class<?> mainApplicationClass;
    public BWFApplicationContext(Class<?>... primarySources) {
        this((ResourceLoader)null, primarySources);
    }
    public BWFApplicationContext(ResourceLoader resourceLoader, Class<?>... primarySources) {

    }

    public static ConfigurableApplicationContext run(Class<?> primarySource, String... args) {
        return run(new Class[]{primarySource}, args);
    }

    public static ConfigurableApplicationContext run(Class<?>[] primarySources, String[] args) {
        return (new BWFApplicationContext(primarySources)).run(args);
    }
    public ConfigurableApplicationContext run(String... args) {
        long startTime = System.nanoTime();
        ConfigurableApplicationContext context = null;
        try{

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //启动所需时间
            Duration timeTakenToStartup = Duration.ofNanos(System.nanoTime() - startTime);
            (new StartupInfoLogger(this.mainApplicationClass)).logStarted(timeTakenToStartup);
        }
        return context;
    }
}
