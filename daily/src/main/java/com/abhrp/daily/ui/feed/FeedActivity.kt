package com.abhrp.daily.ui.feed

import android.os.Build
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abhrp.daily.R
import com.abhrp.daily.common.util.AppLogger
import com.abhrp.daily.core.components.VerticalItemDecoration
import com.abhrp.daily.core.util.PixelHelper
import com.abhrp.daily.di.ViewModelFactory
import com.abhrp.daily.mapper.feed.FeedUIMapper
import com.abhrp.daily.model.feed.FeedUIItem
import com.abhrp.daily.presentation.state.ResourceState
import com.abhrp.daily.presentation.viewmodel.feed.FeedViewModel
import com.abhrp.daily.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_feed.*
import javax.inject.Inject

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

    private var isOnline:Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
        setSupportActionBar(toolbar as Toolbar)
        setUpSwipeLayout()
        feedViewModel = ViewModelProviders.of(this, viewModelFactory).get(FeedViewModel::class.java)
        observeFeed()
        setUpClickListener()
        setUpListView()
        fetchFeedData()
    }

    private fun setUpListView() {
        feedListView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        feedListView.addItemDecoration(VerticalItemDecoration(pixelHelper.pixelFromDp(8)))
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

            } else {

            }
        }
    }

    private fun observeFeed() {
        feedViewModel.observeFeed().observe(this, Observer {resource ->
            when(resource.status) {
                ResourceState.LOADING -> {

                }
                ResourceState.SUCCESS -> {
                    refreshLayout.isRefreshing = false
                    resource.data?.let { data ->
                        val feedUIItemsList = data.map { feedUIMapper.mapToUIView(it) }
                        feedAdapter.addFeedItems(feedUIItemsList)
                    }
                }
                ResourceState.ERROR -> {
                    refreshLayout.isRefreshing = false
                }
            }
        })
    }

    private fun fetchFeedData() {
        feedViewModel.getFeed(true, false)
    }

    override fun online() {
        if(!isOnline) {
            isOnline = true
        }
    }

    override fun offline() {
        isOnline = false
    }

    override fun onDestroy() {
        super.onDestroy()
        feedAdapter.feedItemClickListener = null
    }

    private inner class ClickHandler: FeedItemClickListener {
        /**
         * Will be called when a user clicks on any news article
         * @param feedItem FeedUIItem object which is clicked
         */
        override fun feedItemClicked(feedItem: FeedUIItem) {

        }
    }
}
