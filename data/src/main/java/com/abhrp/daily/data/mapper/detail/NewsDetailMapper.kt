package com.abhrp.daily.data.mapper.detail

import com.abhrp.daily.data.mapper.EntityMapper
import com.abhrp.daily.data.model.detail.NewsDetailData
import com.abhrp.daily.domain.model.detail.NewsDetail
import javax.inject.Inject

class NewsDetailMapper @Inject constructor(): EntityMapper<NewsDetailData, NewsDetail> {
    /**
     * Maps an entity(data) model to domain model
     * @param e: Entity(data) model
     * @return D Domain model
     */
    override fun mapToDomain(e: NewsDetailData): NewsDetail {
        return NewsDetail(e.id, e.sectionName, e.headline, e.byline, e.body, e.thumbnail, e.publicationDate)
    }

}