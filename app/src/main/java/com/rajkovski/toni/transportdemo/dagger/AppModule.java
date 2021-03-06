package com.rajkovski.toni.transportdemo.dagger;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rajkovski.toni.transportdemo.App;
import com.rajkovski.toni.transportdemo.services.MainService;
import com.rajkovski.toni.transportdemo.services.svg.ISvgCache;
import com.rajkovski.toni.transportdemo.services.svg.MemorySvgCache;
import com.rajkovski.toni.transportdemo.services.svg.SvgService;
import com.rajkovski.toni.transportdemo.services.loader.AssetsDataLoader;
import com.rajkovski.toni.transportdemo.services.loader.IDataLoader;
import com.rajkovski.toni.transportdemo.services.parser.GsonDataParser;
import com.rajkovski.toni.transportdemo.services.parser.IDataParser;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

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

  @Provides
  @Singleton
  OkHttpClient provideOkHttpClient() {
    return new OkHttpClient();
  }

  @Provides
  @Singleton
  ISvgCache provideSvgCache() {
    return new MemorySvgCache();
  }

  @Provides
  @Singleton
  SvgService provideSvgService(OkHttpClient httpClient, ISvgCache cache) {
    return new SvgService(httpClient, cache);
  }

}