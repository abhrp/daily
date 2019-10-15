package com.abhrp.daily.cache.mapper.feed

import com.abhrp.daily.cache.factory.DataFactory
import com.abhrp.daily.cache.factory.FeedItemFactory
import com.abhrp.daily.common.util.TimeProvider
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class FeedItemMapperTest {
    private lateinit var feedItemMapper: FeedItemMapper

    @Mock
    private lateinit var timerProvider: TimeProvider

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        feedItemMapper = FeedItemMapper(timerProvider)
    }

    @Test
    fun testMapsToEntityCorrectly() {
        val cacheItem = FeedItemFactory.getCachedFeedItem(1)
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
        val currentTime = DataFactory.randomLong
        stubTimerProvider(currentTime)
        val cacheItem = feedItemMapper.mapToCache(dataItem)
        Assert.assertEquals(cacheItem.articleId, dataItem.id)
        Assert.assertEquals(cacheItem.date, dataItem.publicationDate)
        Assert.assertEquals(cacheItem.headline, dataItem.headline)
        Assert.assertEquals(cacheItem.pageNo, dataItem.pageNo)
        Assert.assertEquals(cacheItem.webUrl, dataItem.webUrl)
        Assert.assertEquals(cacheItem.thumbnail, dataItem.thumbnail)
        Assert.assertEquals(cacheItem.sectionName, dataItem.sectionName)
        Assert.assertEquals(cacheItem.wordCount, dataItem.wordCount)
        Assert.assertEquals(cacheItem.updateTime, currentTime)
        Assert.assertNull(cacheItem.body)
        Assert.assertNull(cacheItem.byline)
        Assert.assertNull(cacheItem.lastCacheTime)
    }

    private fun stubTimerProvider(lastCacheTime: Long) {
        Mockito.`when`(timerProvider.currentTime).thenReturn(lastCacheTime)
    }
}