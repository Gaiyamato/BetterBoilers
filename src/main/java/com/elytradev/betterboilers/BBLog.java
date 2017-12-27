package com.elytradev.betterboilers;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MessageFactory;

public class BBLog {

    private static final Logger log = LogManager.getLogger("Correlated");

    // AUTOGENERATED - DO NOT EDIT BY HAND //

    public static void catching(Level level, Throwable t) {
        log.catching(level, t);
    }

    public static void catching(Throwable t) {
        log.catching(t);
    }

    public static void debug(Marker marker, Message msg) {
        log.debug(marker, msg);
    }

    public static void debug(Marker marker, Message msg, Throwable t) {
        log.debug(marker, msg, t);
    }

    public static void debug(Marker marker, Object message) {
        log.debug(marker, message);
    }

    public static void debug(Marker marker, Object message, Throwable t) {
        log.debug(marker, message, t);
    }

    public static void debug(Marker marker, String message) {
        log.debug(marker, message);
    }

    public static void debug(Marker marker, String message, Object... params) {
        log.debug(marker, message, params);
    }

    public static void debug(Marker marker, String message, Throwable t) {
        log.debug(marker, message, t);
    }

    public static void debug(Message msg) {
        log.debug(msg);
    }

    public static void debug(Message msg, Throwable t) {
        log.debug(msg, t);
    }

    public static void debug(Object message) {
        log.debug(message);
    }

    public static void debug(Object message, Throwable t) {
        log.debug(message, t);
    }

    public static void debug(String message) {
        log.debug(message);
    }

    public static void debug(String message, Object... params) {
        log.debug(message, params);
    }

    public static void debug(String message, Throwable t) {
        log.debug(message, t);
    }

    public static void entry() {
        log.entry();
    }

    public static void entry(Object... params) {
        log.entry(params);
    }

    public static void error(Marker marker, Message msg) {
        log.error(marker, msg);
    }

    public static void error(Marker marker, Message msg, Throwable t) {
        log.error(marker, msg, t);
    }

    public static void error(Marker marker, Object message) {
        log.error(marker, message);
    }

    public static void error(Marker marker, Object message, Throwable t) {
        log.error(marker, message, t);
    }

    public static void error(Marker marker, String message) {
        log.error(marker, message);
    }

    public static void error(Marker marker, String message, Object... params) {
        log.error(marker, message, params);
    }

    public static void error(Marker marker, String message, Throwable t) {
        log.error(marker, message, t);
    }

    public static void error(Message msg) {
        log.error(msg);
    }

    public static void error(Message msg, Throwable t) {
        log.error(msg, t);
    }

    public static void error(Object message) {
        log.error(message);
    }

    public static void error(Object message, Throwable t) {
        log.error(message, t);
    }

    public static void error(String message) {
        log.error(message);
    }

    public static void error(String message, Object... params) {
        log.error(message, params);
    }

    public static void error(String message, Throwable t) {
        log.error(message, t);
    }

    public static void exit() {
        log.exit();
    }

    public static <R> R exit(R result) {
        return log.exit(result);
    }

    public static void fatal(Marker marker, Message msg) {
        log.fatal(marker, msg);
    }

    public static void fatal(Marker marker, Message msg, Throwable t) {
        log.fatal(marker, msg, t);
    }

    public static void fatal(Marker marker, Object message) {
        log.fatal(marker, message);
    }

    public static void fatal(Marker marker, Object message, Throwable t) {
        log.fatal(marker, message, t);
    }

    public static void fatal(Marker marker, String message) {
        log.fatal(marker, message);
    }

    public static void fatal(Marker marker, String message, Object... params) {
        log.fatal(marker, message, params);
    }

    public static void fatal(Marker marker, String message, Throwable t) {
        log.fatal(marker, message, t);
    }

    public static void fatal(Message msg) {
        log.fatal(msg);
    }

    public static void fatal(Message msg, Throwable t) {
        log.fatal(msg, t);
    }

    public static void fatal(Object message) {
        log.fatal(message);
    }

    public static void fatal(Object message, Throwable t) {
        log.fatal(message, t);
    }

    public static void fatal(String message) {
        log.fatal(message);
    }

    public static void fatal(String message, Object... params) {
        log.fatal(message, params);
    }

    public static void fatal(String message, Throwable t) {
        log.fatal(message, t);
    }

    public static MessageFactory getMessageFactory() {
        return log.getMessageFactory();
    }

    public static String getName() {
        return log.getName();
    }

    public static void info(Marker marker, Message msg) {
        log.info(marker, msg);
    }

    public static void info(Marker marker, Message msg, Throwable t) {
        log.info(marker, msg, t);
    }

    public static void info(Marker marker, Object message) {
        log.info(marker, message);
    }

    public static void info(Marker marker, Object message, Throwable t) {
        log.info(marker, message, t);
    }

    public static void info(Marker marker, String message) {
        log.info(marker, message);
    }

    public static void info(Marker marker, String message, Object... params) {
        log.info(marker, message, params);
    }

    public static void info(Marker marker, String message, Throwable t) {
        log.info(marker, message, t);
    }

    public static void info(Message msg) {
        log.info(msg);
    }

    public static void info(Message msg, Throwable t) {
        log.info(msg, t);
    }

    public static void info(Object message) {
        log.info(message);
    }

    public static void info(Object message, Throwable t) {
        log.info(message, t);
    }

    public static void info(String message) {
        log.info(message);
    }

    public static void info(String message, Object... params) {
        log.info(message, params);
    }

    public static void info(String message, Throwable t) {
        log.info(message, t);
    }

    public static boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    public static boolean isDebugEnabled(Marker marker) {
        return log.isDebugEnabled(marker);
    }

    public static boolean isEnabled(Level level) {
        return log.isEnabled(level);
    }

    public static boolean isEnabled(Level level, Marker marker) {
        return log.isEnabled(level, marker);
    }

    public static boolean isErrorEnabled() {
        return log.isErrorEnabled();
    }

    public static boolean isErrorEnabled(Marker marker) {
        return log.isErrorEnabled(marker);
    }

    public static boolean isFatalEnabled() {
        return log.isFatalEnabled();
    }

    public static boolean isFatalEnabled(Marker marker) {
        return log.isFatalEnabled(marker);
    }

    public static boolean isInfoEnabled() {
        return log.isInfoEnabled();
    }

    public static boolean isInfoEnabled(Marker marker) {
        return log.isInfoEnabled(marker);
    }

    public static boolean isTraceEnabled() {
        return log.isTraceEnabled();
    }

    public static boolean isTraceEnabled(Marker marker) {
        return log.isTraceEnabled(marker);
    }

    public static boolean isWarnEnabled() {
        return log.isWarnEnabled();
    }

    public static boolean isWarnEnabled(Marker marker) {
        return log.isWarnEnabled(marker);
    }

    public static void log(Level level, Marker marker, Message msg) {
        log.log(level, marker, msg);
    }

    public static void log(Level level, Marker marker, Message msg, Throwable t) {
        log.log(level, marker, msg, t);
    }

    public static void log(Level level, Marker marker, Object message) {
        log.log(level, marker, message);
    }

    public static void log(Level level, Marker marker, Object message, Throwable t) {
        log.log(level, marker, message, t);
    }

    public static void log(Level level, Marker marker, String message) {
        log.log(level, marker, message);
    }

    public static void log(Level level, Marker marker, String message,
                           Object... params) {
        log.log(level, marker, message, params);
    }

    public static void log(Level level, Marker marker, String message, Throwable t) {
        log.log(level, marker, message, t);
    }

    public static void log(Level level, Message msg) {
        log.log(level, msg);
    }

    public static void log(Level level, Message msg, Throwable t) {
        log.log(level, msg, t);
    }

    public static void log(Level level, Object message) {
        log.log(level, message);
    }

    public static void log(Level level, Object message, Throwable t) {
        log.log(level, message, t);
    }

    public static void log(Level level, String message) {
        log.log(level, message);
    }

    public static void log(Level level, String message, Object... params) {
        log.log(level, message, params);
    }

    public static void log(Level level, String message, Throwable t) {
        log.log(level, message, t);
    }

    public static void printf(Level level, Marker marker, String format, Object... params) {
        log.printf(level, marker, format, params);
    }

    public static void printf(Level level, String format, Object... params) {
        log.printf(level, format, params);
    }

    public static <T extends Throwable> T throwing(Level level, T t) {
        return log.throwing(level, t);
    }

    public static <T extends Throwable> T throwing(T t) {
        return log.throwing(t);
    }

    public static void trace(Marker marker, Message msg) {
        log.trace(marker, msg);
    }

    public static void trace(Marker marker, Message msg, Throwable t) {
        log.trace(marker, msg, t);
    }

    public static void trace(Marker marker, Object message) {
        log.trace(marker, message);
    }

    public static void trace(Marker marker, Object message, Throwable t) {
        log.trace(marker, message, t);
    }

    public static void trace(Marker marker, String message) {
        log.trace(marker, message);
    }

    public static void trace(Marker marker, String message, Object... params) {
        log.trace(marker, message, params);
    }

    public static void trace(Marker marker, String message, Throwable t) {
        log.trace(marker, message, t);
    }

    public static void trace(Message msg) {
        log.trace(msg);
    }

    public static void trace(Message msg, Throwable t) {
        log.trace(msg, t);
    }

    public static void trace(Object message) {
        log.trace(message);
    }

    public static void trace(Object message, Throwable t) {
        log.trace(message, t);
    }

    public static void trace(String message) {
        log.trace(message);
    }

    public static void trace(String message, Object... params) {
        log.trace(message, params);
    }

    public static void trace(String message, Throwable t) {
        log.trace(message, t);
    }

    public static void warn(Marker marker, Message msg) {
        log.warn(marker, msg);
    }

    public static void warn(Marker marker, Message msg, Throwable t) {
        log.warn(marker, msg, t);
    }

    public static void warn(Marker marker, Object message) {
        log.warn(marker, message);
    }

    public static void warn(Marker marker, Object message, Throwable t) {
        log.warn(marker, message, t);
    }

    public static void warn(Marker marker, String message) {
        log.warn(marker, message);
    }

    public static void warn(Marker marker, String message, Object... params) {
        log.warn(marker, message, params);
    }

    public static void warn(Marker marker, String message, Throwable t) {
        log.warn(marker, message, t);
    }

    public static void warn(Message msg) {
        log.warn(msg);
    }

    public static void warn(Message msg, Throwable t) {
        log.warn(msg, t);
    }

    public static void warn(Object message) {
        log.warn(message);
    }

    public static void warn(Object message, Throwable t) {
        log.warn(message, t);
    }

    public static void warn(String message) {
        log.warn(message);
    }

    public static void warn(String message, Object... params) {
        log.warn(message, params);
    }

    public static void warn(String message, Throwable t) {
        log.warn(message, t);
    }



}