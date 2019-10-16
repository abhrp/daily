package com.abhrp.daily.remote.model.feed

import com.google.gson.annotations.JsonAdapter
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Fields(@Json(name = "headline") val headline: String,
                  @Json(name = "wordcount") val wordCount: String?,
                  @Json(name = "thumbnail") val thumbnail: String?)