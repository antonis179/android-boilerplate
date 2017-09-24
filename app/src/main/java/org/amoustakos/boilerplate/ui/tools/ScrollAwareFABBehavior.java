package org.amoustakos.boilerplate.ui.tools;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Behaviour class to enable Floating Action Buttons to be hidden/shown on nested scroll <br>
 * (in a {@link RecyclerView}). To use, define `app:layout_behavior` <br>
 *  on the {@link FloatingActionButton} inside a {@link CoordinatorLayout} layout.
 */
public class ScrollAwareFABBehavior extends FloatingActionButton.Behavior{

    public ScrollAwareFABBehavior(Context context, AttributeSet attrs) {
        super();
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child,
                               View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed,
                dyUnconsumed);
        if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE)
            child.hide();
        else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE)
            child.show();
    }



}
