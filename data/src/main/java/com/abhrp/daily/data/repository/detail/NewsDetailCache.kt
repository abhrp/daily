package com.abhrp.daily.data.repository.detail

import com.abhrp.daily.data.model.detail.NewsDetailData
import io.reactivex.Completable
import io.reactivex.Single

interface NewsDetailCache {
    fun getNewsDetails(id: String): Single<NewsDetailData?>
    fun saveNewsDetails(id: String, newsDetailData: NewsDetailData): Completable
    fun isNewsDetailsCached(id: String): Single<Boolean>
    fun isNewsDetailsExpired(id: String): Single<Boolean>
    fun clearNewsDetails(id: String): Completable
}