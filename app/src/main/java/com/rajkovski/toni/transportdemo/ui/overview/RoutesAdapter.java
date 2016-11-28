package com.rajkovski.toni.transportdemo.ui.overview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rajkovski.toni.transportdemo.R;
import com.rajkovski.toni.transportdemo.model.Route;

import java.util.List;

public class RoutesAdapter extends RecyclerView.Adapter<RoutesAdapter.ViewHolder> {

  private List<Route> routes;

  public static class ViewHolder extends RecyclerView.ViewHolder {
    ViewGroup viewsHolder;

    ViewHolder(ViewGroup v) {
      super(v);
      viewsHolder = v;
    }
  }

  public RoutesAdapter(List<Route> routes) {
    this.routes = routes;
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
    Route route = routes.get(position);
    TextView priceText = (TextView) holder.viewsHolder.findViewById(R.id.overview_route_cost);
    if (route.getPrice() != null) {
      priceText.setText(route.getPrice().getCurrency() + " " + route.getPrice().getAmount());
    } else {
      priceText.setText("");
    }
    ((TextView) holder.viewsHolder.findViewById(R.id.overview_route_type)).setText(route.getType());
  }

  @Override
  public int getItemCount() {
    return routes.size();
  }
}
