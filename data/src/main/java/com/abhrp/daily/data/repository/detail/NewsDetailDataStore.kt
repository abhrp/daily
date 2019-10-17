package com.abhrp.daily.data.repository.detail

import com.abhrp.daily.data.model.detail.NewsDetailData
import io.reactivex.Single

interface NewsDetailDataStore {

    /**
     * Method to fetch details from data layer
     * @param id ID of the article to fetch details for
     */
    fun getNewsDetailData(id: String): Single<NewsDetailData?>
}