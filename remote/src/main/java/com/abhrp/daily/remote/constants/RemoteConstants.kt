package com.abhrp.daily.remote.constants

object RemoteConstants {
    const val BASE_URL = "https://content.guardianapis.com/"
    const val SEARCH_API = "search?show-fields=thumbnail,headline,wordcount"
    const val SHOW_FIELDS_PARAM = "show-fields"
    const val PAGE_NO_PARAM = "page"
    const val API_KEY_PARAM = "api-key"
    const val DETAILS_PARAMS = "body,thumbnail,byline,headline"

    const val TIME_OUT = 60L
}