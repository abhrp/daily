package com.abhrp.daily.data.store.detail

import com.abhrp.daily.data.factory.DataFactory
import com.abhrp.daily.data.factory.FeedItemFactory
import com.abhrp.daily.data.model.detail.NewsDetailData
import com.abhrp.daily.data.repository.detail.NewsDetailCache
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class NewsDetailCacheDataStoreTest {

    private lateinit var newsDetailCacheDataStore: NewsDetailCacheDataStore

    @Mock
    private lateinit var newsDetailCache: NewsDetailCache

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        newsDetailCacheDataStore = NewsDetailCacheDataStore(newsDetailCache)
    }

    @Test
    fun testGetNewsDetailsCompletes() {
        val id = DataFactory.randomString
        val data = FeedItemFactory.getNewsDataDetail()
        stubGetNewsDetails(id, data)
        val testObserver = newsDetailCacheDataStore.getNewsDetailData(id).test()
        testObserver.assertComplete()
    }

    @Test
    fun testGetNewsDetailsReturnsData() {
        val id = DataFactory.randomString
        val data = FeedItemFactory.getNewsDataDetail()
        stubGetNewsDetails(id, data)
        val testObserver = newsDetailCacheDataStore.getNewsDetailData(id).test()
        testObserver.assertValue(data)
    }

    @Test
    fun testGetNewsDetailsCallsCache() {
        val id = DataFactory.randomString
        val data = FeedItemFactory.getNewsDataDetail()
        stubGetNewsDetails(id, data)
        newsDetailCacheDataStore.getNewsDetailData(id).test()
        Mockito.verify(newsDetailCache).getNewsDetails(id)
    }

    private fun stubGetNewsDetails(id: String, data: NewsDetailData) {
        Mockito.`when`(newsDetailCache.getNewsDetails(id)).thenReturn(Single.just(data))
    }

}