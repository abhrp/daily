package com.abhrp.daily.remote.factory

import com.abhrp.daily.data.model.feed.FeedDataItem
import com.abhrp.daily.remote.model.BaseResponse
import com.abhrp.daily.remote.model.feed.FeedItem
import com.abhrp.daily.remote.model.feed.FeedItemResponse
import com.abhrp.daily.remote.model.feed.Fields


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

    fun getFeedItemResponse(count: Int): BaseResponse<FeedItemResponse> {
        return BaseResponse(FeedItemResponse(DataFactory.randomInt, DataFactory.randomInt, DataFactory.randomString, getFeedItems(count)))
    }


    fun getFeedItems(count: Int): List<FeedItem> {
        val feedItems = mutableListOf<FeedItem>()
        for (i in 1..count) {
            feedItems.add(getFeedItem())
        }
        return feedItems
    }

    fun getFeedItem(): FeedItem {
        return FeedItem(DataFactory.randomString, DataFactory.randomString, DataFactory.randomString, DataFactory.randomString, getFields())
    }

    fun getFields(): Fields {
        return Fields(DataFactory.randomString, DataFactory.randomString, DataFactory.randomString, DataFactory.randomString, DataFactory.randomString)
    }
}