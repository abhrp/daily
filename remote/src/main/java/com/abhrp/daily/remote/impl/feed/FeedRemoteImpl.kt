package com.abhrp.daily.remote.impl.feed

import com.abhrp.daily.data.model.feed.FeedDataItem
import com.abhrp.daily.data.repository.feed.FeedRemote
import com.abhrp.daily.remote.mapper.feed.FeedItemMapper
import com.abhrp.daily.remote.service.DailyServiceFactory
import io.reactivex.Single
import javax.inject.Inject

class FeedRemoteImpl @Inject constructor(private val dailyServiceFactory: DailyServiceFactory, private val itemMapper: FeedItemMapper): FeedRemote {
    /**
     * Gets the feed data from the remote layer
     * @param pageNo: Page number for which to get the feed data
     * @return Single<List<FeedDataItem>>
     */
    override fun getFeedItemData(pageNo: Int): Single<List<FeedDataItem>> {
        return dailyServiceFactory.feedService().getFeedItems(pageNo = pageNo)
            .map { data ->
                itemMapper.mapToEntity(data.response)
            }
    }

}