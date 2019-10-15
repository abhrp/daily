package com.abhrp.daily.data.store.feed

import com.abhrp.daily.data.model.feed.FeedDataItem
import com.abhrp.daily.data.repository.feed.FeedDataStore
import com.abhrp.daily.data.repository.feed.FeedRemote
import io.reactivex.Single
import javax.inject.Inject

class FeedRemoteDataStore @Inject constructor(private val feedRemote: FeedRemote): FeedDataStore {
    /**
     * Gets the feed data
     * @param pageNo: Page number for which to get the feed data
     * @return Single<List<FeedDataItem>>
     */
    override fun getFeedItemData(pageNo: Int): Single<List<FeedDataItem>> {
        return feedRemote.getFeedItemData(pageNo)
    }
}