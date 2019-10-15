package com.abhrp.daily.data.impl.feed

import com.abhrp.daily.data.factory.FeedItemFactory
import com.abhrp.daily.data.mapper.feed.FeedItemEntityMapper
import com.abhrp.daily.data.model.feed.FeedDataItem
import com.abhrp.daily.data.repository.feed.FeedCache
import com.abhrp.daily.data.repository.feed.FeedDataStore
import com.abhrp.daily.data.store.feed.FeedCacheDataStore
import com.abhrp.daily.data.store.feed.FeedDataStoreFactory
import com.abhrp.daily.data.store.feed.FeedRemoteDataStore
import com.abhrp.daily.domain.model.feed.FeedItem
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class NewsFeedRepositoryImplTest {
    private lateinit var newsFeedRepositoryImpl: NewsFeedRepositoryImpl

    @Mock
    private lateinit var feedCacheDataStore: FeedCacheDataStore
    @Mock
    private lateinit var feedRemoteDataStore: FeedRemoteDataStore

    @Mock
    private lateinit var feedDataStoreFactory: FeedDataStoreFactory
    @Mock
    private lateinit var feedCache: FeedCache
    @Mock
    private lateinit var feedItemEntityMapper: FeedItemEntityMapper

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        newsFeedRepositoryImpl = NewsFeedRepositoryImpl(feedDataStoreFactory, feedCache, feedItemEntityMapper)
    }

    @Test
    fun testGetFeedItemsCompletesForRemote() {

        val itemCount = 10
        val feedItems = FeedItemFactory.getFeedItems(itemCount)
        val feedDataItems = FeedItemFactory.getFeedDataItems(itemCount)
        val isFirstPage = true
        val isNewRequest = true
        val pageNo = 1
        val isCached = false
        val isExpired = true

        stubGetCurrentPageNumber(pageNo)
        stubClearAllFeedItemData(isNewRequest, Completable.complete())
        stubIsDataItemCached(pageNo, isCached)
        stubIsDataItemExpired(pageNo, isExpired)
        stubGetDataStore(isCached, isExpired, feedRemoteDataStore)
        stubGetDataFromRemote(pageNo, feedDataItems)
        stubClearItemData(pageNo, Completable.complete())
        stubSaveFeedItemData(pageNo, feedDataItems, Completable.complete())
        stubSetCurrentPageNo(pageNo, Completable.complete())
        stubMapToDomain(feedDataItems, feedItems)

        val testObserver = newsFeedRepositoryImpl.getFeedItems(isFirstPage, isNewRequest).test()
        testObserver.assertComplete()
    }

    @Test
    fun testGetFeedItemsCompletesForCache() {

        val itemCount = 10
        val feedItems = FeedItemFactory.getFeedItems(itemCount)
        val feedDataItems = FeedItemFactory.getFeedDataItems(itemCount)
        val isFirstPage = true
        val isNewRequest = false
        val pageNo = 1
        val isCached = true
        val isExpired = false

        stubGetCurrentPageNumber(pageNo)
        stubIsDataItemCached(pageNo, isCached)
        stubIsDataItemExpired(pageNo, isExpired)
        stubGetDataStore(isCached, isExpired, feedCacheDataStore)
        stubGetDataFromCache(pageNo, feedDataItems)
        stubSetCurrentPageNo(pageNo, Completable.complete())
        stubMapToDomain(feedDataItems, feedItems)

        val testObserver = newsFeedRepositoryImpl.getFeedItems(isFirstPage, isNewRequest).test()
        testObserver.assertComplete()
    }

    @Test
    fun testGetFeedItemsForRemoteReturnsData() {

        val itemCount = 10
        val feedItems = FeedItemFactory.getFeedItems(itemCount)
        val feedDataItems = FeedItemFactory.getFeedDataItems(itemCount)
        val isFirstPage = true
        val isNewRequest = true
        val pageNo = 1
        val isCached = false
        val isExpired = true

        stubGetCurrentPageNumber(pageNo)
        stubClearAllFeedItemData(isNewRequest, Completable.complete())
        stubIsDataItemCached(pageNo, isCached)
        stubIsDataItemExpired(pageNo, isExpired)
        stubGetDataStore(isCached, isExpired, feedRemoteDataStore)
        stubGetDataFromRemote(pageNo, feedDataItems)
        stubClearItemData(pageNo, Completable.complete())
        stubSaveFeedItemData(pageNo, feedDataItems, Completable.complete())
        stubSetCurrentPageNo(pageNo, Completable.complete())
        stubMapToDomain(feedDataItems, feedItems)

        val testObserver = newsFeedRepositoryImpl.getFeedItems(isFirstPage, isNewRequest).test()
        testObserver.assertValue(feedItems)
    }

    @Test
    fun testGetFeedItemsForCacheReturnsData() {

        val itemCount = 10
        val feedItems = FeedItemFactory.getFeedItems(itemCount)
        val feedDataItems = FeedItemFactory.getFeedDataItems(itemCount)
        val isFirstPage = true
        val isNewRequest = false
        val pageNo = 1
        val isCached = true
        val isExpired = false

        stubGetCurrentPageNumber(pageNo)
        stubIsDataItemCached(pageNo, isCached)
        stubIsDataItemExpired(pageNo, isExpired)
        stubGetDataStore(isCached, isExpired, feedCacheDataStore)
        stubGetDataFromCache(pageNo, feedDataItems)
        stubSetCurrentPageNo(pageNo, Completable.complete())
        stubMapToDomain(feedDataItems, feedItems)

        val testObserver = newsFeedRepositoryImpl.getFeedItems(isFirstPage, isNewRequest).test()
        testObserver.assertValue(feedItems)
    }


    private fun stubGetCurrentPageNumber(pageNo: Int) {
        Mockito.`when`(feedCache.getCurrentPageNumber()).thenReturn(Single.just(pageNo))
    }

    private fun stubClearAllFeedItemData(isNewRequest: Boolean, completable: Completable) {
        if (isNewRequest) {
            Mockito.`when`(feedCache.clearAllFeedItemData()).thenReturn(completable)
        }
    }

    private fun stubIsDataItemCached(pageNo: Int, cached: Boolean) {
        Mockito.`when`(feedCache.isFeedItemDataCached(pageNo)).thenReturn(Single.just(cached))
    }

    private fun stubIsDataItemExpired(pageNo: Int, expired: Boolean) {
        Mockito.`when`(feedCache.isFeedItemDataExpired(pageNo)).thenReturn(Single.just(expired))
    }

    private fun stubGetDataStore(cached: Boolean, expired: Boolean, dataStore: FeedDataStore) {
        Mockito.`when`(feedDataStoreFactory.getFeedDataStore(cached, expired)).thenReturn(dataStore)
    }

    private fun stubGetDataFromRemote(pageNo: Int, data: List<FeedDataItem>) {
        Mockito.`when`(feedRemoteDataStore.getFeedItemData(pageNo)).thenReturn(Single.just(data))
    }

    private fun stubGetDataFromCache(pageNo: Int, data: List<FeedDataItem>) {
        Mockito.`when`(feedCacheDataStore.getFeedItemData(pageNo)).thenReturn(Single.just(data))
    }

    private fun stubClearItemData(pageNo: Int, completable: Completable) {
        Mockito.`when`(feedCache.clearFeedItemData(pageNo)).thenReturn(completable)
    }

    private fun stubSaveFeedItemData(pageNo: Int, data: List<FeedDataItem>, completable: Completable) {
        Mockito.`when`(feedCache.saveFeedItemData(pageNo, data)).thenReturn(completable)
    }

    private fun stubSetCurrentPageNo(pageNo: Int, completable: Completable) {
        Mockito.`when`(feedCache.setCurrentPageNumber(pageNo)).thenReturn(completable)
    }

    private fun stubMapToDomain(feedDataItems: List<FeedDataItem>, feedItems: List<FeedItem>) {
        for (i in 0 until feedDataItems.count()) {
            Mockito.`when`(feedItemEntityMapper.mapToDomain(feedDataItems[i])).thenReturn(feedItems[i])
        }
    }
}