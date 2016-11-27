package com.rajkovski.toni.transportdemo;

import android.app.Application;

/**
 * Application object.
 */

public class App extends Application{

  private static App instance;

  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;
  }

  public static App getInstance() {
    return instance;
  }
}
