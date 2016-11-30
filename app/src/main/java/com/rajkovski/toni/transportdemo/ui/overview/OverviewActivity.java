package com.rajkovski.toni.transportdemo.ui.overview;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rajkovski.toni.transportdemo.R;
import com.rajkovski.toni.transportdemo.model.Route;
import com.rajkovski.toni.transportdemo.model.TransportRoutes;
import com.rajkovski.toni.transportdemo.ui.AbstractBaseActivity;

import rx.functions.Action1;

public class OverviewActivity extends AbstractBaseActivity implements IOverviewView {

  private static final String LOG_TAG = "OverviewActivity";

  private IOverviewPresenter overviewPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.overview_activity);

    overviewPresenter = new OverviewPresenter(this, this);
  }

  @Override
  protected void onResume() {
    super.onResume();
    overviewPresenter.fetchData(getIntent());
  }

  @Override
  public void onRoutesDisplay(TransportRoutes routes) {
    removeError();

    RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.overview_recycler_view);

    mRecyclerView.setHasFixedSize(true);

    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    mRecyclerView.setLayoutManager(layoutManager);

    RoutesAdapter adapter = new RoutesAdapter(this, routes);
    mRecyclerView.setAdapter(adapter);

    adapter.getClicksObservable().subscribe(new Action1<Route>() {
      @Override
      public void call(Route route) {
        overviewPresenter.onRouteClick(route);
      }
    });

  }

  @Override
  public void onErrorDisplay(int errorId) {
    displayError(getString(errorId));
  }
}
