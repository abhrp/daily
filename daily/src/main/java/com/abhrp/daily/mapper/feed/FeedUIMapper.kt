package com.abhrp.daily.mapper.feed

import com.abhrp.daily.common.util.DateProvider
import com.abhrp.daily.mapper.UIMapper
import com.abhrp.daily.model.feed.FeedUIItem
import com.abhrp.daily.presentation.model.feed.FeedViewItem
import javax.inject.Inject

class FeedUIMapper @Inject constructor(private val dateProvider: DateProvider): UIMapper<FeedViewItem, FeedUIItem> {
    override fun mapToUIView(p: FeedViewItem): FeedUIItem {
        return FeedUIItem(p.id, p.thumbnail, p.headline, dateProvider.getFormattedDate(p.publicationDate), p.sectionName)
    }
}