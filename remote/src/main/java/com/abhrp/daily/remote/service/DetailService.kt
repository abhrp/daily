package com.abhrp.daily.remote.service

import com.abhrp.daily.remote.constants.RemoteConstants
import com.abhrp.daily.remote.model.BaseResponse
import com.abhrp.daily.remote.model.detail.DetailResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface DetailService {
    @GET
    fun getDetails(@Url id: String, @Query(RemoteConstants.SHOW_FIELDS_PARAM) params: String): Single<BaseResponse<DetailResponse>>
}