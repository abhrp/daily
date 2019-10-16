package com.abhrp.daily.data.repository.detail

import com.abhrp.daily.data.model.detail.NewsDetailData
import io.reactivex.Single

interface NewsDetailRemote {
    fun getNewsDetails(id: String): Single<NewsDetailData?>
}