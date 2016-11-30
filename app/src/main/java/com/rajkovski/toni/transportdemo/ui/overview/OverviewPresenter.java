package com.rajkovski.toni.transportdemo.ui.overview;

import android.content.Context;
import android.content.Intent;

import com.rajkovski.toni.transportdemo.R;
import com.rajkovski.toni.transportdemo.logger.Logger;
import com.rajkovski.toni.transportdemo.model.Route;
import com.rajkovski.toni.transportdemo.model.TransportRoutes;
import com.rajkovski.toni.transportdemo.ui.map.MapActivity;

/**
 * Implementation of {@link IOverviewPresenter}
 */
public class OverviewPresenter implements IOverviewPresenter {

  public static final String INTENT_KEY_ROUTES = "transport_demo.routes";
  private static final String LOG_TAG = "OverviewPresenter";

  private Context context;
  private IOverviewView overviewView;

  public OverviewPresenter(Context context, IOverviewView overviewView) {
    this.context = context;
    this.overviewView = overviewView;
  }

  @Override
  public void fetchData(Intent intent) {
    if (intent != null && intent.hasExtra(INTENT_KEY_ROUTES)) {
      TransportRoutes routes = (TransportRoutes) intent.getSerializableExtra(INTENT_KEY_ROUTES);
      if (routes.getRoutes().size() > 0) {
        overviewView.onRoutesDisplay(routes);
      } else {
        overviewView.onErrorDisplay(R.string.error_no_routes_found);
      }
    } else {
      Logger.w(LOG_TAG, "No routes found in the intent or empty intent");
      overviewView.onErrorDisplay(R.string.error_problem_displaying_routes);
    }
  }

  @Override
  public void onRouteClick(Route route) {
    Intent intent = new Intent(context, MapActivity.class);
    intent.putExtra(MapActivity.INTENT_KEY_ROUTE_FOR_MAP, route);
    context.startActivity(intent);
  }

}
