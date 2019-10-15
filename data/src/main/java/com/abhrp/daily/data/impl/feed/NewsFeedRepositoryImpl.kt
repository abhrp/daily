package com.abhrp.daily.data.impl.feed

import com.abhrp.daily.common.util.TimeProvider
import com.abhrp.daily.data.mapper.feed.FeedItemEntityMapper
import com.abhrp.daily.data.model.feed.FeedDataItem
import com.abhrp.daily.data.repository.feed.FeedCache
import com.abhrp.daily.data.repository.feed.FeedDataStore
import com.abhrp.daily.data.store.feed.FeedDataStoreFactory
import com.abhrp.daily.data.store.feed.FeedRemoteDataStore
import com.abhrp.daily.domain.model.feed.FeedItem
import com.abhrp.daily.domain.repository.feed.NewsFeedRepository
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class NewsFeedRepositoryImpl @Inject constructor(
    private val feedDataStoreFactory: FeedDataStoreFactory,
    private val feedCache: FeedCache,
    private val feedItemEntityMapper: FeedItemEntityMapper,
    private val timeProvider: TimeProvider
) : NewsFeedRepository {
    /**
     * Gets the feed items from the data layer
     * @param isFirstPage: Determines if the request is for the very first page
     * @param isNewRequest: Determines if the request is new, for eg, when the user refreshes the feed
     * @return a list of feed items
     * @see FeedItem
     */
    override fun getFeedItems(isFirstPage: Boolean, isNewRequest: Boolean): Single<List<FeedItem>> {
        return getNextPageNumber(isFirstPage)
            .flatMap { checkForNewRequest(it, isNewRequest) }
            .flatMap { pageNo ->
                getDataStore(pageNo)
                    .flatMap { dataStore ->
                        getFeedData(dataStore, pageNo)
                    }
            }
    }

    /**
     * Gets the latest page number from the cache and then increments it to get next page number
     * @param isFirstPage If it's the first request, page number is returned as one
     */
    private fun getNextPageNumber(isFirstPage: Boolean): Single<Int> {
        return if (isFirstPage) Single.just(1) else feedCache.getCurrentPageNumber().flatMap {
            Single.just(
                it + 1
            )
        }
    }

    /**
     * Checks if it's a new request, if so, clears all the data from the cache, to serve completely fresh content to the user.
     * @param pageNo Page number to return if not a new request
     * @param isNewRequest Determines if it's a new request
     */
    private fun checkForNewRequest(pageNo: Int, isNewRequest: Boolean): Single<Int> {
        return if (isNewRequest) feedCache.clearAllFeedItemData().andThen(Single.just(1)) else Single.just(
            pageNo
        )
    }

    /**
     * Checks if the content requested is present in the cache and not expired and returns the correct data store as per the conditions
     * @param pageNo Page number for which to check the content in the cache
     */
    private fun getDataStore(pageNo: Int): Single<FeedDataStore> {
        return Single.zip(feedCache.isFeedItemDataCached(pageNo),
            feedCache.isFeedItemDataExpired(pageNo),
            BiFunction<Boolean, Boolean, Pair<Boolean, Boolean>> { isCached, isExpired ->
                Pair(isCached, isExpired)
            }).flatMap {
            Single.just(feedDataStoreFactory.getFeedDataStore(it.first, it.second))
        }
    }

    /**
     * Gets the Feed data from either the cache or remote layer, depending on the kind of data store provided.
     * If the data store is of type remote, the data for that page number is first cleared, and then new data is saved against the same page number.
     * The current page number is also set in the cache
     * @param dataStore Data store with which to make the request
     * @param pageNo Page number of the feed
     */
    private fun getFeedData(dataStore: FeedDataStore, pageNo: Int): Single<List<FeedItem>> {
        return dataStore.getFeedItemData(pageNo)
            .flatMap { feedData ->
                if (dataStore is FeedRemoteDataStore) {
                    clearDataFromCache(pageNo)
                        .andThen(feedCache.saveFeedItemData(pageNo, feedData))
                        .andThen(feedCache.saveLastCacheTime(pageNo, timeProvider.currentTime))
                        .andThen(feedCache.setCurrentPageNumber(pageNo))
                        .andThen(mapToDomain(feedData))
                } else {
                    feedCache.setCurrentPageNumber(pageNo)
                        .andThen(mapToDomain(feedData))
                }
            }
    }

    /**
     * Clears the data from the cache for a given page no. If the value of page number is 1,
     * then all data is cleared since there is a chance of duplicate feed items appearing in the subsequent pages.
     * @param pageNo Page number to cleared
     */
    private fun clearDataFromCache(pageNo: Int): Completable {
        return if (pageNo == 1)
            feedCache.clearAllFeedItemData().andThen(feedCache.clearAllCacheTime())
        else
            feedCache.clearFeedItemData(pageNo).andThen(feedCache.clearLastCacheTime(pageNo))
    }

    /**
     * Maps the data layer model to domain layer model
     * @param feedData Feed data received from either the cache or remote layer
     */
    private fun mapToDomain(feedData: List<FeedDataItem>): Single<List<FeedItem>> {
        return Single.just(feedData.map {
            feedItemEntityMapper.mapToDomain(it)
        })
    }

}