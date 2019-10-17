package com.abhrp.daily.data.factory

import com.abhrp.daily.data.model.detail.NewsDetailData
import com.abhrp.daily.data.model.feed.FeedDataItem
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

    fun getFeedItem(): FeedItem {
        return FeedItem(
            DataFactory.randomString,
            DataFactory.randomString,
            DataFactory.randomString,
            DataFactory.randomString,
            DataFactory.randomString,
            DataFactory.randomString,
            DataFactory.randomString
        )
    }

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

    fun getNewsDetail(): NewsDetail {
        return NewsDetail(DataFactory.randomString, DataFactory.randomString, DataFactory.randomString, DataFactory.randomString, DataFactory.randomString, DataFactory.randomString, DataFactory.randomString)
    }

    fun getNewsDataDetail(): NewsDetailData {
        return NewsDetailData(DataFactory.randomString, DataFactory.randomString, DataFactory.randomString, DataFactory.randomString, DataFactory.randomString, DataFactory.randomString, DataFactory.randomString)
    }
}