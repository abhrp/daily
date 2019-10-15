package com.abhrp.daily.remote.model.feed

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FeedItem(@Json(name = "id") val id: String,
                    @Json(name = "sectionName") val sectionName: String,
                    @Json(name = "webPublicationDate") val publicationDate: String,
                    @Json(name = "webUrl") val webUrl: String,
                    @Json(name = "fields") val fields: Fields)