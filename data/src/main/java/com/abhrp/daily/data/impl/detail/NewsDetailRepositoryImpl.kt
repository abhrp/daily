package com.abhrp.daily.data.impl.detail

import com.abhrp.daily.data.mapper.detail.NewsDetailMapper
import com.abhrp.daily.data.model.detail.NewsDetailData
import com.abhrp.daily.data.repository.detail.NewsDetailCache
import com.abhrp.daily.data.repository.detail.NewsDetailDataStore
import com.abhrp.daily.data.store.detail.NewsDetailDataStoreFactory
import com.abhrp.daily.data.store.detail.NewsDetailRemoteDataStore
import com.abhrp.daily.domain.model.detail.NewsDetail
import com.abhrp.daily.domain.repository.detail.NewsDetailRepository
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class NewsDetailRepositoryImpl @Inject constructor(private val newsDetailDataStoreFactory: NewsDetailDataStoreFactory, private val newsDetailCache: NewsDetailCache, private val newsDetailMapper: NewsDetailMapper): NewsDetailRepository {

    override fun getNewsDetails(id: String): Single<NewsDetail?> {
        return Single.zip(newsDetailCache.isNewsDetailsCached(id),
            newsDetailCache.isNewsDetailsExpired(id),
            BiFunction<Boolean, Boolean, Pair<Boolean, Boolean>> {
                cached, expired ->
                Pair(cached, expired)
            })
            .flatMap {
                val dataStore = newsDetailDataStoreFactory.getDataStore(it.first, it.second)
                getData(id, dataStore)
            }
    }

    private fun getData(id: String, dataStore: NewsDetailDataStore): Single<NewsDetail?> {
        return dataStore.getNewsDetailData(id)
            .flatMap { data ->
                if (dataStore is NewsDetailRemoteDataStore) {
                    newsDetailCache.clearNewsDetails(id).andThen(newsDetailCache.saveNewsDetails(id, data)).andThen(mapToDomain(data))
                } else {
                    mapToDomain(data)
                }
            }
    }

    private fun mapToDomain(newsDetailData: NewsDetailData): Single<NewsDetail> {
        return Single.just(newsDetailMapper.mapToDomain(newsDetailData))
    }
}