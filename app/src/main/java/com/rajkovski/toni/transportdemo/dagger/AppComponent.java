package com.rajkovski.toni.transportdemo.dagger;

import com.rajkovski.toni.transportdemo.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

  void inject(MainActivity activity);

}