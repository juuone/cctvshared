package com.homecam.utils;

import android.util.Log;

/** Centralized logging that suppresses verbose details in release builds and avoids sensitive payload logging. */
public final class Logger {
    private static volatile boolean debug;
    private Logger() { }
    public static void initialize(boolean enabled) { debug = enabled; }
    public static void d(String tag, String message) { if (debug) Log.d(tag, sanitize(message)); }
    public static void i(String tag, String message) { Log.i(tag, sanitize(message)); }
    public static void w(String tag, String message) { Log.w(tag, sanitize(message)); }
    public static void e(String tag, String message, Throwable error) { Log.e(tag, sanitize(message), error); }
    private static String sanitize(String message) { return message == null ? "" : message.replaceAll("(?i)(token|session|ice)[^\\s,;]*", "$1-redacted"); }
}
