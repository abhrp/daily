package com.abhrp.daily.remote.model.detail

import com.abhrp.daily.remote.model.feed.FeedItem
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DetailResponse(@Json(name = "status") val status: String,
                          @Json(name = "content") val details: FeedItem)