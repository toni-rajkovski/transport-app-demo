package com.rajkovski.toni.transportdemo.logger;

/**
 * Logger that should be used through all application for logging the event.
 */
public class Logger {

  private static ILoggingComponent defaultLogger = new AndroidLogger();

  public static int v(String tag, String msg) {
    return defaultLogger.v(tag, msg);
  }

  public static int v(String tag, String msg, Throwable tr) {
    return defaultLogger.v(tag, msg, tr);
  }

  public static int d(String tag, String msg) {
    return defaultLogger.d(tag, msg);
  }

  public static int d(String tag, String msg, Throwable tr) {
    return defaultLogger.d(tag, msg, tr);
  }

  public static int i(String tag, String msg) {
    return defaultLogger.i(tag, msg);
  }

  public static int i(String tag, String msg, Throwable tr) {
    return defaultLogger.i(tag, msg, tr);
  }

  public static int w(String tag, String msg) {
    return defaultLogger.w(tag, msg);
  }

  public static int w(String tag, String msg, Throwable tr) {
    return defaultLogger.w(tag, msg, tr);
  }

  public static int w(String tag, Throwable tr) {
    return defaultLogger.w(tag, tr);
  }

  public static int e(String tag, String msg) {
    return defaultLogger.e(tag, msg);
  }

  public static int e(String tag, String msg, Throwable tr) {
    return defaultLogger.e(tag, msg, tr);
  }

  public static int wtf(String tag, String msg) {
    return defaultLogger.wtf(tag, msg);
  }

  public static int wtf(String tag, Throwable tr) {
    return defaultLogger.wtf(tag, tr);
  }

  public static void replaceDefaultLogger(ILoggingComponent newLogger) {
    defaultLogger = newLogger;
  }

}
