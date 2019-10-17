package com.abhrp.daily.core.components

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Custom ItemDecoration class to add vertical spacing between recycler view elements.
 */
class VerticalItemDecoration(private val verticalSpace: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (parent.getChildAdapterPosition(view) == 0 || parent.getChildAdapterPosition(view) == 1) {
            outRect.top = verticalSpace
        } else {
            outRect.top = verticalSpace + 2
        }

        outRect.bottom = verticalSpace + 2
    }
}