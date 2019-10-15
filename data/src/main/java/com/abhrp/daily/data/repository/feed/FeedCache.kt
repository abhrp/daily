package com.abhrp.daily.data.repository.feed

import com.abhrp.daily.data.model.feed.FeedDataItem
import io.reactivex.Completable
import io.reactivex.Single

interface FeedCache {

    /**
     * Gets the feed data for a particular pageno from the cache
     * @param pageNo: Page number for which the data is required
     * @return Single<List<FeedDataItem>>
     */
    fun getFeedItemData(pageNo: Int): Single<List<FeedDataItem>>

    /**
     * Saves the feed data for a given page number in the cache
     * @param pageNo: Page number of the feed data
     * @param feedDataItems: Data to be saved
     */
    fun saveFeedItemData(pageNo: Int, feedDataItems: List<FeedDataItem>): Completable

    /**
     * Saves the last cache time for a given page number
     * @param pageNo page number for which to save the last cache time
     * @param currentTime current time in millis
     */
    fun saveLastCacheTime(pageNo: Int, currentTime: Long): Completable

    /**
     * Clears the feed data for a particular page number
     * @param pageNo: The page no for which to clear the feed data
     */
    fun clearFeedItemData(pageNo: Int): Completable

    /**
     * Cleats last cache time of a page number
     * @param pageNo Page number for which to clear the time
     */
    fun clearLastCacheTime(pageNo: Int): Completable

    /**
     * Determines is the feed data for a given page number is cached or not
     * @param pageNo: Page number of the feed data to be checked
     * @return Single<Boolean>
     */
    fun isFeedItemDataCached(pageNo: Int): Single<Boolean>

    /**
     * Determines if the feed data for a given page number is past the cache expiry time.
     * @param pageNo: Page number to be checked
     * @return Single<Boolean>
     */
    fun isFeedItemDataExpired(pageNo: Int): Single<Boolean>

    /**
     * Gets the next page number for the request
     */
    fun getCurrentPageNumber(): Single<Int>

    /**
     * Sets the current page number
     * @param pageNo: Page number to set
     */
    fun setCurrentPageNumber(pageNo: Int): Completable

    /**
     * Clears the cache completely
     * @return Completable
     * @see Completable
     */
    fun clearAllFeedItemData(): Completable

    /**
     * Clears all the cache time set completely
     */
    fun clearAllCacheTime(): Completable
}