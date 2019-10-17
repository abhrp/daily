package com.abhrp.daily.presentation.mapper.detail

import com.abhrp.daily.presentation.factory.FeedItemFactory
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DetailViewMapperTest {

    private lateinit var detailViewMapper: DetailViewMapper

    @Before
    fun setup() {
        detailViewMapper = DetailViewMapper()
    }

    @Test
    fun testMapsToViewCorrectly() {
        val domain = FeedItemFactory.getNewsDetail()
        val viewItem = detailViewMapper.mapToView(domain)
        Assert.assertEquals(domain.id, viewItem.id)
        Assert.assertEquals(domain.headline, viewItem.headline)
        Assert.assertEquals(domain.publicationDate, viewItem.publicationDate)
        Assert.assertEquals(domain.byline, viewItem.byline)
        Assert.assertEquals(domain.body, viewItem.body)
        Assert.assertEquals(domain.thumbnail, viewItem.thumbnail)
        Assert.assertEquals(domain.sectionName, viewItem.sectionName)
    }

}