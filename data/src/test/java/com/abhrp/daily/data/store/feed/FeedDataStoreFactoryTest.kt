package com.abhrp.daily.data.store.feed

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class FeedDataStoreFactoryTest {
    private lateinit var feedDataStoreFactory: FeedDataStoreFactory

    @Mock
    private lateinit var feedCacheDataStore: FeedCacheDataStore
    @Mock
    private lateinit var feedRemoteDataStore: FeedRemoteDataStore

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        feedDataStoreFactory = FeedDataStoreFactory(feedCacheDataStore, feedRemoteDataStore)
    }

    @Test
    fun testGetDataStoreReturnsCacheDataStore() {
        val feedDataStore = feedDataStoreFactory.getFeedDataStore(
            isCached = true,
            isExpired = false
        )
        Assert.assertEquals(feedDataStore, feedCacheDataStore)
    }

    @Test
    fun testGetDataStoreReturnsRemoteDataStore() {
        Assert.assertEquals(feedRemoteDataStore, feedDataStoreFactory.getFeedDataStore(
            isCached = true,
            isExpired = true
        ))
        Assert.assertEquals(feedRemoteDataStore, feedDataStoreFactory.getFeedDataStore(
            isCached = false,
            isExpired = true
        ))
        Assert.assertEquals(feedRemoteDataStore, feedDataStoreFactory.getFeedDataStore(
            isCached = false,
            isExpired = false
        ))
    }

    @Test
    fun testGetCacheDataStoreReturnsCorrectly() {
        Assert.assertEquals(feedCacheDataStore, feedDataStoreFactory.getFeedCacheDataStore())
    }
}