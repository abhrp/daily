package com.abhrp.daily.data.mapper.feed

import com.abhrp.daily.data.factory.FeedItemFactory
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class FeedItemEntityMapperTest {
    private lateinit var feedItemEntityMapper: FeedItemEntityMapper

    @Before
    fun setup() {
        feedItemEntityMapper = FeedItemEntityMapper()
    }

    @Test
    fun testMapToDomainReturnsCorrectData() {
        val dataModel = FeedItemFactory.getFeedDataItem()
        val domain = feedItemEntityMapper.mapToDomain(dataModel)
        Assert.assertEquals(dataModel.id, domain.id)
        Assert.assertEquals(dataModel.sectionName, domain.sectionName)
        Assert.assertEquals(dataModel.webUrl, domain.webUrl)
        Assert.assertEquals(dataModel.headline, domain.headline)
        Assert.assertEquals(dataModel.wordCount, domain.wordCount)
        Assert.assertEquals(dataModel.thumbnail, domain.thumbnail)
    }
}