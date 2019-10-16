package com.abhrp.daily.domain.usecase.detail

import com.abhrp.daily.domain.executor.PostExecutionThread
import com.abhrp.daily.domain.model.detail.NewsDetail
import com.abhrp.daily.domain.repository.detail.NewsDetailRepository
import com.abhrp.daily.domain.usecase.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetNewsDetail @Inject constructor(postExecutionThread: PostExecutionThread, private val newsDetailRepository: NewsDetailRepository): SingleUseCase<NewsDetail?, GetNewsDetail.Params>(postExecutionThread) {

    override fun buildUseCaseObservable(params: Params?): Single<NewsDetail?> {
        requireNotNull(params)
        return newsDetailRepository.getNewsDetails(params.id)
    }

    data class Params(val id: String) {
        companion object {
            fun getParams(id: String) = Params(id)
        }
    }
}