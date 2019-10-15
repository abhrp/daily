package com.abhrp.daily.data.store.feed

import com.abhrp.daily.data.repository.feed.FeedDataStore
import javax.inject.Inject

class FeedDataStoreFactory @Inject constructor(private val feedCacheDataStore: FeedCacheDataStore, private val feedRemoteDataStore: FeedRemoteDataStore) {

    fun getFeedDataStore(isCached: Boolean, isExpired: Boolean): FeedDataStore {
        return if (isCached && !isExpired) feedCacheDataStore else feedRemoteDataStore
    }

    fun getFeedCacheDataStore(): FeedCacheDataStore = feedCacheDataStore
}