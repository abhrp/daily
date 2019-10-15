package com.abhrp.daily.presentation.mapper.feed

import com.abhrp.daily.presentation.factory.FeedItemFactory
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class FeedViewItemMapperTest {
    private lateinit var feedViewItemMapper: FeedViewItemMapper

    @Before
    fun setup() {
        feedViewItemMapper = FeedViewItemMapper()
    }

    @Test
    fun testMapToViewIsCorrect() {
        val feedItem = FeedItemFactory.getFeedItem()
        val viewItem = feedViewItemMapper.mapToView(feedItem)

        Assert.assertEquals(feedItem.id, viewItem.id)
        Assert.assertEquals(feedItem.sectionName, viewItem.sectionName)
        Assert.assertEquals(feedItem.thumbnail, viewItem.thumbnail)
        Assert.assertEquals(feedItem.publicationDate, viewItem.publicationDate)
        Assert.assertEquals(feedItem.headline, viewItem.headline)
    }
}