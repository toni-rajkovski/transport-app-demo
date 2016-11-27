package com.rajkovski.toni.transportdemo.logger;

/**
 * Defines the rules for logging.
 * Basically follows the {@link android.util.Log} methods.
 */
public interface ILoggingComponent {

  int v(String tag, String msg);

  int v(String tag, String msg, Throwable tr);

  int d(String tag, String msg);

  int d(String tag, String msg, Throwable tr);

  int i(String tag, String msg);

  int i(String tag, String msg, Throwable tr);

  int w(String tag, String msg);

  int w(String tag, String msg, Throwable tr);

  int w(String tag, Throwable tr);

  int e(String tag, String msg);

  int e(String tag, String msg, Throwable tr);

  int wtf(String tag, String msg);

  int wtf(String tag, Throwable tr);

}
