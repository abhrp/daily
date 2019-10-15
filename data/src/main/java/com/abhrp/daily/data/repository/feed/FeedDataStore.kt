package com.abhrp.daily.data.repository.feed

import com.abhrp.daily.data.model.feed.FeedDataItem
import io.reactivex.Single

interface FeedDataStore {
    /**
     * Gets the feed data
     * @param pageNo: Page number for which to get the feed data
     * @return Single<List<FeedDataItem>>
     */
    fun getFeedItemData(pageNo: Int): Single<List<FeedDataItem>>
}