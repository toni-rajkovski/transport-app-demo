package com.rajkovski.toni.transportdemo.ui.overview;

import android.content.Intent;

import com.rajkovski.toni.transportdemo.model.Route;

/**
 * Presenter view for overview screen.
 */
public interface IOverviewPresenter {

  /**
   * Gets the data from the intent and directs it to the view for displaying.
   *
   * @param intent the intent
   */
  void fetchData(Intent intent);

  /**
   * Handles the click on a specific route.
   *
   * @param route the route that is clicked
   */
  void onRouteClick(Route route);

}
