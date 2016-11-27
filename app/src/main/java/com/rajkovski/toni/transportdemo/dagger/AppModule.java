package com.rajkovski.toni.transportdemo.dagger;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rajkovski.toni.transportdemo.App;
import com.rajkovski.toni.transportdemo.services.MainService;
import com.rajkovski.toni.transportdemo.services.loader.AssetsDataLoader;
import com.rajkovski.toni.transportdemo.services.loader.IDataLoader;
import com.rajkovski.toni.transportdemo.services.parser.GsonDataParser;
import com.rajkovski.toni.transportdemo.services.parser.IDataParser;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

  @Provides
  @Singleton
  Application provideApplication() {
    return App.getInstance();
  }


  @Provides
  @Singleton
  IDataParser provideDataParser(Gson gson) {
    return new GsonDataParser(gson);
  }

  @Provides
  @Singleton
  Gson provideGson() {
    GsonBuilder gsonBuilder = new GsonBuilder();
    return gsonBuilder.create();
  }

  @Provides
  @Singleton
  IDataLoader provideDataLoader(Application application) {
    return new AssetsDataLoader(application);
  }

  @Provides
  @Singleton
  MainService provideMainService(IDataLoader dataLoader, IDataParser dataParser) {
    return new MainService(dataLoader, dataParser);
  }
}