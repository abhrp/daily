package com.abhrp.daily.data.model.feed

data class FeedDataItem(val id: String,
                        val sectionName: String,
                        val webUrl: String,
                        val headline: String,
                        val wordCount: String,
                        val thumbnail: String,
                        val pageNo: Int)