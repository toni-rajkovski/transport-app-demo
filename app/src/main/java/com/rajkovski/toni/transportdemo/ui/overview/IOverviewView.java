package com.rajkovski.toni.transportdemo.ui.overview;

import com.rajkovski.toni.transportdemo.model.TransportRoutes;

/**
 * Displays the routes.
 */
public interface IOverviewView {

  /**
   * Displays the routes.
   *
   * @param routes the routes to be displayed.
   */
  void onRoutesDisplay(TransportRoutes routes);

  /**
   * Display error.
   *
   * @param errorId error to be dipslayed
   */
  void onErrorDisplay(int errorId);

}
