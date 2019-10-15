package com.abhrp.daily.presentation.mapper.feed

import com.abhrp.daily.domain.model.feed.FeedItem
import com.abhrp.daily.presentation.mapper.ViewItemMapper
import com.abhrp.daily.presentation.model.feed.FeedViewItem
import javax.inject.Inject

class FeedViewItemMapper @Inject constructor(): ViewItemMapper<FeedItem, FeedViewItem> {
    override fun mapToView(d: FeedItem): FeedViewItem {
        return FeedViewItem(d.id, d.thumbnail, d.publicationDate, d.sectionName, d.headline)
    }
}