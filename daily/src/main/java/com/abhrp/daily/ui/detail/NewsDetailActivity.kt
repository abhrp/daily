package com.abhrp.daily.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.abhrp.daily.R
import com.abhrp.daily.common.util.AppLogger
import com.abhrp.daily.core.util.PixelHelper
import com.abhrp.daily.di.ViewModelFactory
import com.abhrp.daily.presentation.model.detail.DetailViewItem
import com.abhrp.daily.presentation.state.ResourceState
import com.abhrp.daily.presentation.viewmodel.detail.NewsDetailViewModel
import com.abhrp.daily.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_news_detail.*
import kotlinx.android.synthetic.main.content_detail.*
import javax.inject.Inject

class NewsDetailActivity : BaseActivity() {

    companion object {

        const val ITEM_ID = "itemId"
        const val TITLE = "headline"
        const val IMAGE_URL = "imageUrl"

        fun newIntent(context: Context, id: String, imageUrl: String, headline: String): Intent {
            val intent = Intent(context, NewsDetailActivity::class.java)
            intent.putExtra(ITEM_ID, id)
            intent.putExtra(TITLE, headline)
            intent.putExtra(IMAGE_URL, imageUrl)
            return intent
        }
    }

    @Inject
    lateinit var pixelHelper: PixelHelper
    @Inject
    lateinit var logger: AppLogger

    @Inject
    lateinit var newsDetailViewModel: NewsDetailViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var itemId: String = ""
    private var headline: String = ""
    private var imageUrl: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)
        setSupportActionBar(toolbar as Toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = ""
        }
        setValuesFromIntent()
        
        newsDetailViewModel = ViewModelProviders.of(this, viewModelFactory).get(NewsDetailViewModel::class.java)

        observeDetails()
        fetchDetails(itemId)
    }


    private fun setValuesFromIntent() {
        imageUrl = intent.getStringExtra(IMAGE_URL) ?: ""
        itemId = intent.getStringExtra(ITEM_ID) ?: ""
        headline = intent.getStringExtra(TITLE) ?: ""
    }

    private fun observeDetails() {
        newsDetailViewModel.observeDetails().observe(this, Observer { resource ->
            when(resource.status) {
                ResourceState.LOADING -> {}
                ResourceState.SUCCESS -> {
                    populateUI(resource.data)
                }
                ResourceState.ERROR -> {
                    logger.logError(resource.error)
                }
            }
        })
    }

    private fun fetchDetails(id: String) {
        newsDetailViewModel.fetchNewsDetails(id)
    }

    private fun populateUI(detailViewItem: DetailViewItem?) {
        if (detailViewItem != null) {
            val html = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(detailViewItem.body, Html.FROM_HTML_MODE_LEGACY)
            } else {
                @Suppress("DEPRECATION")
                Html.fromHtml(detailViewItem.body)
            }
            newsBody.text = html
        }
    }

    override fun online() {

    }

    override fun offline() {

    }
}
