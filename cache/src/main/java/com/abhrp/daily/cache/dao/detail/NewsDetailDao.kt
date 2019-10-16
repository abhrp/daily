package com.abhrp.daily.cache.dao.detail

import androidx.room.Dao
import androidx.room.Query
import com.abhrp.daily.cache.constants.CacheSQLConstants
import com.abhrp.daily.cache.model.feed.CachedFeedItem
import io.reactivex.Maybe

@Dao
interface NewsDetailDao {
    @Query(CacheSQLConstants.SELECT_NEWS_DETAIL)
    fun getNewsDetails(id: String): Maybe<CachedFeedItem>

    @Query(CacheSQLConstants.UPDATE_WITH_DETAIL)
    fun saveNewsDetails(id: String, body: String, byline: String, lastCacheTime: Long)

    @Query(CacheSQLConstants.UPDATE_WITH_DETAIL)
    fun clearNewsDetails(id: String, body: String?, byline: String?, lastCacheTime: Long?)
}