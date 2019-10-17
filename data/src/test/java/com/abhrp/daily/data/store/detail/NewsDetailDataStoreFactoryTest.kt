package com.abhrp.daily.data.store.detail

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class NewsDetailDataStoreFactoryTest {

    private lateinit var newsDetailDataStoreFactory: NewsDetailDataStoreFactory

    @Mock
    private lateinit var newsDetailRemoteDataStore: NewsDetailRemoteDataStore
    @Mock
    private lateinit var newsDetailCacheDataStore: NewsDetailCacheDataStore

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        newsDetailDataStoreFactory = NewsDetailDataStoreFactory(newsDetailCacheDataStore, newsDetailRemoteDataStore)
    }

    @Test
    fun testGetDataStoreReturnsCache() {
        val dataStore = newsDetailDataStoreFactory.getDataStore(isCached = true, isExpired = false)
        Assert.assertEquals(dataStore, newsDetailCacheDataStore)
    }

    @Test
    fun testGetDataStoreReturnsRemote() {
        Assert.assertEquals(newsDetailDataStoreFactory.getDataStore(
            isCached = false,
            isExpired = true
        ), newsDetailRemoteDataStore)

        Assert.assertEquals(newsDetailDataStoreFactory.getDataStore(
            isCached = true,
            isExpired = true
        ), newsDetailRemoteDataStore)

        Assert.assertEquals(newsDetailDataStoreFactory.getDataStore(
            isCached = false,
            isExpired = false
        ), newsDetailRemoteDataStore)
    }

    @Test
    fun testGetCacheDataStoreReturnCache() {
        Assert.assertEquals(newsDetailDataStoreFactory.getCacheDataStore(), newsDetailCacheDataStore)
    }
}