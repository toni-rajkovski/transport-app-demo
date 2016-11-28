package com.rajkovski.toni.transportdemo.ui.overview;

import android.content.Context;
import android.content.Intent;

import com.rajkovski.toni.transportdemo.model.TransportRoutes;

import java.io.Serializable;

/**
 * Implementation of {@link IOverviewPresenter}
 */
public class OverviewPresenter implements IOverviewPresenter {

  public static final String INTENT_KEY_ROUTES = "transport_demo.routes";

  private Context context;
  private IOverviewView overviewView;

  public OverviewPresenter(Context context, IOverviewView overviewView) {
    this.context = context;
    this.overviewView = overviewView;
  }

  @Override
  public void fetchData(Intent intent) {
    TransportRoutes routes = (TransportRoutes) intent.getSerializableExtra(INTENT_KEY_ROUTES);
    overviewView.onRoutesDisplay(routes);
  }

  @Override
  public void onRouteClick(int position) {
    //TODO take the user to details screen
  }

}
