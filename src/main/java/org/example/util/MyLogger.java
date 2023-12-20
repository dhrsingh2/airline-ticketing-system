package org.example.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MyLogger {
    private static MyLogger instance;
    private Logger logger;

    private MyLogger() {
        logger = Logger.getLogger("MyLogger");
    }

    public static synchronized MyLogger getInstance() {
        if (instance == null) {
            instance = new MyLogger();
        }
        return instance;
    }

    public void log(String message) {
        logger.info(message);
    }

    public void logException(String message, Throwable e) {
        logger.log(Level.SEVERE, message, e);
    }
}