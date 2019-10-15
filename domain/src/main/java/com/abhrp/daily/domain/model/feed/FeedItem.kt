package com.abhrp.daily.domain.model.feed

data class FeedItem(val id: String,
                    val sectionName: String,
                    val webUrl: String,
                    val headline: String,
                    val wordCount: String,
                    val thumbnail: String)