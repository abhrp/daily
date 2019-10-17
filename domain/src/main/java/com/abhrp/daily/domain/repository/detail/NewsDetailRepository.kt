package com.abhrp.daily.domain.repository.detail

import com.abhrp.daily.domain.model.detail.NewsDetail
import io.reactivex.Single

interface NewsDetailRepository {

    /**
     * Gets news details from the data layer
     * @param id ID of the article for which to fetch the details
     */
    fun getNewsDetails(id: String): Single<NewsDetail?>
}