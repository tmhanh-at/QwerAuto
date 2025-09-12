package com.qwer.utils;

import org.apache.commons.logging.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerManager {
	
    private static final Logger logger = LogManager.getLogger(Log.class);

    public static void info(String message) {
        logger.info(message);
    }
  
	public static void warn(String message) {
        logger.warn(message);
    }

    public static void error(String message) {
        logger.error(message);
    }

    public static void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    public static void debug(String message) {
        logger.debug(message);
    }

    public static void fatal(String message) {
        logger.fatal(message);
    }

}
