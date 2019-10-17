package com.abhrp.daily.remote.mapper.detail

import com.abhrp.daily.remote.factory.FeedItemFactory
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NewsDetailMapperTest {

    private lateinit var newsDetailMapper: NewsDetailMapper

    @Before
    fun setup() {
        newsDetailMapper = NewsDetailMapper()
    }

    @Test
    fun testMapsToEntityCorrectly() {
        val detailResponse = FeedItemFactory.getDetailsResponse()
        val newsDetailData = newsDetailMapper.mapToEntity(detailResponse.response)
        val feedItem = detailResponse.response.details
        Assert.assertEquals(feedItem.id, newsDetailData.id)
        Assert.assertEquals(feedItem.publicationDate, newsDetailData.publicationDate)
        Assert.assertEquals(feedItem.sectionName, newsDetailData.sectionName)
        Assert.assertEquals(feedItem.fields.headline, newsDetailData.headline)
        Assert.assertEquals(feedItem.fields.thumbnail, newsDetailData.thumbnail)
        Assert.assertEquals(feedItem.fields.body, newsDetailData.body)
        Assert.assertEquals(feedItem.fields.byline, newsDetailData.byline)
    }

}