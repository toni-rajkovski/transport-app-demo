package com.rajkovski.toni.transportdemo.dagger;

import com.rajkovski.toni.transportdemo.ui.main.MainActivity;
import com.rajkovski.toni.transportdemo.ui.overview.RoutesAdapter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

  /**
   * Injects the members into {@link MainActivity}.
   *
   * @param activity the {@link MainActivity}
   */
  void inject(MainActivity activity);

  /**
   * Injects the members into {@link RoutesAdapter}
   *
   * @param routesAdapter the {@link RoutesAdapter}
   */
  void inject(RoutesAdapter routesAdapter);

}