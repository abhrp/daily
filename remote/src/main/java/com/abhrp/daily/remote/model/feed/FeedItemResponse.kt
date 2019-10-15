package com.abhrp.daily.remote.model.feed

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FeedItemResponse(@Json(name = "pageSize") val pageSize: Int,
                            @Json(name = "currentPage") val pageNo: Int,
                            @Json(name = "status") val status: String,
                            @Json(name = "results") val feedItems: List<FeedItem>)