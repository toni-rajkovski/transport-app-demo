package com.rajkovski.toni.transportdemo.ui.overview;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.gson.Gson;
import com.rajkovski.toni.transportdemo.App;
import com.rajkovski.toni.transportdemo.DaggerTestAppComponent;
import com.rajkovski.toni.transportdemo.R;
import com.rajkovski.toni.transportdemo.RecyclerViewMatcher;
import com.rajkovski.toni.transportdemo.TestAppComponent;
import com.rajkovski.toni.transportdemo.TestAppModule;
import com.rajkovski.toni.transportdemo.model.TransportRoutes;
import com.rajkovski.toni.transportdemo.services.parser.GsonDataParser;
import com.rajkovski.toni.transportdemo.services.parser.IDataParser;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumentation test for {@link OverviewActivity}.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class OverviewActivityTest {

  @Rule
  public ActivityTestRule<OverviewActivity> rule = new ActivityTestRule<>(
    OverviewActivity.class, true, false);

  @Inject
  IDataParser parser;

  @Before
  public void prepare() {
    TestAppComponent testAppComponent =
      DaggerTestAppComponent.builder().testAppModule(new TestAppModule()).build();
    App.getInstance().replaceAppCompoment(testAppComponent);

    testAppComponent.inject(this);
  }

  @Test
  public void displayRoutes_routes_displayed() throws Exception {
    // starts the activity with the routes
    startActivityWithIntent(loadTestRoutes());

    // check the data on the screen
    onView(withRecyclerView(R.id.overview_recycler_view).atPositionOnView(0, R.id.overview_route_cost))
      .check(matches(withText("EUR 270")));
    onView(withRecyclerView(R.id.overview_recycler_view).atPositionOnView(0, R.id.overview_route_type))
      .check(matches(withText("Public Transport")));
    onView(withRecyclerView(R.id.overview_recycler_view).atPositionInViewSegments(0, 0, R.id.overview_route_segment_name))
      .check(matches(withText("")));
    onView(withRecyclerView(R.id.overview_recycler_view).atPositionInViewSegments(0, 1, R.id.overview_route_segment_name))
      .check(matches(withText("U2")));

    onView(withRecyclerView(R.id.overview_recycler_view).atPositionOnView(1, R.id.overview_route_cost))
      .check(matches(withText("EUR 270")));
    onView(withRecyclerView(R.id.overview_recycler_view).atPositionOnView(1, R.id.overview_route_type))
      .check(matches(withText("Public Transport")));
    onView(withRecyclerView(R.id.overview_recycler_view).atPositionInViewSegments(1, 0, R.id.overview_route_segment_name))
      .check(matches(withText("")));
    onView(withRecyclerView(R.id.overview_recycler_view).atPositionInViewSegments(1, 1, R.id.overview_route_segment_name))
      .check(matches(withText("142")));
    onView(withRecyclerView(R.id.overview_recycler_view).atPositionInViewSegments(1, 2, R.id.overview_route_segment_name))
      .check(matches(withText("")));
    onView(withRecyclerView(R.id.overview_recycler_view).atPositionInViewSegments(1, 3, R.id.overview_route_segment_name))
      .check(matches(withText("M41")));

    onView(withRecyclerView(R.id.overview_recycler_view).atPositionOnView(2, R.id.overview_route_cost))
      .check(matches(withText("EUR 270")));
    onView(withRecyclerView(R.id.overview_recycler_view).atPositionOnView(2, R.id.overview_route_type))
      .check(matches(withText("Public Transport")));

    onView(withRecyclerView(R.id.overview_recycler_view).atPositionOnView(3, R.id.overview_route_cost))
      .check(matches(withText("EUR 578")));
    onView(withRecyclerView(R.id.overview_recycler_view).atPositionOnView(3, R.id.overview_route_type))
      .check(matches(withText("Car Sharing")));

  }

  @Test
  public void displayRoutes_error_no_routes_found() throws Exception {
    // starts the activity with empty routes
    startActivityWithIntent(new TransportRoutes());

    // check the error message
    onView(withId(R.id.error_view)).check(matches(isDisplayed()));
    onView(withId(R.id.error_text)).check(matches(withText(R.string.error_no_routes_found)));
  }

  @Test
  public void displayRoutes_error_problem_with_server() throws Exception {
    // starts the activity with empty routes
    startActivityWithIntent(null);

    // check the error message
    onView(withId(R.id.error_view)).check(matches(isDisplayed()));
    onView(withId(R.id.error_text)).check(matches(withText(R.string.error_problem_displaying_routes)));
  }

  private void startActivityWithIntent(TransportRoutes routes) {
    Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    Intent result = new Intent(targetContext, OverviewActivity.class);
    if (routes != null) {
      result.putExtra(OverviewPresenter.INTENT_KEY_ROUTES, routes);
    }
    rule.launchActivity(result);
  }

  private TransportRoutes loadTestRoutes() throws IOException {
    Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    InputStream inputStream = targetContext.getResources().getAssets().open("data.json");

    return parser.parseData(inputStream);
  }

  private static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
    return new RecyclerViewMatcher(recyclerViewId);
  }
}
