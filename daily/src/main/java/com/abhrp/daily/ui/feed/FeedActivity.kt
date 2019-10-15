package com.abhrp.daily.ui.feed

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.abhrp.daily.R
import com.abhrp.daily.core.util.AppLogger
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
    lateinit var appLogger: AppLogger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        feedViewModel = ViewModelProviders.of(this, viewModelFactory).get(FeedViewModel::class.java)
        observeFeed()
    }

    private fun observeFeed() {
        feedViewModel.observeFeed().observe(this, Observer {data ->
            when(data.status) {
                ResourceState.LOADING -> {
                    refreshLayout.isRefreshing = true
                }
                ResourceState.SUCCESS -> {
                    refreshLayout.isRefreshing = false
                    appLogger.logDebug("Feed Items ${data.data.toString()}")
                }
                ResourceState.ERROR -> {
                    refreshLayout.isRefreshing = false
                }
            }
        })
    }

    override fun online() {

    }

    override fun offline() {

    }
}
