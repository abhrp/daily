package com.abhrp.daily.data.store.detail

import com.abhrp.daily.data.model.detail.NewsDetailData
import com.abhrp.daily.data.repository.detail.NewsDetailDataStore
import com.abhrp.daily.data.repository.detail.NewsDetailRemote
import io.reactivex.Single
import javax.inject.Inject

class NewsDetailRemoteDataStore @Inject constructor(private val newsDetailRemote: NewsDetailRemote): NewsDetailDataStore {
    override fun getNewsDetailData(id: String): Single<NewsDetailData?> {
        return newsDetailRemote.getNewsDetails(id)
    }
}