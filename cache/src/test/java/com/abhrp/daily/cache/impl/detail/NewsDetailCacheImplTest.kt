package com.abhrp.daily.cache.impl.detail

import com.abhrp.daily.cache.constants.CacheSQLConstants
import com.abhrp.daily.cache.dao.detail.NewsDetailDao
import com.abhrp.daily.cache.factory.DataFactory
import com.abhrp.daily.cache.factory.FeedItemFactory
import com.abhrp.daily.cache.mapper.detail.NewsDetailMapper
import com.abhrp.daily.cache.model.feed.CachedFeedItem
import com.abhrp.daily.cache.sql.DailyDatabase
import com.abhrp.daily.common.util.TimeProvider
import com.abhrp.daily.data.model.detail.NewsDetailData
import io.reactivex.Completable
import io.reactivex.Maybe
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class NewsDetailCacheImplTest {

    private lateinit var newsDetailCacheImpl: NewsDetailCacheImpl

    @Mock
    private lateinit var dailyDatabase: DailyDatabase
    @Mock
    private lateinit var newsDetailMapper: NewsDetailMapper
    @Mock
    private lateinit var timeProvider: TimeProvider

    @Mock
    private lateinit var newsDetailDao: NewsDetailDao

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        newsDetailCacheImpl = NewsDetailCacheImpl(dailyDatabase, newsDetailMapper, timeProvider)
    }

    @Test
    fun testGetNewsDetailCompletes() {
        val cachedFeedItem = FeedItemFactory.getCachedFeedItem(1)
        val data = FeedItemFactory.getNewsDataDetail()
        stubGetNewsDetailDao()
        stubGetNewsDetail(cachedFeedItem.articleId, cachedFeedItem)
        stubMapToEntity(cachedFeedItem, data)
        val testObserver = newsDetailCacheImpl.getNewsDetails(cachedFeedItem.articleId).test()
        testObserver.assertComplete()
    }

    @Test
    fun testGetNewsDetailReturnsData() {
        val cachedFeedItem = FeedItemFactory.getCachedFeedItem(1)
        val data = FeedItemFactory.getNewsDataDetail()
        stubGetNewsDetailDao()
        stubGetNewsDetail(cachedFeedItem.articleId, cachedFeedItem)
        stubMapToEntity(cachedFeedItem, data)
        val testObserver = newsDetailCacheImpl.getNewsDetails(cachedFeedItem.articleId).test()
        testObserver.assertValue(data)
    }

    @Test
    fun testSaveNewsDetailCompletes() {
        val data = FeedItemFactory.getNewsDataDetail()
        val currentTime = DataFactory.randomLong

        stubGetNewsDetailDao()
        stubTimeProvider(currentTime)
        stubSaveNewsDetails(data.id, data.body, data.byline, currentTime)

        val testObserver = newsDetailCacheImpl.saveNewsDetails(data.id, data).test()
        testObserver.assertComplete()
    }

    @Test
    fun testIsNewsDetailsCachedCompletes() {
        val cachedFeedItem = FeedItemFactory.getCachedFeedItem(1)
        stubGetNewsDetailDao()
        stubGetNewsDetail(cachedFeedItem.articleId, cachedFeedItem)

        val testObserver = newsDetailCacheImpl.isNewsDetailsCached(cachedFeedItem.articleId).test()
        testObserver.assertComplete()
    }

    @Test
    fun testIsNewsDetailsCachedReturnsTrue() {
        val cachedFeedItem = FeedItemFactory.getCachedFeedItem(1)
        stubGetNewsDetailDao()
        stubGetNewsDetail(cachedFeedItem.articleId, cachedFeedItem)

        val testObserver = newsDetailCacheImpl.isNewsDetailsCached(cachedFeedItem.articleId).test()
        testObserver.assertValue(true)
    }

    @Test
    fun testIsNewsDetailsCachedReturnsFalse() {
        val cachedFeedItem = CachedFeedItem(DataFactory.randomString, DataFactory.randomString, DataFactory.randomString, DataFactory.randomLong, DataFactory.randomString, DataFactory.randomString, DataFactory.randomString, DataFactory.randomString, null, null, 1, 0)
        stubGetNewsDetailDao()
        stubGetNewsDetail(cachedFeedItem.articleId, cachedFeedItem)

        val testObserver = newsDetailCacheImpl.isNewsDetailsCached(cachedFeedItem.articleId).test()
        testObserver.assertValue(false)
    }

    @Test
    fun testIsNewsDetailsExpiredCompletes() {
        val cachedFeedItem = CachedFeedItem(DataFactory.randomString, DataFactory.randomString, DataFactory.randomString, DataFactory.randomLong, DataFactory.randomString, DataFactory.randomString, DataFactory.randomString, DataFactory.randomString, null, null, 1, System.currentTimeMillis())
        stubGetNewsDetailDao()
        stubGetNewsDetail(cachedFeedItem.articleId, cachedFeedItem)
        val testObserver = newsDetailCacheImpl.isNewsDetailsExpired(cachedFeedItem.articleId).test()
        testObserver.assertComplete()
    }

    @Test
    fun testIsNewsDetailsExpiredReturnsTrue() {
        val currentTime = System.currentTimeMillis()
        val cachedFeedItem = CachedFeedItem(DataFactory.randomString, DataFactory.randomString, DataFactory.randomString, DataFactory.randomLong, DataFactory.randomString, DataFactory.randomString, DataFactory.randomString, DataFactory.randomString, null, null, 1, (currentTime - (2*CacheSQLConstants.CACHE_TIME_OUT)))
        stubGetNewsDetailDao()
        stubGetNewsDetail(cachedFeedItem.articleId, cachedFeedItem)
        stubTimeProvider(currentTime)
        val testObserver = newsDetailCacheImpl.isNewsDetailsExpired(cachedFeedItem.articleId).test()
        testObserver.assertValue(true)
    }

    @Test
    fun testIsNewsDetailsExpiredReturnsFalse() {
        val currentTime = System.currentTimeMillis()
        val cachedFeedItem = CachedFeedItem(DataFactory.randomString, DataFactory.randomString, DataFactory.randomString, DataFactory.randomLong, DataFactory.randomString, DataFactory.randomString, DataFactory.randomString, DataFactory.randomString, null, null, 1, currentTime)
        stubGetNewsDetailDao()
        stubGetNewsDetail(cachedFeedItem.articleId, cachedFeedItem)
        stubTimeProvider(currentTime)
        val testObserver = newsDetailCacheImpl.isNewsDetailsExpired(cachedFeedItem.articleId).test()
        testObserver.assertValue(false)
    }

    @Test
    fun testClearNewsDetailsCompletes() {
        val id = DataFactory.randomString
        stubGetNewsDetailDao()
        stubClearNewsDetails(id, Completable.complete())

        val testObserver = newsDetailCacheImpl.clearNewsDetails(id).test()
        testObserver.assertComplete()
    }


    private fun stubGetNewsDetailDao() {
        Mockito.`when`(dailyDatabase.getNewsDetailDao()).thenReturn(newsDetailDao)
    }

    private fun stubGetNewsDetail(id: String, cachedFeedItem: CachedFeedItem) {
        Mockito.`when`(newsDetailDao.getNewsDetails(id)).thenReturn(Maybe.just(cachedFeedItem))
    }

    private fun stubClearNewsDetails(id: String, completable: Completable) {
        Mockito.doNothing().`when`(newsDetailDao).clearNewsDetails(id, null, null, null)
    }

    private fun stubSaveNewsDetails(id: String, body: String, byline: String, lastCacheTime: Long) {
        Mockito.doNothing().`when`(newsDetailDao).saveNewsDetails(id, body, byline, lastCacheTime)
    }

    private fun stubMapToEntity(cachedFeedItem: CachedFeedItem, newsDetailData: NewsDetailData) {
        Mockito.`when`(newsDetailMapper.mapToEntity(cachedFeedItem)).thenReturn(newsDetailData)
    }

    private fun stubMapToCache(newsDetailData: NewsDetailData, cachedFeedItem: CachedFeedItem) {
        Mockito.`when`(newsDetailMapper.mapToCache(newsDetailData)).thenReturn(cachedFeedItem)
    }

    private fun stubTimeProvider(currentTime: Long) {
        Mockito.`when`(timeProvider.currentTime).thenReturn(currentTime)
    }

}