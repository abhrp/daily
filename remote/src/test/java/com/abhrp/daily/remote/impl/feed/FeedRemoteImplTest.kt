package com.abhrp.daily.remote.impl.feed

import com.abhrp.daily.data.model.feed.FeedDataItem
import com.abhrp.daily.remote.factory.FeedItemFactory
import com.abhrp.daily.remote.mapper.feed.FeedItemMapper
import com.abhrp.daily.remote.model.BaseResponse
import com.abhrp.daily.remote.model.feed.FeedItemResponse
import com.abhrp.daily.remote.service.DailyServiceFactory
import com.abhrp.daily.remote.service.FeedService
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class FeedRemoteImplTest {

    private lateinit var feedRemoteImpl: FeedRemoteImpl

    @Mock
    private lateinit var dailyServiceFactory: DailyServiceFactory
    @Mock
    private lateinit var itemMapper: FeedItemMapper

    @Mock
    private lateinit var feedService: FeedService

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        feedRemoteImpl = FeedRemoteImpl(dailyServiceFactory, itemMapper)
    }

    @Test
    fun testGetFeedItemDataCompletes() {
        val response = FeedItemFactory.getFeedItemResponse(10)
        val pageNo = 1
        val dataItems = FeedItemFactory.getFeedDataItems(10)

        stubGetFeedService()
        stubGetFeedResponse(pageNo, response)
        stubMapper(response.response, dataItems)

        val testObserver = feedRemoteImpl.getFeedItemData(pageNo).test()
        testObserver.assertComplete()
    }

    @Test
    fun testGetFeedItemDataReturnsData() {
        val response = FeedItemFactory.getFeedItemResponse(10)
        val pageNo = 1
        val dataItems = FeedItemFactory.getFeedDataItems(10)

        stubGetFeedService()
        stubGetFeedResponse(pageNo, response)
        stubMapper(response.response, dataItems)

        val testObserver = feedRemoteImpl.getFeedItemData(pageNo).test()
        testObserver.assertValue(dataItems)
    }

    @Test
    fun testGetFeedItemDataCallsRemote() {
        val response = FeedItemFactory.getFeedItemResponse(10)
        val pageNo = 1
        val dataItems = FeedItemFactory.getFeedDataItems(10)

        stubGetFeedService()
        stubGetFeedResponse(pageNo, response)
        stubMapper(response.response, dataItems)

        feedRemoteImpl.getFeedItemData(pageNo).test()
        Mockito.verify(feedService).getFeedItems(pageNo)
    }

    private fun stubGetFeedService() {
        Mockito.`when`(dailyServiceFactory.feedService()).thenReturn(feedService)
    }

    private fun stubGetFeedResponse(pageNo: Int, response: BaseResponse<FeedItemResponse>) {
        Mockito.`when`(feedService.getFeedItems(pageNo)).thenReturn(Single.just(response))
    }

    private fun stubMapper(response: FeedItemResponse, data: List<FeedDataItem>) {
        Mockito.`when`(itemMapper.mapToEntity(response)).thenReturn(data)
    }

}