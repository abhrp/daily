package com.abhrp.daily.domain.usecase.detail

import com.abhrp.daily.domain.executor.PostExecutionThread
import com.abhrp.daily.domain.factory.DataFactory
import com.abhrp.daily.domain.factory.FeedItemFactory
import com.abhrp.daily.domain.model.detail.NewsDetail
import com.abhrp.daily.domain.repository.detail.NewsDetailRepository
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class GetNewsDetailTest {

    private lateinit var getNewsDetail: GetNewsDetail

    @Mock
    private lateinit var postExecutionThread: PostExecutionThread
    @Mock
    private lateinit var newsDetailRepository: NewsDetailRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getNewsDetail = GetNewsDetail(postExecutionThread, newsDetailRepository)
    }

    @Test
    fun testGetNewsDetailCompletes() {
        val id = DataFactory.randomString
        val data = FeedItemFactory.getNewsDetail()
        stubGetNewsDetail(id, data)
        val testObserver = getNewsDetail.buildUseCaseObservable(GetNewsDetail.Params.getParams(id)).test()
        testObserver.assertComplete()
    }

    @Test
    fun testGetNewsDetailReturnsData() {
        val id = DataFactory.randomString
        val data = FeedItemFactory.getNewsDetail()
        stubGetNewsDetail(id, data)
        val testObserver = getNewsDetail.buildUseCaseObservable(GetNewsDetail.Params.getParams(id)).test()
        testObserver.assertValue(data)
    }

    @Test
    fun testGetNewsDetailCallsRepository() {
        val id = DataFactory.randomString
        val data = FeedItemFactory.getNewsDetail()
        stubGetNewsDetail(id, data)
        getNewsDetail.buildUseCaseObservable(GetNewsDetail.Params.getParams(id)).test()
        Mockito.verify(newsDetailRepository).getNewsDetails(id)
    }

    private fun stubGetNewsDetail(id: String, newsDetail: NewsDetail) {
        Mockito.`when`(newsDetailRepository.getNewsDetails(id)).thenReturn(Single.just(newsDetail))
    }

}