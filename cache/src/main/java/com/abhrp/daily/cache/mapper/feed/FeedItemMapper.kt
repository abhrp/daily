package com.abhrp.daily.cache.mapper.feed

import com.abhrp.daily.cache.mapper.CacheEntityMapper
import com.abhrp.daily.cache.model.feed.CachedFeedItem
import com.abhrp.daily.common.util.TimeProvider
import com.abhrp.daily.data.model.feed.FeedDataItem
import javax.inject.Inject

class FeedItemMapper @Inject constructor(private val timerProvider: TimeProvider): CacheEntityMapper<FeedDataItem, CachedFeedItem> {

    override fun mapToCache(e: FeedDataItem): CachedFeedItem {
        return CachedFeedItem(e.id, e.publicationDate, e.sectionName, timerProvider.currentTime, e.webUrl, e.headline, e.wordCount, e.thumbnail, null, null, e.pageNo, null)
    }

    override fun mapToEntity(c: CachedFeedItem): FeedDataItem {
        return FeedDataItem(c.articleId, c.sectionName, c.date, c.webUrl, c.headline, c.wordCount, c.thumbnail, c.pageNo)
    }
}