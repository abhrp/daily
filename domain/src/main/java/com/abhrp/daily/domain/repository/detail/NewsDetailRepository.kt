package com.abhrp.daily.domain.repository.detail

import com.abhrp.daily.domain.model.detail.NewsDetail
import io.reactivex.Single

interface NewsDetailRepository {
    fun getNewsDetails(id: String): Single<NewsDetail?>
}