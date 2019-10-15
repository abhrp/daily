package com.abhrp.daily.remote.service

import com.abhrp.daily.remote.http.RetrofitProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DailyServiceFactory @Inject constructor(private val retrofitProvider: RetrofitProvider) {

    fun feedService(): FeedService {
        return retrofitProvider.retrofitClient.create(FeedService::class.java)
    }

}