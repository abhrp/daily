package com.abhrp.daily.data.repository.feed

import com.abhrp.daily.data.model.feed.FeedDataItem
import io.reactivex.Single

interface FeedRemote {

    /**
     * Gets the feed data from the remote layer
     * @param pageNo: Page number for which to get the feed data
     * @return Single<List<FeedDataItem>>
     */
    fun getFeedItemData(pageNo: Int): Single<List<FeedDataItem>>
}