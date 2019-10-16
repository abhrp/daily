package com.abhrp.daily.cache.impl.feed

import com.abhrp.daily.cache.constants.CacheSQLConstants
import com.abhrp.daily.cache.mapper.feed.FeedItemMapper
import com.abhrp.daily.cache.model.feed.CachedTimeItem
import com.abhrp.daily.cache.sharedpreferences.DailySharedPreferences
import com.abhrp.daily.cache.sql.DailyDatabase
import com.abhrp.daily.common.util.AppLogger
import com.abhrp.daily.common.util.TimeProvider
import com.abhrp.daily.data.model.feed.FeedDataItem
import com.abhrp.daily.data.repository.feed.FeedCache
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class FeedCacheImpl @Inject constructor(private val dailyDatabase: DailyDatabase, private val dailySharedPreferences: DailySharedPreferences, private val itemMapper: FeedItemMapper, private val timerProvider: TimeProvider): FeedCache {

    @Inject
    lateinit var logger: AppLogger

    /**
     * Gets the feed data for a particular pageno from the cache
     * @param pageNo: Page number for which the data is required
     * @return Single<List<FeedDataItem>>
     */
    override fun getFeedItemData(pageNo: Int): Single<List<FeedDataItem>> {
        return dailyDatabase.getFeedDao().getFeedItems(pageNo)
            .toSingle()
            .map { cachedItem ->
                cachedItem.map {
                    itemMapper.mapToEntity(it)
                }
            }.doOnError {
                logger.logThrowable(it)
            }.onErrorReturnItem(emptyList())
    }

    /**
     * Saves the feed data for a given page number in the cache
     * @param pageNo: Page number of the feed data
     * @param feedDataItems: Data to be saved
     */
    override fun saveFeedItemData(pageNo: Int, feedDataItems: List<FeedDataItem>): Completable {
        return Completable.defer {
            val list = feedDataItems.map { itemMapper.mapToCache(it) }
            dailyDatabase.getFeedDao().saveFeedItems(list)
            Completable.complete()
        }
    }

    /**
     * Saves the last cache time for a given page number
     * @param pageNo page number for which to save the last cache time
     * @param currentTime current time in millis
     */
    override fun saveLastCacheTime(pageNo: Int, currentTime: Long): Completable {
        return Completable.defer {
            dailyDatabase.getCacheTimeDao().saveCacheTime(CachedTimeItem(pageNo, currentTime))
            Completable.complete()
        }
    }

    /**
     * Clears the feed data for a particular page number
     * @param pageNo: The page no for which to clear the feed data
     */
    override fun clearFeedItemData(pageNo: Int): Completable {
        return Completable.defer {
            dailyDatabase.getFeedDao().clearFeedItem(pageNo)
            Completable.complete()
        }
    }

    /**
     * Cleats last cache time of a page number
     * @param pageNo Page number for which to clear the time
     */
    override fun clearLastCacheTime(pageNo: Int): Completable {
        return Completable.defer {
            dailyDatabase.getCacheTimeDao().clearCacheTime(pageNo)
            Completable.complete()
        }
    }

    /**
     * Determines is the feed data for a given page number is cached or not
     * @param pageNo: Page number of the feed data to be checked
     * @return Single<Boolean>
     */
    override fun isFeedItemDataCached(pageNo: Int): Single<Boolean> {
        return dailyDatabase.getFeedDao().getFeedItems(pageNo).isEmpty.map { !it }
    }

    /**
     * Determines if the feed data for a given page number is past the cache expiry time.
     * @param pageNo: Page number to be checked
     * @return Single<Boolean>
     */
    override fun isFeedItemDataExpired(pageNo: Int): Single<Boolean> {
        return dailyDatabase.getCacheTimeDao().getLastCacheTime(pageNo).toSingle().map {
            isExpired(it)
        }.onErrorReturn { true }
    }

    /**
     * Gets the next page number for the request
     */
    override fun getCurrentPageNumber(): Single<Int> {
        return Single.just(dailySharedPreferences.currentPageNumber)
    }

    /**
     * Sets the current page number
     * @param pageNo: Page number to set
     */
    override fun setCurrentPageNumber(pageNo: Int): Completable {
        return Completable.defer {
            dailySharedPreferences.currentPageNumber = pageNo
            Completable.complete()
        }
    }

    /**
     * Clears the cache completely
     * @return Completable
     * @see Completable
     */
    override fun clearAllFeedItemData(): Completable {
        return Completable.defer {
            dailyDatabase.getFeedDao().clearAllFeedItems()
            Completable.complete()
        }
    }

    /**
     * Clears all the cache time set completely
     */
    override fun clearAllCacheTime(): Completable {
        return Completable.defer {
            dailyDatabase.getCacheTimeDao().clearAllCacheTime()
            Completable.complete()
        }
    }

    private fun isExpired(cachedTimeItem: CachedTimeItem): Boolean {
        val cacheTime = cachedTimeItem.lastCacheTime
        val currentTime = timerProvider.currentTime
        val expirationTime = CacheSQLConstants.CACHE_TIME_OUT.toLong()
        return currentTime - cacheTime > expirationTime
    }
}