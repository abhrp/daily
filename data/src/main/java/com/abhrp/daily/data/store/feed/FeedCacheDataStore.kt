package com.abhrp.daily.data.store.feed

import com.abhrp.daily.data.model.feed.FeedDataItem
import com.abhrp.daily.data.repository.feed.FeedCache
import com.abhrp.daily.data.repository.feed.FeedDataStore
import io.reactivex.Single
import javax.inject.Inject

class FeedCacheDataStore @Inject constructor(private val feedCache: FeedCache): FeedDataStore {

    /**
     * Gets the feed data
     * @param pageNo: Page number for which to get the feed data
     * @return Single<List<FeedDataItem>>
     */
    override fun getFeedItemData(pageNo: Int): Single<List<FeedDataItem>> {
        return feedCache.getFeedItemData(pageNo)
    }

}