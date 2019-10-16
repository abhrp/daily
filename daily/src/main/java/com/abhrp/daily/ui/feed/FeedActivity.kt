package com.abhrp.daily.ui.feed

import android.os.Build
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.abhrp.daily.R
import com.abhrp.daily.common.util.AppLogger
import com.abhrp.daily.di.ViewModelFactory
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

    private var isOnline:Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
        setSupportActionBar(toolbar as Toolbar)
        setUpSwipeLayout()
        feedViewModel = ViewModelProviders.of(this, viewModelFactory).get(FeedViewModel::class.java)
        observeFeed()
        fetchFeedData()
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
        feedViewModel.observeFeed().observe(this, Observer {data ->
            when(data.status) {
                ResourceState.LOADING -> {
                    refreshLayout.isRefreshing = true
                }
                ResourceState.SUCCESS -> {
                    refreshLayout.isRefreshing = false
                    logger.logDebug("Feed Items ${data.data.toString()}")
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
}
