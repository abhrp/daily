package com.abhrp.daily.remote.impl.detail

import com.abhrp.daily.data.model.detail.NewsDetailData
import com.abhrp.daily.remote.constants.RemoteConstants
import com.abhrp.daily.remote.factory.FeedItemFactory
import com.abhrp.daily.remote.mapper.detail.NewsDetailMapper
import com.abhrp.daily.remote.model.BaseResponse
import com.abhrp.daily.remote.model.detail.DetailResponse
import com.abhrp.daily.remote.service.DailyServiceFactory
import com.abhrp.daily.remote.service.DetailService
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class NewsDetailRemoteImplTest {

    private lateinit var newsDetailRemoteImpl: NewsDetailRemoteImpl

    @Mock
    private lateinit var dailyServiceFactory: DailyServiceFactory
    @Mock
    private lateinit var mapper: NewsDetailMapper

    @Mock
    private lateinit var detailService: DetailService

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        newsDetailRemoteImpl = NewsDetailRemoteImpl(dailyServiceFactory, mapper)
    }

    @Test
    fun testGetDetailsCompletes() {
        val response = FeedItemFactory.getDetailsResponse()
        val data = FeedItemFactory.getNewsDataDetail()
        val id = response.response.details.id

        stubGetDetailService()
        stubGetDetails(id, response)
        stubMapper(response.response, data)

        val testObserver = newsDetailRemoteImpl.getNewsDetails(id).test()
        testObserver.assertComplete()
    }

    @Test
    fun testGetDetailsReturnsData() {
        val response = FeedItemFactory.getDetailsResponse()
        val data = FeedItemFactory.getNewsDataDetail()
        val id = response.response.details.id

        stubGetDetailService()
        stubGetDetails(id, response)
        stubMapper(response.response, data)

        val testObserver = newsDetailRemoteImpl.getNewsDetails(id).test()
        testObserver.assertValue(data)
    }

    @Test
    fun testGetDetailsCallsRemote() {
        val response = FeedItemFactory.getDetailsResponse()
        val data = FeedItemFactory.getNewsDataDetail()
        val id = response.response.details.id

        stubGetDetailService()
        stubGetDetails(id, response)
        stubMapper(response.response, data)

        newsDetailRemoteImpl.getNewsDetails(id).test()
        Mockito.verify(detailService).getDetails(id, RemoteConstants.DETAILS_PARAMS)
    }


    private fun stubGetDetailService() {
        Mockito.`when`(dailyServiceFactory.detailService).thenReturn(detailService)
    }

    private fun stubGetDetails(id: String, response: BaseResponse<DetailResponse>) {
        Mockito.`when`(detailService.getDetails(id, RemoteConstants.DETAILS_PARAMS)).thenReturn(
            Single.just(response))
    }

    private fun stubMapper(response: DetailResponse, newsDetailData: NewsDetailData) {
        Mockito.`when`(mapper.mapToEntity(response)).thenReturn(newsDetailData)
    }

}