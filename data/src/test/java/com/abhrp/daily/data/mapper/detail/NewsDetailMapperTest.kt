package com.abhrp.daily.data.mapper.detail

import com.abhrp.daily.data.factory.FeedItemFactory
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
    fun mapToDomainMapsCorrectly() {
        val entity = FeedItemFactory.getNewsDataDetail()
        val domain = newsDetailMapper.mapToDomain(entity)
        Assert.assertEquals(entity.id, domain.id)
        Assert.assertEquals(entity.sectionName, domain.sectionName)
        Assert.assertEquals(entity.thumbnail, domain.thumbnail)
        Assert.assertEquals(entity.body, domain.body)
        Assert.assertEquals(entity.byline, domain.byline)
        Assert.assertEquals(entity.publicationDate, domain.publicationDate)
        Assert.assertEquals(entity.headline, domain.headline)
    }

}