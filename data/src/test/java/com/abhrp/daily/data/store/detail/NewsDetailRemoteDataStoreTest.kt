package com.abhrp.daily.data.store.detail

import com.abhrp.daily.data.factory.DataFactory
import com.abhrp.daily.data.factory.FeedItemFactory
import com.abhrp.daily.data.model.detail.NewsDetailData
import com.abhrp.daily.data.repository.detail.NewsDetailRemote
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class NewsDetailRemoteDataStoreTest {
    private lateinit var newsDetailRemoteDataStore: NewsDetailRemoteDataStore

    @Mock
    private lateinit var newsDetailRemote: NewsDetailRemote

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        newsDetailRemoteDataStore = NewsDetailRemoteDataStore((newsDetailRemote))
    }

    @Test
    fun testGetNewsDetailsCompletes() {
        val id = DataFactory.randomString
        val data = FeedItemFactory.getNewsDataDetail()
        stubGetNewsDetails(id, data)
        val testObserver = newsDetailRemoteDataStore.getNewsDetailData(id).test()
        testObserver.assertComplete()
    }

    @Test
    fun testGetNewsDetailsReturnsData() {
        val id = DataFactory.randomString
        val data = FeedItemFactory.getNewsDataDetail()
        stubGetNewsDetails(id, data)
        val testObserver = newsDetailRemoteDataStore.getNewsDetailData(id).test()
        testObserver.assertValue(data)
    }

    @Test
    fun testGetNewsDetailsCallsRemote() {
        val id = DataFactory.randomString
        val data = FeedItemFactory.getNewsDataDetail()
        stubGetNewsDetails(id, data)
        newsDetailRemoteDataStore.getNewsDetailData(id).test()
        Mockito.verify(newsDetailRemote).getNewsDetails(id)
    }

    private fun stubGetNewsDetails(id: String, data: NewsDetailData) {
        Mockito.`when`(newsDetailRemote.getNewsDetails(id)).thenReturn(Single.just(data))
    }

}