package com.bwf.core.support;

import com.bwf.common.annotation.bootstrap.annotation.Nullable;
import com.bwf.core.bootstrap.ApplicationContext;
import com.bwf.core.context.ConfigurableApplicationContext;
import com.bwf.core.eventbus.BWFEventMessageBus;
import com.bwf.core.exception.BeansException;

public class AbstractApplicationContext implements ConfigurableApplicationContext {
    private final Object startupShutdownMonitor = new Object();
    @Nullable
    private Thread shutdownHook;
    static {

    }

    @Override
    public void setId(String id) {

    }

    @Override
    public void setParent(ApplicationContext parent) {

    }

    @Override
    public void setClassLoader(ClassLoader classLoader) {

    }

    @Override
    public void refresh() throws IllegalStateException {

    }

    @Override
    public void registerShutdownHook() {

    }

    @Override
    public void close() {
        synchronized (this.startupShutdownMonitor) {
//            doClose();
            // If we registered a JVM shutdown hook, we don't need it anymore now:
            // We've already explicitly closed the context.
            if (this.shutdownHook != null) {
                try {
                    Runtime.getRuntime().removeShutdownHook(this.shutdownHook);
                }
                catch (IllegalStateException ex) {
                    // ignore - VM is already shutting down
                }
            }
        }
    }

    @Override
    public void destroy() {
        close();
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getApplicationName() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return null;
    }

    @Override
    public long getStartupDate() {
        return 0;
    }

    @Override
    public ApplicationContext getParent() {
        return null;
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return null;
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return null;
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return null;
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return null;
    }

    @Override
    public boolean containsBean(String name) {
        return false;
    }
}
