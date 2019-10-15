package com.abhrp.daily.presentation.factory

import com.abhrp.daily.domain.model.feed.FeedItem
import com.abhrp.daily.presentation.model.feed.FeedViewItem

object FeedItemFactory {

    fun getFeedItems(count: Int): List<FeedItem> {
        val feedItems = mutableListOf<FeedItem>()
        for (i in 1..count) {
            feedItems.add(getFeedItem())
        }
        return feedItems
    }

    fun getFeedItem(): FeedItem {
        return FeedItem(
            DataFactory.randomString,
            DataFactory.randomString,
            "",
            DataFactory.randomString,
            DataFactory.randomString,
            DataFactory.randomString,
            DataFactory.randomString
        )
    }

    fun getFeedViewItems(count: Int): List<FeedViewItem> {
        val feedViewItems = mutableListOf<FeedViewItem>()
        for(i in 1..count) {
            feedViewItems.add(getFeedViewItem())
        }
        return feedViewItems
    }

    fun getFeedViewItem(): FeedViewItem {
        return FeedViewItem(DataFactory.randomString, DataFactory.randomString, DataFactory.randomString, DataFactory.randomString, DataFactory.randomString)
    }
}