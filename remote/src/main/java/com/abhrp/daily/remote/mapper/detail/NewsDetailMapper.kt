package com.abhrp.daily.remote.mapper.detail

import com.abhrp.daily.data.model.detail.NewsDetailData
import com.abhrp.daily.remote.mapper.RemoteEntityMapper
import com.abhrp.daily.remote.model.detail.DetailResponse
import javax.inject.Inject

class NewsDetailMapper @Inject constructor(): RemoteEntityMapper<DetailResponse, NewsDetailData> {
    override fun mapToEntity(m: DetailResponse): NewsDetailData {
        val details = m.details
        return NewsDetailData(details.id,
            details.sectionName,
            details.fields.headline,
            details.fields.byline ?: "",
            details.fields.body ?: "",
            details.fields.thumbnail ?: "",
            details.publicationDate)
    }
}