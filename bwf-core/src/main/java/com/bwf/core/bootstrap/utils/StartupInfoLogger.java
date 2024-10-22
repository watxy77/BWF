package com.bwf.core.bootstrap.utils;




import lombok.extern.slf4j.Slf4j;

import java.lang.management.ManagementFactory;
import java.time.Duration;
/**
 * @Author bjweijiannan
 * @description
 */
@Slf4j
public class StartupInfoLogger {
    private final Class<?> sourceClass;
    private final static String LINE_CHARACTER = "\n";
    private static StringBuilder BWFComponentBeanMessage = new StringBuilder();
    public StartupInfoLogger(Class<?> sourceClass) {
        this.sourceClass = sourceClass;
    }

    public void logStarted(Duration timeTakenToStartup) {
        log.info(this.getStartedMessage(timeTakenToStartup).toString());
    }
    public void logPlugin(int apiPluginCount, int cachePluginCount, Duration timeTakenToStartup) {
        log.info(this.lodePluginMessage(apiPluginCount, cachePluginCount, timeTakenToStartup).toString());
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

    public static void addBWFComponentBeanMessage(String msg) {
        BWFComponentBeanMessage.append(msg + LINE_CHARACTER);
    }

    public static CharSequence lodeBWFComponentBeanMessage(){
        return BWFComponentBeanMessage;
    }

    private void appendApplicationName(StringBuilder message) {
        String name = this.sourceClass != null ? ClassUtils.getShortName(this.sourceClass) : "application";
        message.append(name);
    }
}
