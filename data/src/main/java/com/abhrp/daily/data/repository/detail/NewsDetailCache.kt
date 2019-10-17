package com.abhrp.daily.data.repository.detail

import com.abhrp.daily.data.model.detail.NewsDetailData
import io.reactivex.Completable
import io.reactivex.Single

interface NewsDetailCache {

    /**
     * Gets news details from the cache for a given id
     * @param id Id of the article to fetch from the cache
     */
    fun getNewsDetails(id: String): Single<NewsDetailData?>
    
    /**
     * Saves the news details in cache
     * @param id Id to save
     * @param newsDetailData Object to save
     */
    fun saveNewsDetails(id: String, newsDetailData: NewsDetailData): Completable

    /**
     * Determines if the details are cached
     * @param id Id of the article
     */
    fun isNewsDetailsCached(id: String): Single<Boolean>

    /**
     * Determines if the cache for a given id is expired
     * @param id id to check for expiry
     */
    fun isNewsDetailsExpired(id: String): Single<Boolean>

    /**
     * Clears all details
     * @param id
     */
    fun clearNewsDetails(id: String): Completable
}