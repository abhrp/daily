package com.abhrp.daily.domain.repository.feed

import com.abhrp.daily.domain.model.feed.FeedItem
import io.reactivex.Single

interface NewsFeedRepository {

    /**
     * Gets the feed items from the data layer
     * @param isFirstPage: Determines if the request is for the very first page
     * @param isNewRequest: Determines if the request is new, for eg, when the user refreshes the feed
     * @return a list of feed items
     * @see FeedItem
     */
    fun getFeedItems(isFirstPage: Boolean, isNewRequest: Boolean): Single<List<FeedItem>>
}