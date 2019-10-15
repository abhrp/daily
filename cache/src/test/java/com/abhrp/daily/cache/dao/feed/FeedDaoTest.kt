package com.abhrp.daily.cache.dao.feed

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.abhrp.daily.cache.factory.FeedItemFactory
import com.abhrp.daily.cache.sql.DailyDatabase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class FeedDaoTest {

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
    fun testGetFeedItemsCompletes() {
        val pageNo = 1
        val cachedFeedItems = FeedItemFactory.getCachedFeedItems(10, pageNo)
        dailyDatabase.getFeedDao().saveFeedItems(cachedFeedItems)
        val testObserver = dailyDatabase.getFeedDao().getFeedItems(pageNo).test()
        testObserver.assertComplete()
    }

    @Test
    fun testGetFeedItemsReturnsData() {
        val pageNo = 1
        val cachedFeedItemsPage1 = FeedItemFactory.getCachedFeedItems(10, pageNo)
        dailyDatabase.getFeedDao().saveFeedItems(cachedFeedItemsPage1)
        dailyDatabase.getFeedDao().saveFeedItems(FeedItemFactory.getCachedFeedItems(10, 2))
        val testObserver = dailyDatabase.getFeedDao().getFeedItems(pageNo).test()
        testObserver.assertValue(cachedFeedItemsPage1)
    }

    @Test
    fun testClearFeedItemsDeletesCorrectly() {
        val pageNo = 1
        val cachedFeedItemsPage1 = FeedItemFactory.getCachedFeedItems(10, pageNo)
        dailyDatabase.getFeedDao().saveFeedItems(cachedFeedItemsPage1)
        val cachedFeedItemsPage2 = FeedItemFactory.getCachedFeedItems(10, 2)
        dailyDatabase.getFeedDao().saveFeedItems(cachedFeedItemsPage2)
        dailyDatabase.getFeedDao().clearFeedItem(pageNo)

        val testObserver1 = dailyDatabase.getFeedDao().getFeedItems(pageNo).test()
        testObserver1.assertValue(emptyList())

        val testObserver2 = dailyDatabase.getFeedDao().getFeedItems(2).test()
        testObserver2.assertValue(cachedFeedItemsPage2)
    }

    @Test
    fun testClearAllFeedItemsDeletesCorrectly() {
        val pageNo = 1
        val cachedFeedItemsPage1 = FeedItemFactory.getCachedFeedItems(10, pageNo)
        dailyDatabase.getFeedDao().saveFeedItems(cachedFeedItemsPage1)
        val cachedFeedItemsPage2 = FeedItemFactory.getCachedFeedItems(10, 2)
        dailyDatabase.getFeedDao().saveFeedItems(cachedFeedItemsPage2)
        dailyDatabase.getFeedDao().clearAllFeedItems()

        val testObserver1 = dailyDatabase.getFeedDao().getFeedItems(pageNo).test()
        testObserver1.assertValue(emptyList())

        val testObserver2 = dailyDatabase.getFeedDao().getFeedItems(2).test()
        testObserver2.assertValue(emptyList())
    }

}