package com.abhrp.daily.presentation.viewmodel.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abhrp.daily.common.util.AppLogger
import com.abhrp.daily.domain.model.detail.NewsDetail
import com.abhrp.daily.domain.usecase.detail.GetNewsDetail
import com.abhrp.daily.presentation.mapper.detail.DetailViewMapper
import com.abhrp.daily.presentation.model.detail.DetailViewItem
import com.abhrp.daily.presentation.state.Resource
import com.abhrp.daily.presentation.state.ResourceState
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class NewsDetailViewModel @Inject constructor(private val getNewsDetail: GetNewsDetail, private val mapper: DetailViewMapper): ViewModel() {

    @Inject
    lateinit var logger: AppLogger

    override fun onCleared() {
        super.onCleared()
        getNewsDetail.disposeAll()
    }

    private val detailLiveData = MutableLiveData<Resource<DetailViewItem>>()

    fun observeDetails(): LiveData<Resource<DetailViewItem>> = detailLiveData

    fun fetchNewsDetails(id: String) {
        detailLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        getNewsDetail.execute(GetNewsDetailsObserver(), GetNewsDetail.Params.getParams(id))
    }

    inner class GetNewsDetailsObserver: DisposableSingleObserver<NewsDetail?>() {
        override fun onSuccess(t: NewsDetail) {
            val detailViewItem = mapper.mapToView(t)
            detailLiveData.postValue(Resource(ResourceState.SUCCESS, detailViewItem, null))
        }

        override fun onError(e: Throwable) {
            detailLiveData.postValue(Resource(ResourceState.ERROR, null, e.localizedMessage))
        }
    }

}