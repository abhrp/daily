package com.abhrp.daily.cache.model.feed

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abhrp.daily.cache.constants.CacheSQLConstants

@Entity(tableName = CacheSQLConstants.TABLE_CACHE_TIME)
data class CachedTimeItem(
    @PrimaryKey
    @ColumnInfo(name = CacheSQLConstants.COL_PAGE_NO)
    val pageNo: Int,

    @ColumnInfo(name = CacheSQLConstants.COL_LAST_CACHE_TIME)
    val lastCacheTime: Long
)