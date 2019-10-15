package com.abhrp.daily.cache.mapper.feed

import com.abhrp.daily.cache.factory.FeedItemFactory
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class FeedItemMapperTest {
    private lateinit var feedItemMapper: FeedItemMapper

    @Before
    fun setup() {
        feedItemMapper = FeedItemMapper()
    }

    @Test
    fun testMapsToEntityCorrectly() {
        val cacheItem = FeedItemFactory.getCachedFeedItem()
        val dataItem = feedItemMapper.mapToEntity(cacheItem)
        Assert.assertEquals(cacheItem.articleId, dataItem.id)
        Assert.assertEquals(cacheItem.date, dataItem.publicationDate)
        Assert.assertEquals(cacheItem.headline, dataItem.headline)
        Assert.assertEquals(cacheItem.pageNo, dataItem.pageNo)
        Assert.assertEquals(cacheItem.webUrl, dataItem.webUrl)
        Assert.assertEquals(cacheItem.thumbnail, dataItem.thumbnail)
        Assert.assertEquals(cacheItem.sectionName, dataItem.sectionName)
        Assert.assertEquals(cacheItem.wordCount, dataItem.wordCount)
    }

    @Test
    fun testMapsToCacheCorrectly() {
        val dataItem = FeedItemFactory.getFeedDataItem()
        val cacheItem = feedItemMapper.mapToCache(dataItem)
        Assert.assertEquals(cacheItem.articleId, dataItem.id)
        Assert.assertEquals(cacheItem.date, dataItem.publicationDate)
        Assert.assertEquals(cacheItem.headline, dataItem.headline)
        Assert.assertEquals(cacheItem.pageNo, dataItem.pageNo)
        Assert.assertEquals(cacheItem.webUrl, dataItem.webUrl)
        Assert.assertEquals(cacheItem.thumbnail, dataItem.thumbnail)
        Assert.assertEquals(cacheItem.sectionName, dataItem.sectionName)
        Assert.assertEquals(cacheItem.wordCount, dataItem.wordCount)
        Assert.assertNotNull(cacheItem.updateTime)
        Assert.assertNull(cacheItem.body)
        Assert.assertNull(cacheItem.byline)
        Assert.assertNull(cacheItem.lastCacheTime)
    }
}