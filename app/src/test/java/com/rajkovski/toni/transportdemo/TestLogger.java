package com.rajkovski.toni.transportdemo;

import com.rajkovski.toni.transportdemo.logger.ILoggingComponent;

/**
 * Replacement for the default android logger.
 * Useful for JUnit tests.
 */
public class TestLogger implements ILoggingComponent {

  @Override
  public int v(String tag, String msg) {
    return 0;
  }

  @Override
  public int v(String tag, String msg, Throwable tr) {
    return 0;
  }

  @Override
  public int d(String tag, String msg) {
    return 0;
  }

  @Override
  public int d(String tag, String msg, Throwable tr) {
    return 0;
  }

  @Override
  public int i(String tag, String msg) {
    return 0;
  }

  @Override
  public int i(String tag, String msg, Throwable tr) {
    return 0;
  }

  @Override
  public int w(String tag, String msg) {
    return 0;
  }

  @Override
  public int w(String tag, String msg, Throwable tr) {
    return 0;
  }

  @Override
  public int w(String tag, Throwable tr) {
    return 0;
  }

  @Override
  public int e(String tag, String msg) {
    return 0;
  }

  @Override
  public int e(String tag, String msg, Throwable tr) {
    return 0;
  }

  @Override
  public int wtf(String tag, String msg) {
    return 0;
  }

  @Override
  public int wtf(String tag, Throwable tr) {
    return 0;
  }
}
