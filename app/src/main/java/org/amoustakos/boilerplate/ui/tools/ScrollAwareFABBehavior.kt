package org.amoustakos.boilerplate.ui.tools

import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Behaviour class to enable Floating Action Buttons to be hidden/shown on nested scroll <br></br>
 * (in a [RecyclerView]). To use, define `app:layout_behavior` <br></br>
 * on the [FloatingActionButton] inside a [CoordinatorLayout] layout.
 */
class ScrollAwareFABBehavior : FloatingActionButton.Behavior() {


	override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: FloatingActionButton, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
		super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type)

		val hide = dyConsumed > 0 && child.visibility == View.VISIBLE
		val show = dyConsumed < 0 && child.visibility != View.VISIBLE

		if (hide)
			child.hide()
		else if (show)
			child.show()
	}

}
