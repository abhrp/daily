package com.abhrp.daily.cache.model.feed

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abhrp.daily.cache.constants.CacheSQLConstants

@Entity(tableName = CacheSQLConstants.TABLE_ARTICLE)
data class CachedFeedItem(
    @PrimaryKey
    @ColumnInfo(name = CacheSQLConstants.COL_ARTICLE_ID)
    val articleId: String,

    @ColumnInfo(name = CacheSQLConstants.COL_DATE)
    val date: String,

    @ColumnInfo(name = CacheSQLConstants.COL_SECTION_NAME)
    val sectionName: String,

    @ColumnInfo(name = CacheSQLConstants.COL_UPDATE_TIME)
    val updateTime: Long,

    @ColumnInfo(name = CacheSQLConstants.COL_WEB_URL)
    val webUrl: String,

    @ColumnInfo(name = CacheSQLConstants.COL_HEADLINE)
    val headline: String,

    @ColumnInfo(name = CacheSQLConstants.COL_WORD_COUNT)
    val wordCount: String,

    @ColumnInfo(name = CacheSQLConstants.COL_THUMBNAIL)
    val thumbnail: String,

    @ColumnInfo(name = CacheSQLConstants.COL_BODY)
    val body: String?,

    @ColumnInfo(name = CacheSQLConstants.COL_BYLINE)
    val byline: String?,

    @ColumnInfo(name = CacheSQLConstants.COL_PAGE_NO)
    val pageNo: Int,

    @ColumnInfo(name = CacheSQLConstants.LAST_CACHE_TIME)
    val lastCacheTime: Long?
)