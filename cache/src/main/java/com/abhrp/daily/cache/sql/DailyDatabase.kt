package com.abhrp.daily.cache.sql

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.abhrp.daily.cache.dao.feed.CacheTimeDao
import com.abhrp.daily.cache.dao.feed.FeedDao
import com.abhrp.daily.cache.model.feed.CachedFeedItem
import com.abhrp.daily.cache.model.feed.CachedTimeItem

@Database(entities = [CachedFeedItem::class, CachedTimeItem::class], version = 1)
abstract class DailyDatabase: RoomDatabase() {
    abstract fun getFeedDao(): FeedDao
    abstract fun getCacheTimeDao(): CacheTimeDao

    companion object {
        private var INSTANCE: DailyDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): DailyDatabase {
            if (INSTANCE == null) {
                synchronized(lock) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext, DailyDatabase::class.java, "daily.db").build()
                    }
                    return INSTANCE as DailyDatabase
                }
            }
            return INSTANCE as DailyDatabase
        }
    }
}