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

    /**
     * Maps a domain model to entity(data) model
     * @param d: Domain model
     * @return E Entity(data) model
     */
    override fun mapToEntity(d: NewsDetail): NewsDetailData {
        return NewsDetailData(d.id, d.sectionName, d.headline, d.byline, d.body, d.thumbnail, d.publicationDate)
    }
}