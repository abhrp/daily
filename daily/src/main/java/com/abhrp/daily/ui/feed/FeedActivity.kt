package com.abhrp.daily.ui.feed

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abhrp.daily.R
import com.abhrp.daily.common.util.AppLogger
import com.abhrp.daily.core.components.RecyclerViewPaginationListener
import com.abhrp.daily.core.components.VerticalItemDecoration
import com.abhrp.daily.core.util.PixelHelper
import com.abhrp.daily.di.ViewModelFactory
import com.abhrp.daily.mapper.feed.FeedUIMapper
import com.abhrp.daily.model.feed.FeedUIItem
import com.abhrp.daily.presentation.model.feed.FeedViewItem
import com.abhrp.daily.presentation.state.Resource
import com.abhrp.daily.presentation.state.ResourceState
import com.abhrp.daily.presentation.viewmodel.feed.FeedViewModel
import com.abhrp.daily.ui.base.BaseActivity
import com.abhrp.daily.ui.detail.NewsDetailActivity
import kotlinx.android.synthetic.main.activity_feed.*
import javax.inject.Inject

/**
 * FeedActivity - Activity for first screen of the app
 */
class FeedActivity : BaseActivity() {

    @Inject
    lateinit var feedViewModel: FeedViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var logger: AppLogger
    @Inject
    lateinit var feedUIMapper: FeedUIMapper
    @Inject
    lateinit var feedAdapter: FeedAdapter

    @Inject
    lateinit var pixelHelper: PixelHelper

    private var paginationHandler: PaginationHandler? = null

    private var isOnline: Boolean = true
    private var isLoading: Boolean = false
    private var firstPage: Boolean = true
    private var newRequest: Boolean = false
    private var errorOrDone: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
        setSupportActionBar(toolbar as Toolbar)
        setUpSwipeLayout()
        feedViewModel = ViewModelProviders.of(this, viewModelFactory).get(FeedViewModel::class.java)
        observeFeedChanges()
        setUpClickListener()
        setUpListView()
        fetchFeedData()
    }

    private fun setUpListView() {
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        feedListView.layoutManager = layoutManager
        feedListView.addItemDecoration(VerticalItemDecoration(pixelHelper.pixelFromDp(8)))

        paginationHandler = PaginationHandler(layoutManager)
        paginationHandler?.let {
            feedListView.addOnScrollListener(it)
        }

        feedListView.adapter = feedAdapter
    }

    private fun setUpClickListener() {
        feedAdapter.feedItemClickListener = ClickHandler()
    }

    private fun setUpSwipeLayout() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            refreshLayout.setColorSchemeColors(resources.getColor(R.color.colorPrimary, null))
        } else {
            @Suppress("DEPRECATION")
            refreshLayout.setColorSchemeColors(resources.getColor(R.color.colorPrimary))
        }

        refreshLayout.setOnRefreshListener {
            if (isOnline) {
                newRequest = true
                firstPage = true
                fetchFeedData()
            } else {
                refreshLayout.isRefreshing = false
            }
        }
    }

    private fun observeFeedChanges() {
        feedViewModel.observeFeed().observe(this, Observer { resource ->
            when (resource.status) {
                ResourceState.LOADING -> {
                    onFeedLoading()
                }
                ResourceState.SUCCESS -> {
                    onFeedLoadSuccess(resource)
                }
                ResourceState.ERROR -> {
                    logger.logError(resource.error)
                    onFeedLoadFailure()
                }
            }
        })
    }

    private fun onFeedLoadFailure() {
        refreshLayout.isRefreshing = false
        if (isOnline) {
            showError(getString(R.string.error_news_feed))
        }
        isLoading = false
        errorOrDone = true
        feedAdapter.doneLoadingItems()
    }

    private fun onFeedLoadSuccess(resource: Resource<List<FeedViewItem>>) {
        refreshLayout.isRefreshing = false

        isLoading = false
        firstPage = false

        resource.data?.let { data ->
            if (data.isNotEmpty()) {
                handlePopulatedFeed(data)
            } else {
                handleEmptyFeed()
            }
        }
    }

    private fun handleEmptyFeed() {
        errorOrDone = true
        feedAdapter.doneLoadingItems()
        if (!isOnline && feedAdapter.itemCount == 0) {
            refreshLayout.visibility = View.GONE
            noInternetIcon.visibility = View.VISIBLE
        }
    }

    private fun handlePopulatedFeed(data: List<FeedViewItem>) {
        errorOrDone = false
        val feedUIItemsList = data.map { feedUIMapper.mapToUIView(it) }
        if (newRequest) {
            feedAdapter.refreshFeedItems()
            newRequest = false
        }
        feedAdapter.addFeedItems(feedUIItemsList)
    }

    private fun onFeedLoading() {
        isLoading = true
        if (!firstPage && !newRequest) {
            feedAdapter.isLoadingItems()
        }
    }

    private fun fetchFeedData() {
        feedViewModel.getFeed(firstPage, newRequest)
    }

    override fun online() {
        if (!isOnline) {
            isOnline = true
        }

        dismissOfflineSnackBar()

        if (noInternetIcon.visibility == View.VISIBLE) {
            noInternetIcon.visibility = View.GONE
            refreshLayout.visibility = View.VISIBLE
            fetchFeedData()
        }
    }

    override fun offline() {
        isOnline = false
        showOffLineSnackBar(feedContainer)
    }

    override fun onDestroy() {
        super.onDestroy()
        feedAdapter.feedItemClickListener = null
        paginationHandler?.let {
            feedListView.removeOnScrollListener(it)
        }
    }

    private fun startNewsDetailActivity(feedItem: FeedUIItem) {
        val newIntent = NewsDetailActivity.newIntent(
            this,
            feedItem.id,
            feedItem.thumbnail,
            feedItem.headline,
            feedItem.sectionName
        )
        startActivity(newIntent)
    }

    private inner class ClickHandler : FeedItemClickListener {
        /**
         * Will be called when a user clicks on any news article
         * @param feedItem FeedUIItem object which is clicked
         */
        override fun feedItemClicked(feedItem: FeedUIItem) {
            startNewsDetailActivity(feedItem)
        }
    }

    /**
     * PaginationHandler will handle pagination events from recycler view scroll listener
     */
    private inner class PaginationHandler(layoutManager: LinearLayoutManager) :
        RecyclerViewPaginationListener(layoutManager) {
        override fun onLoadNextPage() {
            fetchFeedData()
        }

        override fun isLastPage(): Boolean {
            return errorOrDone
        }

        override fun isLoading(): Boolean {
            return isLoading
        }
    }
}
