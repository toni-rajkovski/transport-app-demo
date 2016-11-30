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

  public class ViewHolder extends RecyclerView.ViewHolder {
    ViewGroup viewsHolder;
    TextView priceText;
    TextView routeTypeText;
    ImageView providerImage;
    ViewGroup segmentsHolder;

    public ViewHolder(ViewGroup v) {
      super(v);
      viewsHolder = v;

      priceText = (TextView) viewsHolder.findViewById(R.id.overview_route_cost);
      routeTypeText = (TextView) viewsHolder.findViewById(R.id.overview_route_type);
      providerImage = (ImageView) viewsHolder.findViewById(R.id.overview_route_provider_icon);
      segmentsHolder = (ViewGroup) viewsHolder.findViewById(R.id.overview_route_segments);
    }

    void fillWithData(Route route) {
      if (route.getPrice() != null) {
        priceText.setText(route.getPrice().getCurrency() + " " + route.getPrice().getAmount());
      } else {
        priceText.setText("");
      }
      routeTypeText.setText(
        findRouteTypeText(route.getType()));

      String providerUrl = ModelUtil.findProviderIconUrl(transportRoutes, route.getProvider());
      if (providerUrl != null) {
        loadAndDisplaySvgImage(providerUrl, providerImage);
      }

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
    holder.fillWithData(route);
  }

  private void loadAndDisplaySvgImage(final String url, final ImageView view) {
    svgService.loadSvgFromServer(new Subscriber<byte[]>() {
      @Override
      public void onCompleted() {
      }

      @Override
      public void onError(Throwable e) {
        //TODO we can add some default error image instead
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
          Logger.e(LOG_TAG, "Problem displaying svg for url: " + url, e);
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
