package com.abhrp.daily.presentation.viewmodel.feed

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.abhrp.daily.domain.model.feed.FeedItem
import com.abhrp.daily.domain.usecase.feed.GetFeed
import com.abhrp.daily.presentation.factory.DataFactory
import com.abhrp.daily.presentation.factory.FeedItemFactory
import com.abhrp.daily.presentation.mapper.feed.FeedViewItemMapper
import com.abhrp.daily.presentation.model.feed.FeedViewItem
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
class FeedViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Captor
    private val feedArgumentCaptor = argumentCaptor<DisposableSingleObserver<List<FeedItem>>>()

    private val getFeed = mock<GetFeed>()
    private val itemMapper = mock<FeedViewItemMapper>()

    private val feedViewModel = FeedViewModel(getFeed, itemMapper)


    @Test
    fun testGetFeedCompletes() {
        val firstPage = true
        val isNewRequest = true
        feedViewModel.getFeed(firstPage, isNewRequest)
        val params = GetFeed.Params.getParams(firstPage, isNewRequest)
        verify(getFeed, Mockito.times(1)).execute(any(), eq(params))
    }

    @Test
    fun testGetFeedReturnsDataOnSuccess() {
        val firstPage = false
        val isNewRequest = false
        val feedItems = FeedItemFactory.getFeedItems(10)
        val feedViewItems = FeedItemFactory.getFeedViewItems(10)
        stubMapper(feedItems, feedViewItems)
        feedViewModel.getFeed(firstPage, isNewRequest)
        val params = GetFeed.Params.getParams(firstPage, isNewRequest)
        verify(getFeed, Mockito.times(1)).execute(feedArgumentCaptor.capture(), eq(params))
        feedArgumentCaptor.firstValue.onSuccess(feedItems)
        Assert.assertEquals(feedViewModel.observeFeed().value?.status, ResourceState.SUCCESS)
        Assert.assertEquals(feedViewModel.observeFeed().value?.data, feedViewItems)
        Assert.assertNull(feedViewModel.observeFeed().value?.error)
    }

    @Test
    fun testGetFeedReturnsErrorOnFailure() {
        val firstPage = false
        val isNewRequest = false
        val error = DataFactory.randomString
        feedViewModel.getFeed(firstPage, isNewRequest)
        val params = GetFeed.Params.getParams(firstPage, isNewRequest)
        verify(getFeed, Mockito.times(1)).execute(feedArgumentCaptor.capture(), eq(params))
        feedArgumentCaptor.firstValue.onError(Throwable(error))
        Assert.assertEquals(feedViewModel.observeFeed().value?.status, ResourceState.ERROR)
        Assert.assertNull(feedViewModel.observeFeed().value?.data)
        Assert.assertEquals(feedViewModel.observeFeed().value?.error, error)
    }

    private fun stubMapper(feedItems: List<FeedItem>, feedViewItems: List<FeedViewItem>) {
        for (i in 0 until feedItems.count()) {
            whenever(itemMapper.mapToView(feedItems[i])).thenReturn(feedViewItems[i])
        }
    }
}