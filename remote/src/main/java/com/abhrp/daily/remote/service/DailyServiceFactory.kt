package com.abhrp.daily.remote.service

import com.abhrp.daily.remote.http.RetrofitProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DailyServiceFactory @Inject constructor(retrofitProvider: RetrofitProvider) {

    val feedService: FeedService = retrofitProvider.retrofitClient.create(FeedService::class.java)
    val detailService: DetailService = retrofitProvider.retrofitClient.create(DetailService::class.java)
}