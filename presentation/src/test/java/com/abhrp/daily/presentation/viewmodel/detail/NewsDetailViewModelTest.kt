package com.abhrp.daily.presentation.viewmodel.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.abhrp.daily.domain.model.detail.NewsDetail
import com.abhrp.daily.domain.usecase.detail.GetNewsDetail
import com.abhrp.daily.presentation.factory.DataFactory
import com.abhrp.daily.presentation.factory.FeedItemFactory
import com.abhrp.daily.presentation.mapper.detail.DetailViewMapper
import com.abhrp.daily.presentation.model.detail.DetailViewItem
import com.abhrp.daily.presentation.state.ResourceState
import com.nhaarman.mockito_kotlin.*
import io.reactivex.observers.DisposableSingleObserver
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor
import org.mockito.Mockito

@RunWith(JUnit4::class)
class NewsDetailViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Captor
    private val detailArgumentCaptor = argumentCaptor<DisposableSingleObserver<NewsDetail?>>()


    private val getDetails = mock<GetNewsDetail>()
    private val mapper = mock<DetailViewMapper>()
    private val detailViewModel = NewsDetailViewModel(getDetails, mapper)

    @Test
    fun testGetDetailsCompletes() {
        val id = DataFactory.randomString
        val params = GetNewsDetail.Params.getParams(id)

        detailViewModel.fetchNewsDetails(id)
        verify(getDetails, Mockito.times(1)).execute(any(), eq(params))

    }

    @Test
    fun testGetDetailsReturnsDataOnSuccess() {
        val id = DataFactory.randomString
        val params = GetNewsDetail.Params.getParams(id)
        val newsDetail = FeedItemFactory.getNewsDetail()
        val detailViewItem = FeedItemFactory.getDetailViewItem()

        stubMapper(newsDetail, detailViewItem)
        detailViewModel.fetchNewsDetails(id)
        verify(getDetails, Mockito.times(1)).execute(detailArgumentCaptor.capture(), eq(params))
        detailArgumentCaptor.firstValue.onSuccess(newsDetail)
        Assert.assertEquals(detailViewModel.observeDetails().value?.status, ResourceState.SUCCESS)
        Assert.assertEquals(detailViewModel.observeDetails().value?.data, detailViewItem)
        Assert.assertNull(detailViewModel.observeDetails().value?.error)
    }

    @Test
    fun testGetDetailsReturnsErrorOnFailure() {
        val id = DataFactory.randomString
        val params = GetNewsDetail.Params.getParams(id)
        val error = DataFactory.randomString
        detailViewModel.fetchNewsDetails(id)
        verify(getDetails, Mockito.times(1)).execute(detailArgumentCaptor.capture(), eq(params))
        detailArgumentCaptor.firstValue.onError(Throwable(error))

        Assert.assertEquals(detailViewModel.observeDetails().value?.status, ResourceState.ERROR)
        Assert.assertEquals(detailViewModel.observeDetails().value?.error, error)
        Assert.assertNull(detailViewModel.observeDetails().value?.data)
    }


    private fun stubMapper(newsDetail: NewsDetail, detailViewItem: DetailViewItem) {
        whenever(mapper.mapToView(newsDetail)).thenReturn(detailViewItem)
    }
}