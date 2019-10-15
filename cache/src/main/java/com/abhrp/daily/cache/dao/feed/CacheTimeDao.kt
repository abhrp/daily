package com.abhrp.daily.cache.dao.feed

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abhrp.daily.cache.constants.CacheSQLConstants
import com.abhrp.daily.cache.model.feed.CachedTimeItem
import io.reactivex.Maybe

@Dao
interface CacheTimeDao {
    @Query(CacheSQLConstants.SELECT_CACHE_TIME)
    fun getLastCacheTime(pageNo: Int): Maybe<CachedTimeItem>

    @Query(CacheSQLConstants.DELETE_CACHE_ITEM)
    fun clearCacheTime(pageNo: Int)

    @Query(CacheSQLConstants.DELETE_ALL_CACHE)
    fun clearAllCacheTime()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCacheTime(cachedTimeItem: CachedTimeItem)
}