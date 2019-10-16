package com.abhrp.daily.data.store.detail

import com.abhrp.daily.data.repository.detail.NewsDetailDataStore
import javax.inject.Inject

class NewsDetailDataStoreFactory @Inject constructor(private val newsDetailCacheDataStore: NewsDetailCacheDataStore, private val newsDetailRemoteDataStore: NewsDetailRemoteDataStore) {

    fun getDataStore(isCached: Boolean, isExpired: Boolean): NewsDetailDataStore {
        return if(isCached && !isExpired) newsDetailCacheDataStore else newsDetailRemoteDataStore
    }

    fun getCacheDataStore(): NewsDetailCacheDataStore = newsDetailCacheDataStore
}