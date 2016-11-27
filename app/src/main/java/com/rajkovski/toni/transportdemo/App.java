package com.rajkovski.toni.transportdemo;

import android.app.Application;

import com.rajkovski.toni.transportdemo.dagger.AppComponent;
import com.rajkovski.toni.transportdemo.dagger.AppModule;
import com.rajkovski.toni.transportdemo.dagger.DaggerAppComponent;

/**
 * Application object.
 */

public class App extends Application{

  private static App instance;
  private AppComponent appComponent;

  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;

    appComponent = DaggerAppComponent.builder()
      .appModule(new AppModule())
      .build();
  }

  public static App getInstance() {
    return instance;
  }

  public AppComponent getAppComponent() {
    return appComponent;
  }
}
