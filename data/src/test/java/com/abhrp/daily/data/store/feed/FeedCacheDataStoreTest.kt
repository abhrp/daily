package com.abhrp.daily.data.store.feed

import com.abhrp.daily.data.factory.DataFactory
import com.abhrp.daily.data.factory.FeedItemFactory
import com.abhrp.daily.data.model.feed.FeedDataItem
import com.abhrp.daily.data.repository.feed.FeedCache
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class FeedCacheDataStoreTest {
    private lateinit var feedCacheDataStore: FeedCacheDataStore

    @Mock
    private lateinit var feedCache: FeedCache

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        feedCacheDataStore = FeedCacheDataStore(feedCache)
    }

    @Test
    fun testGetFeedItemDataCompletes() {
        val data = FeedItemFactory.getFeedDataItems(10)
        val pageNo = DataFactory.randomInt
        stubGetFeedItemData(pageNo, data)
        val testObserver = feedCacheDataStore.getFeedItemData(pageNo).test()
        testObserver.assertComplete()
    }

    @Test
    fun testGetFeedItemDataReturnsData() {
        val data = FeedItemFactory.getFeedDataItems(10)
        val pageNo = DataFactory.randomInt
        stubGetFeedItemData(pageNo, data)
        val testObserver = feedCacheDataStore.getFeedItemData(pageNo).test()
        testObserver.assertValue(data)
    }

    @Test
    fun testGetFeedItemDataCallsCache() {
        val data = FeedItemFactory.getFeedDataItems(10)
        val pageNo = DataFactory.randomInt
        stubGetFeedItemData(pageNo, data)
        feedCacheDataStore.getFeedItemData(pageNo).test()
        Mockito.verify(feedCache).getFeedItemData(pageNo)
    }

    private fun stubGetFeedItemData(pageNo: Int,data: List<FeedDataItem>) {
        Mockito.`when`(feedCache.getFeedItemData(pageNo)).thenReturn(Single.just(data))
    }
}