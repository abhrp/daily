package com.abhrp.daily.remote.impl.detail

import com.abhrp.daily.common.util.AppLogger
import com.abhrp.daily.data.model.detail.NewsDetailData
import com.abhrp.daily.data.repository.detail.NewsDetailRemote
import com.abhrp.daily.remote.constants.RemoteConstants
import com.abhrp.daily.remote.mapper.detail.NewsDetailMapper
import com.abhrp.daily.remote.service.DailyServiceFactory
import io.reactivex.Single
import javax.inject.Inject

class NewsDetailRemoteImpl @Inject constructor(private val dailyServiceFactory: DailyServiceFactory, private val detailsMapper: NewsDetailMapper): NewsDetailRemote {

    @Inject
    lateinit var logger: AppLogger

    override fun getNewsDetails(id: String): Single<NewsDetailData?> {
        return dailyServiceFactory.detailService.getDetails(id, RemoteConstants.DETAILS_PARAMS)
            .map { data ->
                detailsMapper.mapToEntity(data.response)
            }
    }
}