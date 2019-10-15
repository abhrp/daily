package com.abhrp.daily.cache.factory

import com.abhrp.daily.cache.model.feed.CachedFeedItem
import com.abhrp.daily.data.model.feed.FeedDataItem

object FeedItemFactory {

    fun getFeedDataItems(count: Int): List<FeedDataItem> {
        val feedItems = mutableListOf<FeedDataItem>()
        for (i in 1..count) {
            feedItems.add(getFeedDataItem())
        }
        return feedItems
    }

    fun getFeedDataItem(): FeedDataItem {
        return FeedDataItem(
            DataFactory.randomString,
            DataFactory.randomString,
            DataFactory.randomString,
            DataFactory.randomString,
            DataFactory.randomString,
            DataFactory.randomString,
            DataFactory.randomString,
            DataFactory.randomInt
        )
    }

    fun getCachedFeedItems(count: Int): List<CachedFeedItem> {
        val feedItems = mutableListOf<CachedFeedItem>()
        for (i in 1..count) {
            feedItems.add(getCachedFeedItem())
        }
        return feedItems
    }

    fun getCachedFeedItem(): CachedFeedItem {
        return CachedFeedItem(
            DataFactory.randomString,
            DataFactory.randomString,
            DataFactory.randomString,
            DataFactory.randomLong,
            DataFactory.randomString,
            DataFactory.randomString,
            DataFactory.randomString,
            DataFactory.randomString,
            DataFactory.randomString,
            DataFactory.randomString,
            DataFactory.randomInt,
            DataFactory.randomLong
        )
    }
}