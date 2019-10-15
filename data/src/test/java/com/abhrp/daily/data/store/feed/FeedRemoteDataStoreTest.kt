package com.abhrp.daily.data.store.feed

import com.abhrp.daily.data.factory.DataFactory
import com.abhrp.daily.data.factory.FeedItemFactory
import com.abhrp.daily.data.model.feed.FeedDataItem
import com.abhrp.daily.data.repository.feed.FeedRemote
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class FeedRemoteDataStoreTest {
    private lateinit var feedRemoteDataStore: FeedRemoteDataStore

    @Mock
    private lateinit var feedRemote: FeedRemote

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        feedRemoteDataStore = FeedRemoteDataStore(feedRemote)
    }

    @Test
    fun testGetFeedItemDataCompletes() {
        val data = FeedItemFactory.getFeedDataItems(10)
        val pageNo = DataFactory.randomInt
        stubGetFeedItemData(pageNo, data)
        val testObserver = feedRemoteDataStore.getFeedItemData(pageNo).test()
        testObserver.assertComplete()
    }

    @Test
    fun testGetFeedItemDataReturnsData() {
        val data = FeedItemFactory.getFeedDataItems(10)
        val pageNo = DataFactory.randomInt
        stubGetFeedItemData(pageNo, data)
        val testObserver = feedRemoteDataStore.getFeedItemData(pageNo).test()
        testObserver.assertValue(data)
    }

    @Test
    fun testGetFeedItemDataCallsRemote() {
        val data = FeedItemFactory.getFeedDataItems(10)
        val pageNo = DataFactory.randomInt
        stubGetFeedItemData(pageNo, data)
        feedRemoteDataStore.getFeedItemData(pageNo).test()
        Mockito.verify(feedRemote).getFeedItemData(pageNo)
    }

    private fun stubGetFeedItemData(pageNo: Int,data: List<FeedDataItem>) {
        Mockito.`when`(feedRemote.getFeedItemData(pageNo)).thenReturn(Single.just(data))
    }
}