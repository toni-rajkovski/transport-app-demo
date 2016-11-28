package com.rajkovski.toni.transportdemo.ui.overview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.rajkovski.toni.transportdemo.App;
import com.rajkovski.toni.transportdemo.R;
import com.rajkovski.toni.transportdemo.logger.Logger;
import com.rajkovski.toni.transportdemo.model.TransportRoutes;
import com.rajkovski.toni.transportdemo.services.MainService;
import com.rajkovski.toni.transportdemo.services.svg.SvgService;
import com.scand.svg.SVGHelper;

import java.io.IOException;

import javax.inject.Inject;

import rx.Subscriber;

public class OverviewActivity extends AppCompatActivity implements IOverviewView {

  private static final String LOG_TAG = "OverviewActivity";

  private IOverviewPresenter overviewPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    overviewPresenter = new OverviewPresenter(this, this);
  }

  @Override
  protected void onResume() {
    super.onResume();
    overviewPresenter.fetchData(getIntent());
  }

  @Override
  public void onRoutesDisplay(TransportRoutes routes) {

  }
}
