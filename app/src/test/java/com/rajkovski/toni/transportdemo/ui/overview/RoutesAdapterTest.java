package com.rajkovski.toni.transportdemo.ui.overview;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rajkovski.toni.transportdemo.R;
import com.rajkovski.toni.transportdemo.TestBuildConfig;
import com.rajkovski.toni.transportdemo.model.Price;
import com.rajkovski.toni.transportdemo.model.Route;
import com.rajkovski.toni.transportdemo.model.Segment;
import com.rajkovski.toni.transportdemo.model.Stop;
import com.rajkovski.toni.transportdemo.model.TransportRoutes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link RoutesAdapter}.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = TestBuildConfig.class, sdk = 21, manifest = Config.NONE)
public class RoutesAdapterTest {

  @Test
  public void onBindViewHolder_success() {
    //given
    OverviewActivity activity = Robolectric.buildActivity(OverviewActivity.class).create().get();
    ViewGroup routeView = (ViewGroup) LayoutInflater.from(activity).inflate(
      R.layout.route_item, null);


    TransportRoutes routes = new TransportRoutes();
    Route route = new Route();
    route.setType("public_transport");
    route.setProvider("vbb");
    Price price = new Price();
    price.setAmount(100);
    price.setCurrency("EUR");
    route.setPrice(price);

    Segment segment = new Segment();
    segment.setName("U2");
    segment.setColor("#aaaaaa");
    route.getSegments().add(segment);

    Stop stop1 = new Stop();
    stop1.setDatetime("2015-04-17T13:30:00+0200");
    segment.getStops().add(stop1);

    Stop stop2 = new Stop();
    stop2.setDatetime("2015-04-17T13:38:00+0200");
    segment.getStops().add(stop2);

    routes.getRoutes().add(route);
    RoutesAdapter routesAdapter = new RoutesAdapter(activity, routes);
    RoutesAdapter.ViewHolder viewHolder = routesAdapter.new ViewHolder(routeView);

    //when
    routesAdapter.onBindViewHolder(viewHolder, 0);

    //then
    TextView costView = (TextView) routeView.findViewById(R.id.overview_route_cost);
    TextView typeView = (TextView) routeView.findViewById(R.id.overview_route_type);
    ViewGroup segmentsHolder = (ViewGroup) routeView.findViewById(R.id.overview_route_segments);
    ViewGroup firstSegment = (ViewGroup) segmentsHolder.getChildAt(0);
    TextView segmentNameText = (TextView) firstSegment.findViewById(
      R.id.overview_route_segment_name);
    View background = firstSegment.findViewById(R.id.overview_route_segment_image_background);


    assertEquals(activity.getString(R.string.public_transport) + " (8min)", typeView.getText());
    assertEquals("EUR 100", costView.getText());
    assertEquals("U2", segmentNameText.getText());
    assertEquals("U2", segmentNameText.getText());
    assertEquals(Color.parseColor("#aaaaaa"), ((ColorDrawable) background.getBackground()).getColor());
  }
}
