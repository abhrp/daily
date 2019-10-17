package com.abhrp.daily.data.impl.detail

import com.abhrp.daily.data.factory.DataFactory
import com.abhrp.daily.data.factory.FeedItemFactory
import com.abhrp.daily.data.mapper.detail.NewsDetailMapper
import com.abhrp.daily.data.model.detail.NewsDetailData
import com.abhrp.daily.data.repository.detail.NewsDetailCache
import com.abhrp.daily.data.repository.detail.NewsDetailDataStore
import com.abhrp.daily.data.store.detail.NewsDetailCacheDataStore
import com.abhrp.daily.data.store.detail.NewsDetailDataStoreFactory
import com.abhrp.daily.data.store.detail.NewsDetailRemoteDataStore
import com.abhrp.daily.domain.model.detail.NewsDetail
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
class NewsDetailRepositoryImplTest {
    private lateinit var newsDetailRepositoryImpl: NewsDetailRepositoryImpl

    @Mock
    private lateinit var newsDetailDataStoreFactory: NewsDetailDataStoreFactory
    @Mock
    private lateinit var newsDetailCache: NewsDetailCache
    @Mock
    private lateinit var newsDetailMapper: NewsDetailMapper

    @Mock
    private lateinit var newsDetailCacheDataStore: NewsDetailCacheDataStore
    @Mock
    private lateinit var newsDetailRemoteDataStore: NewsDetailRemoteDataStore

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        newsDetailRepositoryImpl = NewsDetailRepositoryImpl(newsDetailDataStoreFactory, newsDetailCache, newsDetailMapper)
    }

    @Test
    fun testGetNewsDetailsRemoteCompletes() {
        val id = DataFactory.randomString
        val newsDetailData = FeedItemFactory.getNewsDataDetail()
        val newsDetail = FeedItemFactory.getNewsDetail()

        val isCached = false
        val isExpired = true

        stubGetIsCached(id, isCached)
        stubIsExpired(id, isExpired)
        stubGetDataStore(isCached, isExpired, newsDetailRemoteDataStore)
        stubGetNewsDetailRemote(id, newsDetailData)
        stubClearNewsDetail(id, Completable.complete())
        stubSaveNewsDetail(id, newsDetailData, Completable.complete())
        stubMapper(newsDetailData, newsDetail)

        val testObserver = newsDetailRepositoryImpl.getNewsDetails(id).test()
        testObserver.assertComplete()
    }

    @Test
    fun testGetNewsDetailsRemoteReturnsData() {
        val id = DataFactory.randomString
        val newsDetailData = FeedItemFactory.getNewsDataDetail()
        val newsDetail = FeedItemFactory.getNewsDetail()

        val isCached = false
        val isExpired = true

        stubGetIsCached(id, isCached)
        stubIsExpired(id, isExpired)
        stubGetDataStore(isCached, isExpired, newsDetailRemoteDataStore)
        stubGetNewsDetailRemote(id, newsDetailData)
        stubClearNewsDetail(id, Completable.complete())
        stubSaveNewsDetail(id, newsDetailData, Completable.complete())
        stubMapper(newsDetailData, newsDetail)

        val testObserver = newsDetailRepositoryImpl.getNewsDetails(id).test()
        testObserver.assertValue(newsDetail)
    }

    @Test
    fun testGetNewsDetailsCacheCompletes() {
        val id = DataFactory.randomString
        val newsDetailData = FeedItemFactory.getNewsDataDetail()
        val newsDetail = FeedItemFactory.getNewsDetail()

        val isCached = true
        val isExpired = false

        stubGetIsCached(id, isCached)
        stubIsExpired(id, isExpired)
        stubGetDataStore(isCached, isExpired, newsDetailCacheDataStore)
        stubGetNewsDetailCache(id, newsDetailData)
        stubMapper(newsDetailData, newsDetail)

        val testObserver = newsDetailRepositoryImpl.getNewsDetails(id).test()
        testObserver.assertComplete()
    }

    @Test
    fun testGetNewsDetailsCacheReturnsData() {
        val id = DataFactory.randomString
        val newsDetailData = FeedItemFactory.getNewsDataDetail()
        val newsDetail = FeedItemFactory.getNewsDetail()

        val isCached = true
        val isExpired = false

        stubGetIsCached(id, isCached)
        stubIsExpired(id, isExpired)
        stubGetDataStore(isCached, isExpired, newsDetailCacheDataStore)
        stubGetNewsDetailCache(id, newsDetailData)
        stubMapper(newsDetailData, newsDetail)

        val testObserver = newsDetailRepositoryImpl.getNewsDetails(id).test()
        testObserver.assertValue(newsDetail)
    }

    private fun stubGetIsCached(id: String, isCached: Boolean) {
        Mockito.`when`(newsDetailCache.isNewsDetailsCached(id)).thenReturn(Single.just(isCached))
    }

    private fun stubIsExpired(id: String, isExpired: Boolean) {
        Mockito.`when`(newsDetailCache.isNewsDetailsExpired(id)).thenReturn(Single.just(isExpired))
    }

    private fun stubGetDataStore(isCached: Boolean, isExpired: Boolean, dataStore: NewsDetailDataStore) {
        Mockito.`when`(newsDetailDataStoreFactory.getDataStore(isCached, isExpired)).thenReturn(dataStore)
    }

    private fun stubGetNewsDetailRemote(id: String, data: NewsDetailData) {
        Mockito.`when`(newsDetailRemoteDataStore.getNewsDetailData(id)).thenReturn(Single.just(data))
    }

    private fun stubGetNewsDetailCache(id: String, data: NewsDetailData) {
        Mockito.`when`(newsDetailCacheDataStore.getNewsDetailData(id)).thenReturn(Single.just(data))
    }

    private fun stubSaveNewsDetail(id: String, data: NewsDetailData, completable: Completable) {
        Mockito.`when`(newsDetailCache.saveNewsDetails(id, data)).thenReturn(completable)
    }

    private fun stubClearNewsDetail(id: String, completable: Completable) {
        Mockito.`when`(newsDetailCache.clearNewsDetails(id)).thenReturn(completable)
    }

    private fun stubMapper(newsDetailData: NewsDetailData, newsDetail: NewsDetail) {
        Mockito.`when`(newsDetailMapper.mapToDomain(newsDetailData)).thenReturn(newsDetail)
    }
 }