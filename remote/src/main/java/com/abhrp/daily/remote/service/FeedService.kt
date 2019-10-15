package com.abhrp.daily.remote.service

import com.abhrp.daily.remote.constants.RemoteConstants
import com.abhrp.daily.remote.model.BaseResponse
import com.abhrp.daily.remote.model.feed.FeedItemResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface FeedService {
    @GET(RemoteConstants.SEARCH_API)
    fun getFeedItems(@Query(RemoteConstants.PAGE_NO_PARAM) pageNo: Int): Single<BaseResponse<FeedItemResponse>>
}