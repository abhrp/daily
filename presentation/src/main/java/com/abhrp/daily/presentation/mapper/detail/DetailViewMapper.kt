package com.abhrp.daily.presentation.mapper.detail

import com.abhrp.daily.domain.model.detail.NewsDetail
import com.abhrp.daily.presentation.mapper.ViewItemMapper
import com.abhrp.daily.presentation.model.detail.DetailViewItem
import javax.inject.Inject

class DetailViewMapper @Inject constructor(): ViewItemMapper<NewsDetail, DetailViewItem> {
    override fun mapToView(d: NewsDetail): DetailViewItem {
        return DetailViewItem(d.id, d.sectionName, d.headline, d.byline, d.body, d.thumbnail, d.publicationDate)
    }
}