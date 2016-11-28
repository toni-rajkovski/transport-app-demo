package com.rajkovski.toni.transportdemo.ui.overview;

import android.content.Intent;

/**
 * Presenter view for overview screen.
 */
public interface IOverviewPresenter {

  void fetchData(Intent intent);

  void onRouteClick(int position);

}
