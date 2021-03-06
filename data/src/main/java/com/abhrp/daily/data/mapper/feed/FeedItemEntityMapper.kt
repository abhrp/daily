package com.abhrp.daily.data.mapper.feed

import com.abhrp.daily.data.mapper.EntityMapper
import com.abhrp.daily.data.model.feed.FeedDataItem
import com.abhrp.daily.domain.model.feed.FeedItem
import javax.inject.Inject

class FeedItemEntityMapper @Inject constructor(): EntityMapper<FeedDataItem, FeedItem> {

    override fun mapToDomain(e: FeedDataItem): FeedItem {
        return FeedItem(e.id, e.sectionName, e.publicationDate, e.webUrl, e.headline, e.wordCount, e.thumbnail)
    }

}