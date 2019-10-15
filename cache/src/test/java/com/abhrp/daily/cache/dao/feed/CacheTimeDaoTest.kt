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
class CacheTimeDaoTest {

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
    fun testSaveCacheTimeCompletes() {
        val pageNo = 1
        val cachedTime = FeedItemFactory.getCacheTime(pageNo)
        dailyDatabase.getCacheTimeDao().saveCacheTime(cachedTime)
        val testObserver = dailyDatabase.getCacheTimeDao().getLastCacheTime(pageNo).test()
        testObserver.assertComplete()
    }

    @Test
    fun testSaveCacheTimeReturnsData() {
        val pageNo = 1
        val cachedTime = FeedItemFactory.getCacheTime(pageNo)
        dailyDatabase.getCacheTimeDao().saveCacheTime(cachedTime)
        val testObserver = dailyDatabase.getCacheTimeDao().getLastCacheTime(pageNo).test()
        testObserver.assertValue(cachedTime)
    }

    @Test
    fun testClearCacheTimeClearsCorrectly() {
        val pageNo = 1
        val cachedTime = FeedItemFactory.getCacheTime(pageNo)
        val pageNo2 = 2
        val cachedTime2 = FeedItemFactory.getCacheTime(pageNo2)
        dailyDatabase.getCacheTimeDao().saveCacheTime(cachedTime)
        dailyDatabase.getCacheTimeDao().saveCacheTime(cachedTime2)
        dailyDatabase.getCacheTimeDao().clearCacheTime(pageNo)
        val testObserver = dailyDatabase.getCacheTimeDao().getLastCacheTime(pageNo).test()
        val testObserver2 = dailyDatabase.getCacheTimeDao().getLastCacheTime(pageNo2).test()
        testObserver.assertNoValues()
        testObserver2.assertValue(cachedTime2)
    }

    @Test
    fun testClearAllCacheTimeClearsCorrectly() {
        val pageNo = 1
        val cachedTime = FeedItemFactory.getCacheTime(pageNo)
        val pageNo2 = 2
        val cachedTime2 = FeedItemFactory.getCacheTime(pageNo2)
        dailyDatabase.getCacheTimeDao().saveCacheTime(cachedTime)
        dailyDatabase.getCacheTimeDao().saveCacheTime(cachedTime2)
        dailyDatabase.getCacheTimeDao().clearAllCacheTime()
        val testObserver = dailyDatabase.getCacheTimeDao().getLastCacheTime(pageNo).test()
        val testObserver2 = dailyDatabase.getCacheTimeDao().getLastCacheTime(pageNo2).test()
        testObserver.assertNoValues()
        testObserver2.assertNoValues()
    }

}