package com.abhrp.daily.cache.impl.feed

import com.abhrp.daily.cache.constants.CacheSQLConstants
import com.abhrp.daily.cache.dao.feed.CacheTimeDao
import com.abhrp.daily.cache.dao.feed.FeedDao
import com.abhrp.daily.cache.factory.DataFactory
import com.abhrp.daily.cache.factory.FeedItemFactory
import com.abhrp.daily.cache.mapper.feed.FeedItemMapper
import com.abhrp.daily.cache.model.feed.CachedFeedItem
import com.abhrp.daily.cache.model.feed.CachedTimeItem
import com.abhrp.daily.cache.sharedpreferences.DailySharedPreferences
import com.abhrp.daily.cache.sql.DailyDatabase
import com.abhrp.daily.common.util.TimeProvider
import com.abhrp.daily.data.model.feed.FeedDataItem
import io.reactivex.Maybe
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class FeedCacheImplTest {

    private lateinit var feedCacheImpl: FeedCacheImpl

    @Mock
    private lateinit var dailyDatabase: DailyDatabase
    @Mock
    private lateinit var dailySharedPreferences: DailySharedPreferences
    @Mock
    private lateinit var itemMapper: FeedItemMapper
    @Mock
    private lateinit var timerProvider: TimeProvider

    @Mock
    private lateinit var feedDao: FeedDao
    @Mock
    private lateinit var cachedTimeDao: CacheTimeDao

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        feedCacheImpl = FeedCacheImpl(dailyDatabase, dailySharedPreferences, itemMapper, timerProvider)
    }

    @Test
    fun testGetFeedItemsFromCacheCompletes() {
        val count = 10
        val pageNo = 1
        val cachedItems = FeedItemFactory.getCachedFeedItems(count, pageNo)
        val dataItems = FeedItemFactory.getFeedDataItems(count)

        stubGetFeedDao()
        stubGetFeedItems(pageNo, cachedItems)
        stubCacheToEntityMapper(cachedItems, dataItems)

        val testObserver = feedCacheImpl.getFeedItemData(pageNo).test()
        testObserver.assertComplete()
    }

    @Test
    fun testGetFeedItemsFromCacheReturnsData() {
        val count = 10
        val pageNo = 1
        val cachedItems = FeedItemFactory.getCachedFeedItems(count, pageNo)
        val dataItems = FeedItemFactory.getFeedDataItems(count)

        stubGetFeedDao()
        stubGetFeedItems(pageNo, cachedItems)
        stubCacheToEntityMapper(cachedItems, dataItems)

        val testObserver = feedCacheImpl.getFeedItemData(pageNo).test()
        testObserver.assertValue(dataItems)
    }

    @Test
    fun testGetFeedItemsCallsDatabase() {
        val count = 10
        val pageNo = 1
        val cachedItems = FeedItemFactory.getCachedFeedItems(count, pageNo)
        val dataItems = FeedItemFactory.getFeedDataItems(count)

        stubGetFeedDao()
        stubGetFeedItems(pageNo, cachedItems)
        stubCacheToEntityMapper(cachedItems, dataItems)

        feedCacheImpl.getFeedItemData(pageNo).test()
        Mockito.verify(feedDao).getFeedItems(pageNo)
    }

    @Test
    fun testSaveFeedItemDataCompletes() {
        val count = 10
        val pageNo = 1
        val feedDataItems = FeedItemFactory.getFeedDataItems(count)
        val cachedDataItems = FeedItemFactory.getCachedFeedItems(count, pageNo)

        stubEntityToCacheMapper(feedDataItems, cachedDataItems)
        stubGetFeedDao()
        stubSaveFeedItems(cachedDataItems)

        val testObserver = feedCacheImpl.saveFeedItemData(pageNo, feedDataItems).test()
        testObserver.assertComplete()
    }


    @Test
    fun testSaveLastCacheTimeCompletes() {
        val pageNo = 1
        val currentTime = DataFactory.randomLong
        stubCachedTimeDao()
        stubSaveLastCacheTime(CachedTimeItem(pageNo, currentTime))

        val testObserver = feedCacheImpl.saveLastCacheTime(pageNo, currentTime).test()
        testObserver.assertComplete()
    }

    @Test
    fun testClearFeedItemDataCompletes() {
        val pageNo = 1
        stubGetFeedDao()
        stubClearFeedItem(pageNo)
        val testObserver = feedCacheImpl.clearFeedItemData(pageNo).test()
        testObserver.assertComplete()
    }

    @Test
    fun testClearLastCacheTimeCompletes() {
        val pageNo = 1
        stubCachedTimeDao()
        stubClearCacheTime(pageNo)
        val testObserver = feedCacheImpl.clearLastCacheTime(pageNo).test()
        testObserver.assertComplete()
    }

    @Test
    fun testIsFeedItemDataCachedCompletes() {
        val pageNo = 1
        val count = 10
        val data = FeedItemFactory.getCachedFeedItems(count, pageNo)
        stubGetFeedDao()
        stubGetFeedItems(pageNo, data)

        val testObserver = feedCacheImpl.isFeedItemDataCached(pageNo).test()
        testObserver.assertComplete()
    }

    @Test
    fun testIsFeedItemDataCachedReturnsTrue() {
        val pageNo = 1
        val count = 10
        val data = FeedItemFactory.getCachedFeedItems(count, pageNo)
        stubGetFeedDao()
        stubGetFeedItems(pageNo, data)

        val testObserver = feedCacheImpl.isFeedItemDataCached(pageNo).test()
        testObserver.assertValue(true)
    }

    @Test
    fun testIsFeedItemDataCachedReturnsFalse() {
        val pageNo = 1
        val data = emptyList<CachedFeedItem>()
        stubGetFeedDao()
        stubGetFeedItems(pageNo, data)

        val testObserver = feedCacheImpl.isFeedItemDataCached(pageNo).test()
        testObserver.assertValue(false)
    }
    
    @Test
    fun testIsFeedItemDataExpiredCompletes() {
        val pageNo = 1
        val lastCacheTime = DataFactory.randomLong
        stubCachedTimeDao()
        stubGetLastCacheTime(pageNo, CachedTimeItem(pageNo, lastCacheTime))
        stubTimerProvider(System.currentTimeMillis())
        val testObserver = feedCacheImpl.isFeedItemDataExpired(pageNo).test()
        testObserver.assertComplete()
    }

    @Test
    fun testIsFeedItemDataExpiredReturnsFalse() {
        val pageNo = 1
        val lastCacheTime = System.currentTimeMillis()
        stubCachedTimeDao()
        stubGetLastCacheTime(pageNo, CachedTimeItem(pageNo, lastCacheTime))
        stubTimerProvider(System.currentTimeMillis())
        val testObserver = feedCacheImpl.isFeedItemDataExpired(pageNo).test()
        testObserver.assertValue(false)
    }

    @Test
    fun testIsFeedItemDataExpiredReturnsTrue() {
        val pageNo = 1
        val lastCacheTime = System.currentTimeMillis() - (2*CacheSQLConstants.CACHE_TIME_OUT).toLong()
        stubCachedTimeDao()
        stubGetLastCacheTime(pageNo, CachedTimeItem(pageNo, lastCacheTime))
        stubTimerProvider(System.currentTimeMillis())

        val testObserver = feedCacheImpl.isFeedItemDataExpired(pageNo).test()
        testObserver.assertValue(true)
    }

    @Test
    fun testClearAllFeedItemDataCompletes() {
        stubGetFeedDao()
        stubClearAllFeedData()
        val testObserver = feedCacheImpl.clearAllFeedItemData().test()
        testObserver.assertComplete()
    }

    @Test
    fun testClearAllCacheTimeCompletes() {
        stubCachedTimeDao()
        stubClearAllCacheTimeData()
        val testObserver = feedCacheImpl.clearAllCacheTime().test()
        testObserver.assertComplete()
    }

    @Test
    fun testSetCurrentPageNumberCompletes() {
        val pageNo = 1
        stubSetCurrentPageNumber(pageNo)
        val testObserver = feedCacheImpl.setCurrentPageNumber(pageNo).test()
        testObserver.assertComplete()
    }

    @Test
    fun testGetCurrentPageNumberCompletes() {
        val pageNo = 1
        stubGetCurrentPageNumber(pageNo)
        val testObserver = feedCacheImpl.getCurrentPageNumber().test()
        testObserver.assertComplete()
    }

    @Test
    fun testGetCurrentPageNumberReturnsData() {
        val pageNo = 1
        stubGetCurrentPageNumber(pageNo)
        val testObserver = feedCacheImpl.getCurrentPageNumber().test()
        testObserver.assertValue(pageNo)
    }


    private fun stubGetFeedDao() {
        Mockito.`when`(dailyDatabase.getFeedDao()).thenReturn(feedDao)
    }

    private fun stubCachedTimeDao() {
        Mockito.`when`(dailyDatabase.getCacheTimeDao()).thenReturn(cachedTimeDao)
    }

    private fun stubGetFeedItems(pageNo: Int, data: List<CachedFeedItem>) {
        Mockito.`when`(feedDao.getFeedItems(pageNo)).thenReturn(Maybe.just(data))
    }

    private fun stubSaveFeedItems(cachedFeedItems: List<CachedFeedItem>) {
        Mockito.doNothing().`when`(feedDao).saveFeedItems(cachedFeedItems)
    }

    private fun stubSaveLastCacheTime(cachedTimeItem: CachedTimeItem) {
        Mockito.doNothing().`when`(cachedTimeDao).saveCacheTime(cachedTimeItem)
    }

    private fun stubClearFeedItem(pageNo: Int) {
        Mockito.doNothing().`when`(feedDao).clearFeedItem(pageNo)
    }

    private fun stubClearCacheTime(pageNo: Int) {
        Mockito.doNothing().`when`(cachedTimeDao).clearCacheTime(pageNo)
    }

    private fun stubGetLastCacheTime(pageNo: Int, lastCacheTime: CachedTimeItem) {
        Mockito.`when`(cachedTimeDao.getLastCacheTime(pageNo)).thenReturn(Maybe.just(lastCacheTime))
    }

    private fun stubClearAllFeedData() {
        Mockito.doNothing().`when`(feedDao).clearAllFeedItems()
    }

    private fun stubClearAllCacheTimeData() {
        Mockito.doNothing().`when`(cachedTimeDao).clearAllCacheTime()
    }

    private fun stubGetCurrentPageNumber(pageNo: Int) {
        Mockito.`when`(dailySharedPreferences.currentPageNumber).thenReturn(pageNo)
    }

    private fun stubSetCurrentPageNumber(pageNo: Int) {
        Mockito.doNothing().`when`(dailySharedPreferences).currentPageNumber = pageNo
    }

    private fun stubCacheToEntityMapper(cachedFeedItems: List<CachedFeedItem>, feedDataItems: List<FeedDataItem>) {
        for(i in 0 until cachedFeedItems.count()) {
            Mockito.`when`(itemMapper.mapToEntity(cachedFeedItems[i])).thenReturn(feedDataItems[i])
        }
    }

    private fun stubEntityToCacheMapper(feedDataItems: List<FeedDataItem>, cachedFeedItems: List<CachedFeedItem>) {
        for (i in 0 until feedDataItems.count()) {
            Mockito.`when`(itemMapper.mapToCache(feedDataItems[i])).thenReturn(cachedFeedItems[i])
        }
    }

    private fun stubTimerProvider(currentTime: Long) {
        Mockito.`when`(timerProvider.currentTime).thenReturn(currentTime)
    }

}