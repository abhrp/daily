package com.abhrp.daily.core.components

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class RecyclerViewPaginationListener(private val linearLayoutManager: LinearLayoutManager): RecyclerView.OnScrollListener() {
    val PAGE_SIZE = 10

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItems = linearLayoutManager.childCount
        val totalItems = linearLayoutManager.itemCount
        val firstItemPosition = linearLayoutManager.findFirstVisibleItemPosition()

        if (!isLoading() && !isLastPage()) {
            if ((visibleItems + firstItemPosition) >= totalItems
                && firstItemPosition >= 0
                && totalItems >= PAGE_SIZE) {
                onLoadNextPage()
            }
        }
    }

    abstract fun onLoadNextPage()
    abstract fun isLastPage(): Boolean
    abstract fun isLoading(): Boolean
}