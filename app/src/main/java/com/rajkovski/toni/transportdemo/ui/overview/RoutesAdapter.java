package com.rajkovski.toni.transportdemo.ui.overview;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rajkovski.toni.transportdemo.App;
import com.rajkovski.toni.transportdemo.R;
import com.rajkovski.toni.transportdemo.logger.Logger;
import com.rajkovski.toni.transportdemo.model.ModelUtil;
import com.rajkovski.toni.transportdemo.model.Route;
import com.rajkovski.toni.transportdemo.model.Segment;
import com.rajkovski.toni.transportdemo.model.TransportRoutes;
import com.rajkovski.toni.transportdemo.services.svg.SvgService;
import com.scand.svg.SVGHelper;

import java.io.IOException;

import javax.inject.Inject;

import rx.Subscriber;

public class RoutesAdapter extends RecyclerView.Adapter<RoutesAdapter.ViewHolder> {

  private static final String LOG_TAG = "RoutesAdapter";
  private Context context;
  private TransportRoutes transportRoutes;

  @Inject
  SvgService svgService;

  public static class ViewHolder extends RecyclerView.ViewHolder {
    ViewGroup viewsHolder;

    ViewHolder(ViewGroup v) {
      super(v);
      viewsHolder = v;
    }
  }

  public RoutesAdapter(Context context, TransportRoutes routes) {
    this.context = context;
    this.transportRoutes = routes;

    App.getInstance().getAppComponent().inject(this);
  }

  @Override
  public RoutesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    ViewGroup v = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(
      R.layout.route_item, parent, false);
    ViewHolder vh = new ViewHolder(v);
    return vh;
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    Route route = transportRoutes.getRoutes().get(position);
    TextView priceText = (TextView) holder.viewsHolder.findViewById(R.id.overview_route_cost);
    if (route.getPrice() != null) {
      priceText.setText(route.getPrice().getCurrency() + " " + route.getPrice().getAmount());
    } else {
      priceText.setText("");
    }
    ((TextView) holder.viewsHolder.findViewById(R.id.overview_route_type)).setText(
      findRouteTypeText(route.getType()));

    String providerUrl = ModelUtil.findProviderIconUrl(transportRoutes, route.getProvider());
    if (providerUrl != null) {
      loadAndDisplaySvgImage(providerUrl,
        (ImageView) holder.viewsHolder.findViewById(R.id.overview_route_provider_icon));
    }

    ViewGroup segmentsHolder =
      (ViewGroup) holder.viewsHolder.findViewById(R.id.overview_route_segments);
    segmentsHolder.removeAllViews();
    for (Segment segment: route.getSegments()) {
      View segmentView = LayoutInflater.from(context).inflate(R.layout.segment_item, null);
      ImageView segmentImage = (ImageView) segmentView.findViewById(R.id.overview_route_segment_image);
      loadAndDisplaySvgImage(segment.getIcon_url(),
        segmentImage);

      View imageBackground = segmentView.findViewById(R.id.overview_route_segment_image_background);
      imageBackground.setBackgroundColor(Color.parseColor(segment.getColor()));
      ((TextView) segmentView.findViewById(R.id.overview_route_segment_name)).setText(
        segment.getName());
      segmentsHolder.addView(segmentView);
    }
  }

  private void loadAndDisplaySvgImage(final String url, final ImageView view) {
    svgService.loadSvgFromServer(new Subscriber<byte[]>() {
      @Override
      public void onCompleted() {
      }

      @Override
      public void onError(Throwable e) {
        Logger.w(LOG_TAG, "Cannot load image from url " + url, e);
      }

      @Override
      public void onNext(byte[] bytes) {
        try {
          SVGHelper
            .useContext(context)
            .open(new String(bytes))
            .setBaseBounds(view.getWidth(), view.getHeight())
            .setKeepAspectRatio(true)
            .pictureAsBackground(view);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }, url);
  }

  private int findRouteTypeText(String routeType) {
    return App.getInstance().getResources().getIdentifier(
      routeType, "string", App.getInstance().getPackageName());
  }

  @Override
  public int getItemCount() {
    return transportRoutes.getRoutes().size();
  }
}
