package com.rajkovski.toni.transportdemo;

import com.rajkovski.toni.transportdemo.dagger.AppComponent;
import com.rajkovski.toni.transportdemo.dagger.AppModule;
import com.rajkovski.toni.transportdemo.ui.main.MainActivity;
import com.rajkovski.toni.transportdemo.ui.overview.OverviewActivityTest;
import com.rajkovski.toni.transportdemo.ui.overview.RoutesAdapter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TestAppModule.class})
public interface TestAppComponent extends AppComponent {

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

  /**
   * Injects the members into {@link OverviewActivityTest}
   *
   * @param overviewActivityTest the {@link OverviewActivityTest}
   */
  void inject(OverviewActivityTest overviewActivityTest);

}