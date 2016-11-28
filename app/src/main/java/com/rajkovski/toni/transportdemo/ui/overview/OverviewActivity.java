package com.rajkovski.toni.transportdemo.ui.overview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rajkovski.toni.transportdemo.R;
import com.rajkovski.toni.transportdemo.model.TransportRoutes;

public class OverviewActivity extends AppCompatActivity implements IOverviewView {

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
    RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.overview_recycler_view);

    mRecyclerView.setHasFixedSize(true);

    // use a linear layout manager
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    mRecyclerView.setLayoutManager(layoutManager);

    // specify an adapter (see also next example)
    RecyclerView.Adapter adapter = new RoutesAdapter(routes.getRoutes());
    mRecyclerView.setAdapter(adapter);
  }
}
