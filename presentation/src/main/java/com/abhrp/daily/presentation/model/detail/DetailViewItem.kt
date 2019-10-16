package com.abhrp.daily.presentation.model.detail

data class DetailViewItem(val id: String,
                          val sectionName: String,
                          val headline: String,
                          val byline: String,
                          val body: String,
                          val thumbnail: String,
                          val publicationDate: String)