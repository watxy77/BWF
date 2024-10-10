package com.bwf.core.bootstrap.utils;

import java.lang.management.ManagementFactory;
import java.time.Duration;
/**
 * @Author bjweijiannan
 * @description
 */
public class StartupInfoLogger {
    private final Class<?> sourceClass;
    public StartupInfoLogger(Class<?> sourceClass) {
        this.sourceClass = sourceClass;
    }

    public void logStarted(Duration timeTakenToStartup) {
        System.out.println(this.getStartedMessage(timeTakenToStartup));
    }
    public void logPlugin(int apiPluginCount, int cachePluginCount, Duration timeTakenToStartup) {
        System.out.println(this.lodePluginMessage(apiPluginCount, cachePluginCount, timeTakenToStartup));
    }

    private CharSequence getStartedMessage(Duration timeTakenToStartup) {
        StringBuilder message = new StringBuilder();
        message.append("Started ");
        this.appendApplicationName(message);
        message.append(" in ");
        message.append((double)timeTakenToStartup.toMillis() / 1000.0D);
        message.append(" seconds");

        try {
            double uptime = (double) ManagementFactory.getRuntimeMXBean().getUptime() / 1000.0D;
            message.append(" (JVM running for ").append(uptime).append(")");
        } catch (Throwable var5) {
        }

        return message;
    }
    private CharSequence lodePluginMessage(int apiPluginCount, int cachePluginCount, Duration timeTakenToStartup) {
        StringBuilder message = new StringBuilder();
        message.append("Complete Load Plugin");
        message.append(" in ");
        message.append((double)timeTakenToStartup.toMillis() / 1000.0D);
        message.append(" seconds");
        message.append(" \n");
        message.append("API Plugin Amount ："+ apiPluginCount);
        message.append(" \n");
        message.append("Cache Plugin Amount ："+ cachePluginCount );
        return message;
    }

    private void appendApplicationName(StringBuilder message) {
        String name = this.sourceClass != null ? ClassUtils.getShortName(this.sourceClass) : "application";
        message.append(name);
    }
}
