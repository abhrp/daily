package com.abhrp.daily.cache.constants

object CacheSQLConstants {
    const val TABLE_ARTICLE = "article"
    const val COL_ARTICLE_ID = "article_id"
    const val COL_SECTION_NAME = "section_name"
    const val COL_PAGE_NO = "page_no"
    const val COL_DATE = "date"
    const val COL_UPDATE_TIME = "update_time"
    const val COL_WEB_URL = "web_url"
    const val COL_HEADLINE = "headline"
    const val COL_WORD_COUNT = "word_count"
    const val COL_THUMBNAIL = "thumbnail"
    const val COL_BODY = "body"
    const val COL_BYLINE = "byline"

    const val TABLE_CACHE_TIME = "cache_time"
    const val COL_LAST_CACHE_TIME = "last_cache_time"

    const val CACHE_TIME_OUT = 60 * 60 * 1000


    const val SELECT_FEED_ITEMS = "select * from $TABLE_ARTICLE where $COL_PAGE_NO=:pageNo"
    const val SELECT_CACHE_TIME = "select * from $TABLE_CACHE_TIME where $COL_PAGE_NO=:pageNo"

    const val DELETE_FEED_ITEMS = "delete from $TABLE_ARTICLE where $COL_PAGE_NO=:pageNo"
    const val DELETE_ALL_FEEDS = "delete from $TABLE_ARTICLE"
    
    const val DELETE_CACHE_ITEM = "delete from $TABLE_CACHE_TIME where $COL_PAGE_NO=:pageNo"
    const val DELETE_ALL_CACHE = "delete from $TABLE_CACHE_TIME"

    const val SELECT_NEWS_DETAIL = "select * from $TABLE_ARTICLE where $COL_ARTICLE_ID=:id"
    const val UPDATE_WITH_DETAIL = "update $TABLE_ARTICLE set $COL_BODY=:body, $COL_BYLINE=:byline, $COL_LAST_CACHE_TIME=:lastCacheTime where $COL_ARTICLE_ID=:id"
}