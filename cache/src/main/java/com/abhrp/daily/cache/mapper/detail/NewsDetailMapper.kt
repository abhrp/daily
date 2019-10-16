package com.abhrp.daily.cache.mapper.detail

import com.abhrp.daily.cache.mapper.CacheEntityMapper
import com.abhrp.daily.cache.model.feed.CachedFeedItem
import com.abhrp.daily.common.util.TimeProvider
import com.abhrp.daily.data.model.detail.NewsDetailData
import javax.inject.Inject

class NewsDetailMapper @Inject constructor(private val timeProvider: TimeProvider): CacheEntityMapper<NewsDetailData, CachedFeedItem> {

    override fun mapToCache(e: NewsDetailData): CachedFeedItem {
        val currentTime = timeProvider.currentTime
        return CachedFeedItem(e.id, e.publicationDate, e.sectionName, currentTime, "", e.headline, "", e.thumbnail, e.body, e.byline, 0, currentTime)
    }

    override fun mapToEntity(c: CachedFeedItem): NewsDetailData {
        return NewsDetailData(c.articleId, c.sectionName, c.headline, c.byline ?: "", c.body ?: "", c.thumbnail, c.date)
    }
}