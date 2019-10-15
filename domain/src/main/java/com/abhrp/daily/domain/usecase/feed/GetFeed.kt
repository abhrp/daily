package com.abhrp.daily.domain.usecase.feed

import com.abhrp.daily.domain.executor.PostExecutionThread
import com.abhrp.daily.domain.model.feed.FeedItem
import com.abhrp.daily.domain.repository.feed.NewsFeedRepository
import com.abhrp.daily.domain.usecase.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

/**
 * GetFeed: Use case class that will be executed when the user requests for a new feed of news items
 * @param postExecutionThread: Executor for RxJava
 * @param newsFeedRepository: Repository for getting feed items
 */
class GetFeed @Inject constructor(postExecutionThread: PostExecutionThread, private val newsFeedRepository: NewsFeedRepository): SingleUseCase<List<FeedItem>, GetFeed.Params>(postExecutionThread) {

    override fun buildUseCaseObservable(params: Params?): Single<List<FeedItem>> {
        requireNotNull(params) { "Params for GetFeed cannot be null" }
        return newsFeedRepository.getFeedItems(params.isFirstPage, params.isNewRequest)
    }

    data class Params(val isFirstPage: Boolean, val isNewRequest: Boolean) {
        companion object {
            fun getParams(isFirstPage: Boolean, isNewRequest: Boolean) = Params(isFirstPage, isNewRequest)
        }
    }
}