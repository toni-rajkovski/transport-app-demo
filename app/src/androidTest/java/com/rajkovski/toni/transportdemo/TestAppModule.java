package com.rajkovski.toni.transportdemo;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rajkovski.toni.transportdemo.services.MainService;
import com.rajkovski.toni.transportdemo.services.loader.AssetsDataLoader;
import com.rajkovski.toni.transportdemo.services.loader.IDataLoader;
import com.rajkovski.toni.transportdemo.services.parser.GsonDataParser;
import com.rajkovski.toni.transportdemo.services.parser.IDataParser;
import com.rajkovski.toni.transportdemo.services.svg.ISvgCache;
import com.rajkovski.toni.transportdemo.services.svg.MemorySvgCache;
import com.rajkovski.toni.transportdemo.services.svg.SvgService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public class TestAppModule {

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
    // Dummy cache, it will prevent the network calls
    return new ISvgCache() {
      @Override
      public byte[] getImageByUrl(String url) {
        return new byte[0];
      }

      @Override
      public void putImage(String url, byte[] image) {
      }
    };
  }

  @Provides
  @Singleton
  SvgService provideSvgService(OkHttpClient httpClient, ISvgCache cache) {
    return new SvgService(httpClient, cache);
  }

}