package com.abhrp.daily.remote.mapper.feed

import com.abhrp.daily.data.model.feed.FeedDataItem
import com.abhrp.daily.remote.mapper.RemoteEntityMapper
import com.abhrp.daily.remote.model.feed.FeedItemResponse
import javax.inject.Inject

class FeedItemMapper @Inject constructor(): RemoteEntityMapper<FeedItemResponse, List<FeedDataItem>> {

    override fun mapToEntity(response: FeedItemResponse): List<FeedDataItem> {
        val pageNo = response.pageNo
        return response.feedItems.map {
            FeedDataItem(it.id, it.sectionName, it.publicationDate, it.webUrl, it.fields.headline, it.fields.wordCount ?: "", it.fields.thumbnail ?: "", pageNo)
        }
    }
}