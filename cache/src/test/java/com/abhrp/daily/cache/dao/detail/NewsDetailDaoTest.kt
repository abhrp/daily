package com.abhrp.daily.cache.dao.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.abhrp.daily.cache.factory.DataFactory
import com.abhrp.daily.cache.factory.FeedItemFactory
import com.abhrp.daily.cache.model.feed.CachedFeedItem
import com.abhrp.daily.cache.sql.DailyDatabase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import javax.sql.DataSource

@RunWith(RobolectricTestRunner::class)
class NewsDetailDaoTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var dailyDatabase: DailyDatabase

    @Before
    fun setup() {
        dailyDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DailyDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun closeDb() {
        dailyDatabase.close()
    }

    @Test
    fun testGetNewsDetailsCompletes() {
        val cachedFeedItem = FeedItemFactory.getCachedFeedItem(1)
        dailyDatabase.getFeedDao().saveFeedItems(listOf(cachedFeedItem))
        val testObserver = dailyDatabase.getNewsDetailDao().getNewsDetails(cachedFeedItem.articleId).test()
        testObserver.assertComplete()
    }

    @Test
    fun testGetNewsDetailsReturnsData() {
        val cachedFeedItem = FeedItemFactory.getCachedFeedItem(1)
        dailyDatabase.getFeedDao().saveFeedItems(listOf(cachedFeedItem))
        val testObserver = dailyDatabase.getNewsDetailDao().getNewsDetails(cachedFeedItem.articleId).test()
        testObserver.assertValue(cachedFeedItem)
    }

    @Test
    fun testSaveNewsDetailsIsCorrect() {
        val cachedFeedItem = FeedItemFactory.getCachedFeedItem(1)
        dailyDatabase.getFeedDao().saveFeedItems(listOf(cachedFeedItem))
        val body = DataFactory.randomString
        val currentTime = DataFactory.randomLong
        val byline = DataFactory.randomString
        dailyDatabase.getNewsDetailDao().saveNewsDetails(cachedFeedItem.articleId, body, byline, lastCacheTime = currentTime)
        val testObserver = dailyDatabase.getNewsDetailDao().getNewsDetails(cachedFeedItem.articleId).test()
        val newCachedFeedItem = CachedFeedItem(cachedFeedItem.articleId, cachedFeedItem.date, cachedFeedItem.sectionName, cachedFeedItem.updateTime, cachedFeedItem.webUrl, cachedFeedItem.headline, cachedFeedItem.wordCount, cachedFeedItem.thumbnail, body, byline, cachedFeedItem.pageNo, lastCacheTime = currentTime)
        testObserver.assertValue(newCachedFeedItem)
    }

    @Test
    fun testClearNewsDetailsIsCorrect() {
        val cachedFeedItem = FeedItemFactory.getCachedFeedItem(1)
        dailyDatabase.getFeedDao().saveFeedItems(listOf(cachedFeedItem))
        val body = null
        val currentTime =null
        val byline =null
        dailyDatabase.getNewsDetailDao().clearNewsDetails(cachedFeedItem.articleId, body, byline, lastCacheTime = currentTime)
        val testObserver = dailyDatabase.getNewsDetailDao().getNewsDetails(cachedFeedItem.articleId).test()
        val newCachedFeedItem = CachedFeedItem(cachedFeedItem.articleId, cachedFeedItem.date, cachedFeedItem.sectionName, cachedFeedItem.updateTime, cachedFeedItem.webUrl, cachedFeedItem.headline, cachedFeedItem.wordCount, cachedFeedItem.thumbnail, body, byline, cachedFeedItem.pageNo, lastCacheTime = currentTime)
        testObserver.assertValue(newCachedFeedItem)
    }
}