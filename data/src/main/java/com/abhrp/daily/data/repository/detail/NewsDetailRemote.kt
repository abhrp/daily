package com.abhrp.daily.data.repository.detail

import com.abhrp.daily.data.model.detail.NewsDetailData
import io.reactivex.Single

interface NewsDetailRemote {

    /**
     * Method to get details from remote layer
     * @param id ID to query remote for
     */
    fun getNewsDetails(id: String): Single<NewsDetailData?>
}