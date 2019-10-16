package com.abhrp.daily.cache.impl.detail

import com.abhrp.daily.cache.constants.CacheSQLConstants
import com.abhrp.daily.cache.mapper.detail.NewsDetailMapper
import com.abhrp.daily.cache.model.feed.CachedFeedItem
import com.abhrp.daily.cache.sql.DailyDatabase
import com.abhrp.daily.common.util.TimeProvider
import com.abhrp.daily.data.model.detail.NewsDetailData
import com.abhrp.daily.data.repository.detail.NewsDetailCache
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class NewsDetailCacheImpl @Inject constructor(private val dailyDatabase: DailyDatabase, private val itemMapper: NewsDetailMapper, private val timeProvider: TimeProvider): NewsDetailCache {

    override fun getNewsDetails(id: String): Single<NewsDetailData?> {
        return dailyDatabase.getNewsDetailDao().getNewsDetails(id).toSingle().map {
            itemMapper.mapToEntity(it)
        }
    }

    override fun saveNewsDetails(id: String, newsDetailData: NewsDetailData): Completable {
        return Completable.defer {
            dailyDatabase.getNewsDetailDao().saveNewsDetails(id, newsDetailData.body, newsDetailData.byline, timeProvider.currentTime)
            Completable.complete()
        }
    }

    override fun isNewsDetailsCached(id: String): Single<Boolean> {
        return dailyDatabase.getNewsDetailDao().getNewsDetails(id).toSingle().map {
            it.body != null && it.body.isNotEmpty()
        }.onErrorReturnItem(false)
    }

    override fun isNewsDetailsExpired(id: String): Single<Boolean> {
        return dailyDatabase.getNewsDetailDao().getNewsDetails(id).toSingle().map {
            isExpired(it)
        }.onErrorReturnItem(true)
    }

    override fun clearNewsDetails(id: String): Completable {
        return Completable.defer {
            dailyDatabase.getNewsDetailDao().clearNewsDetails(id, null, null, null)
            Completable.complete()
        }
    }

    private fun isExpired(cachedFeedItem: CachedFeedItem): Boolean {
        val cacheTime = cachedFeedItem.lastCacheTime ?: 0
        val currentTime = timeProvider.currentTime
        val expirationTime = CacheSQLConstants.CACHE_TIME_OUT.toLong()
        return currentTime - cacheTime > expirationTime
    }
}