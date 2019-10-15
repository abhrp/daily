package com.abhrp.daily.cache.dao.feed

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abhrp.daily.cache.constants.CacheSQLConstants
import com.abhrp.daily.cache.model.feed.CachedFeedItem
import io.reactivex.Maybe

@Dao
interface FeedDao {
    @Query(CacheSQLConstants.SELECT_FEED_ITEMS)
    fun getFeedItems(pageNo: Int): Maybe<List<CachedFeedItem>>

    @Query(CacheSQLConstants.DELETE_FEED_ITEMS)
    fun clearFeedItem(pageNo: Int)

    @Query(CacheSQLConstants.DELETE_ALL_FEEDS)
    fun clearAllFeedItems()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFeedItems(feedItems: List<CachedFeedItem>)
}