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

  }

  protected void createAppComponent() {
    appComponent = DaggerAppComponent.builder()
      .appModule(new AppModule())
      .build();
  }

  /**
   * Instance of the application.
   *
   * @return App
   */
  public static App getInstance() {
    return instance;
  }

  /**
   * Dagger component for injection.
   *
   * @return AppComponent
   */
  public AppComponent getAppComponent() {
    if (appComponent == null) {
      createAppComponent();
    }
    return appComponent;
  }

  public void replaceAppCompoment(AppComponent appComponent) {
    this.appComponent = appComponent;

  }
}
