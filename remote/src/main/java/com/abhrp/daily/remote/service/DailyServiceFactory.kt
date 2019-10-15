package com.abhrp.daily.remote.service

import com.abhrp.daily.remote.http.RetrofitProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DailyServiceFactory @Inject constructor(private val retrofitProvider: RetrofitProvider) {

    val feedService: FeedService = retrofitProvider.retrofitClient.create(FeedService::class.java)

}