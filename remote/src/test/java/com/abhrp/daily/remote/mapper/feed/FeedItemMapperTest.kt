package com.abhrp.daily.remote.mapper.feed

import com.abhrp.daily.data.model.feed.FeedDataItem
import com.abhrp.daily.remote.factory.FeedItemFactory
import com.abhrp.daily.remote.model.feed.FeedItem
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
    fun testMapToEntityReturnsCorrectData() {
        val response = FeedItemFactory.getFeedItemResponse(10).response
        val feedItems = response.feedItems
        val pageNo = response.pageNo

        val dataItems = feedItemMapper.mapToEntity(response)
        Assert.assertEquals(dataItems.count(), feedItems.count())

        for (i in 0 until 10) {
            assertFields(pageNo, feedItems[i], dataItems[i])
        }
    }

    private fun assertFields(pageNo: Int, feedItem: FeedItem, feedDataItem: FeedDataItem) {
        Assert.assertEquals(feedItem.id, feedDataItem.id)
        Assert.assertEquals(feedItem.sectionName, feedDataItem.sectionName)
        Assert.assertEquals(feedItem.publicationDate, feedDataItem.publicationDate)
        Assert.assertEquals(feedItem.webUrl, feedDataItem.webUrl)
        Assert.assertEquals(pageNo, feedDataItem.pageNo)
        Assert.assertEquals(feedItem.fields.headline, feedDataItem.headline)
        Assert.assertEquals(feedItem.fields.wordCount, feedDataItem.wordCount)
        Assert.assertEquals(feedItem.fields.thumbnail, feedDataItem.thumbnail)
    }

}