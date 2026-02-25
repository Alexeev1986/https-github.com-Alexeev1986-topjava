package ru.javawebinar.topjava.util;

import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.logging.Level;

public class JULLoggingInitializer {
    static {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        java.util.logging.Logger.getLogger("org.postgresql").setLevel(Level.FINE);
    }

}
