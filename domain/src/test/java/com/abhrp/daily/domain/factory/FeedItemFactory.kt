package com.abhrp.daily.domain.factory

import com.abhrp.daily.domain.model.detail.NewsDetail
import com.abhrp.daily.domain.model.feed.FeedItem

object FeedItemFactory {

    fun getFeedItems(count: Int): List<FeedItem> {
        val feedItems = mutableListOf<FeedItem>()
        for (i in 1..count) {
            feedItems.add(getFeedItem())
        }
        return feedItems
    }

    private fun getFeedItem(): FeedItem {
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

    fun getNewsDetail(): NewsDetail {
        return NewsDetail(DataFactory.randomString, DataFactory.randomString, DataFactory.randomString, DataFactory.randomString, DataFactory.randomString, DataFactory.randomString, DataFactory.randomString)
    }
}