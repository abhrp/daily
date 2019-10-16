package com.abhrp.daily.data.store.detail

import com.abhrp.daily.data.model.detail.NewsDetailData
import com.abhrp.daily.data.repository.detail.NewsDetailCache
import com.abhrp.daily.data.repository.detail.NewsDetailDataStore
import io.reactivex.Single
import javax.inject.Inject

class NewsDetailCacheDataStore @Inject constructor(private val newsDetailCache: NewsDetailCache): NewsDetailDataStore {
    override fun getNewsDetailData(id: String): Single<NewsDetailData?> {
        return newsDetailCache.getNewsDetails(id)
    }
}