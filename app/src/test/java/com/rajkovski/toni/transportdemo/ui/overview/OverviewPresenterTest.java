package com.rajkovski.toni.transportdemo.ui.overview;

import android.content.Context;
import android.content.Intent;

import com.rajkovski.toni.transportdemo.R;
import com.rajkovski.toni.transportdemo.TestBuildConfig;
import com.rajkovski.toni.transportdemo.model.Route;
import com.rajkovski.toni.transportdemo.model.TransportRoutes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Tests the {@link OverviewPresenter}.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = TestBuildConfig.class, sdk = 21, manifest = Config.NONE)
public class OverviewPresenterTest {

  @Test
  public void fetchData_success_good_intent() {
    //given
    IOverviewView mockView = mock(IOverviewView.class);
    Context mockContext = mock(Context.class);
    OverviewPresenter presenter = new OverviewPresenter(mockContext, mockView);
    Intent intent = new Intent();
    TransportRoutes transportRoutes = new TransportRoutes();
    // has at least one route
    transportRoutes.getRoutes().add(new Route());
    intent.putExtra(OverviewPresenter.INTENT_KEY_ROUTES, transportRoutes);

    //when
    presenter.fetchData(intent);

    //then
    verify(mockView).onRoutesDisplay(transportRoutes);
  }

  @Test
  public void fetchData_error_no_routes_found() {
    //given
    IOverviewView mockView = mock(IOverviewView.class);
    Context mockContext = mock(Context.class);
    OverviewPresenter presenter = new OverviewPresenter(mockContext, mockView);
    Intent intent = new Intent();
    TransportRoutes transportRoutes = new TransportRoutes();
    intent.putExtra(OverviewPresenter.INTENT_KEY_ROUTES, transportRoutes);

    //when
    presenter.fetchData(intent);

    //then
    verify(mockView).onErrorDisplay(R.string.error_no_routes_found);
  }

  @Test
  public void fetchData_error_intent_without_extras() {
    //given
    IOverviewView mockView = mock(IOverviewView.class);
    Context mockContext = mock(Context.class);
    OverviewPresenter presenter = new OverviewPresenter(mockContext, mockView);
    Intent intent = new Intent();

    //when
    presenter.fetchData(intent);

    //then
    verify(mockView).onErrorDisplay(R.string.error_problem_displaying_routes);
  }

}
