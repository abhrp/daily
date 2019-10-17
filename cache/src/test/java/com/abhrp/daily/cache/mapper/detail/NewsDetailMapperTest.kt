package com.abhrp.daily.cache.mapper.detail

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
class NewsDetailMapperTest {

    private lateinit var newsDetailMapper: NewsDetailMapper

    @Mock
    private lateinit var timeProvider: TimeProvider

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        newsDetailMapper = NewsDetailMapper(timeProvider)
    }

    @Test
    fun testMapsToCacheCorrectly() {
        val currentTime = DataFactory.randomLong
        val entity = FeedItemFactory.getNewsDataDetail()
        stubTimerProvider(currentTime)
        val cache = newsDetailMapper.mapToCache(entity)

        Assert.assertEquals(entity.id, cache.articleId)
        Assert.assertEquals(entity.headline, cache.headline)
        Assert.assertEquals(entity.sectionName, cache.sectionName)
        Assert.assertEquals(entity.thumbnail, cache.thumbnail)
        Assert.assertEquals(entity.byline, cache.byline)
        Assert.assertEquals(entity.body, cache.body)
        Assert.assertEquals(entity.publicationDate, cache.date)
        Assert.assertEquals(currentTime, cache.updateTime)
        Assert.assertEquals(currentTime, cache.lastCacheTime)
    }

    @Test
    fun testMapsToEntityCorrectly() {
        val currentTime = DataFactory.randomLong
        val cache = FeedItemFactory.getCachedFeedItem(1)
        stubTimerProvider(currentTime)
        val entity = newsDetailMapper.mapToEntity(cache)

        Assert.assertEquals(entity.id, cache.articleId)
        Assert.assertEquals(entity.headline, cache.headline)
        Assert.assertEquals(entity.sectionName, cache.sectionName)
        Assert.assertEquals(entity.thumbnail, cache.thumbnail)
        Assert.assertEquals(entity.byline, cache.byline)
        Assert.assertEquals(entity.body, cache.body)
        Assert.assertEquals(entity.publicationDate, cache.date)
    }

    private fun stubTimerProvider(currentTime: Long) {
        Mockito.`when`(timeProvider.currentTime).thenReturn(currentTime)
    }

}