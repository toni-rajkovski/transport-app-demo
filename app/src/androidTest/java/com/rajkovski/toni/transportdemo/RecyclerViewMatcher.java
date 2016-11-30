package com.rajkovski.toni.transportdemo;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * RecyclerView matchers.
 */
public class RecyclerViewMatcher {

  private final int recyclerViewId;

  public RecyclerViewMatcher(int recyclerViewId) {
    this.recyclerViewId = recyclerViewId;
  }

  public Matcher<View> atPosition(final int position) {
    return atPositionOnView(position, -1);
  }

  public Matcher<View> atPositionOnView(final int position, final int targetViewId) {

    return new TypeSafeMatcher<View>() {
      Resources resources = null;
      View childView;

      public void describeTo(Description description) {
        String idDescription = Integer.toString(recyclerViewId);
        if (this.resources != null) {
          try {
            idDescription = this.resources.getResourceName(recyclerViewId);
          } catch (Resources.NotFoundException var4) {
            idDescription = String.format("%s (resource name not found)",
              new Object[]{Integer.valueOf(recyclerViewId)});
          }
        }
        description.appendText("with id: " + idDescription);
      }

      public boolean matchesSafely(View view) {
        this.resources = view.getResources();
        if (childView == null) {
          RecyclerView recyclerView =
            (RecyclerView) view.getRootView().findViewById(recyclerViewId);
          if (recyclerView != null && recyclerView.getId() == recyclerViewId) {
            childView = recyclerView.findViewHolderForAdapterPosition(position).itemView;
          } else {
            return false;
          }
        }
        if (targetViewId == -1) {
          return view == childView;
        } else {
          View targetView = childView.findViewById(targetViewId);
          return view == targetView;
        }
      }
    };
  }

  public Matcher<View> atPositionInViewSegments(final int position,
                                                final int segmentsPosition, final int segmentsViewId) {

    return new TypeSafeMatcher<View>() {
      Resources resources = null;
      View childView;

      public void describeTo(Description description) {
        String idDescription = Integer.toString(recyclerViewId);
        if (this.resources != null) {
          try {
            idDescription = this.resources.getResourceName(recyclerViewId);
          } catch (Resources.NotFoundException var4) {
            idDescription = String.format("%s (resource name not found)",
              new Object[]{Integer.valueOf(recyclerViewId)});
          }
        }
        description.appendText("with id: " + idDescription);
      }

      public boolean matchesSafely(View view) {
        this.resources = view.getResources();
        if (childView == null) {
          RecyclerView recyclerView =
            (RecyclerView) view.getRootView().findViewById(recyclerViewId);
          if (recyclerView != null && recyclerView.getId() == recyclerViewId) {
            View recyclerViewItem = recyclerView.findViewHolderForAdapterPosition(position).itemView;
            ViewGroup segments = (ViewGroup) recyclerViewItem.findViewById(R.id.overview_route_segments);
            childView = segments.getChildAt(segmentsPosition);
          } else {
            return false;
          }
        }
        if (segmentsViewId == -1) {
          return view == childView;
        } else {
          View targetView = childView.findViewById(segmentsViewId);
          return view == targetView;
        }
      }
    };
  }

}