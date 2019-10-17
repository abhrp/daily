package com.abhrp.daily.presentation.viewmodel.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abhrp.daily.domain.model.feed.FeedItem
import com.abhrp.daily.domain.usecase.feed.GetFeed
import com.abhrp.daily.presentation.mapper.feed.FeedViewItemMapper
import com.abhrp.daily.presentation.model.feed.FeedViewItem
import com.abhrp.daily.presentation.state.Resource
import com.abhrp.daily.presentation.state.ResourceState
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

/**
 * FeedViewModel - ViewModel class for the feed screen of the app.
 * @param getFeed - GetFeed usecase
 * @param viewItemMapper - Mapper to convert domain classes to view classes
 */
class FeedViewModel @Inject constructor(private val getFeed: GetFeed, private val viewItemMapper: FeedViewItemMapper): ViewModel() {

    private val feedLiveData = MutableLiveData<Resource<List<FeedViewItem>>>()

    override fun onCleared() {
        super.onCleared()
        getFeed.disposeAll()
    }

    fun observeFeed(): LiveData<Resource<List<FeedViewItem>>> = feedLiveData

    fun getFeed(isFirstPage: Boolean, isNewRequest: Boolean) {
        feedLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        getFeed.execute(GetFeedSingleObserver(), GetFeed.Params.getParams(isFirstPage, isNewRequest))
    }

    inner class GetFeedSingleObserver: DisposableSingleObserver<List<FeedItem>>() {
        override fun onSuccess(list: List<FeedItem>) {
            val viewItems = list.map { viewItemMapper.mapToView(it) }
            feedLiveData.postValue(Resource(ResourceState.SUCCESS, viewItems, null))
        }

        override fun onError(e: Throwable) {
            feedLiveData.postValue(Resource(ResourceState.ERROR, null, e.message))
        }

    }
}