package com.abhrp.daily.ui.feed

import com.abhrp.daily.model.feed.FeedUIItem

interface FeedItemClickListener {

    /**
     * Will be called when a user clicks on any news article
     * @param feedItem FeedUIItem object which is clicked
     */
    fun feedItemClicked(feedItem: FeedUIItem)
}