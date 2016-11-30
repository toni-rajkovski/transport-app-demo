package com.rajkovski.toni.transportdemo.ui.map;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;
import com.rajkovski.toni.transportdemo.R;
import com.rajkovski.toni.transportdemo.model.Route;
import com.rajkovski.toni.transportdemo.model.Segment;
import com.rajkovski.toni.transportdemo.model.Stop;
import com.rajkovski.toni.transportdemo.util.DateTimeUtil;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

  public static final String INTENT_KEY_ROUTE_FOR_MAP = "transport_demo.route_for_map";

  private GoogleMap map;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.map_activity);

    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
      .findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    map = googleMap;

    Route route = (Route) getIntent().getSerializableExtra(INTENT_KEY_ROUTE_FOR_MAP);
    addRouteToMap(route);
  }

  private void addRouteToMap(Route route) {
    final LatLngBounds.Builder boundsBuilder = LatLngBounds.builder();
    final List<MarkerOptions> markers = new ArrayList<>();
    if (route != null && route.getSegments().size() > 0) {
      for (Segment segment : route.getSegments()) {
        if (segment.getPolyline() != null) {
          PolylineOptions polylineOptions = new PolylineOptions();
          List<LatLng> polylineLatLng = PolyUtil.decode(segment.getPolyline());
          for (LatLng latLng : polylineLatLng) {
            polylineOptions.add(latLng);
            boundsBuilder.include(latLng);
          }
          polylineOptions.color(Color.parseColor(segment.getColor()));
          map.addPolyline(polylineOptions);

        }
        if (segment.getStops() != null) {
          for (Stop stop : segment.getStops()) {
            MarkerOptions markerOptions = new MarkerOptions()
              .title(stop.getName())
              .snippet(DateTimeUtil.timeStringHHMM(stop.getDatetime()))
              .position(new LatLng(stop.getLat(), stop.getLng()))
              .visible(true);
            markers.add(markerOptions);
          }
        }
      }
      map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
         @Override
         public void onMapLoaded() {
           for (MarkerOptions marker : markers) {
             map.addMarker(marker);
           }
           map.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 300));
         }
       });
    }
  }

}
