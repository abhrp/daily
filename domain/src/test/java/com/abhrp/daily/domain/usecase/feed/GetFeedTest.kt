package com.abhrp.daily.domain.usecase.feed

import com.abhrp.daily.domain.executor.PostExecutionThread
import com.abhrp.daily.domain.factory.FeedItemFactory
import com.abhrp.daily.domain.model.feed.FeedItem
import com.abhrp.daily.domain.repository.feed.NewsFeedRepository
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.lang.IllegalArgumentException

@RunWith(JUnit4::class)
class GetFeedTest {
    
    private lateinit var getFeed: GetFeed

    @Mock
    private lateinit var postExecutionThread: PostExecutionThread
    @Mock
    private lateinit var newsFeedRepository: NewsFeedRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getFeed = GetFeed(postExecutionThread, newsFeedRepository)
    }

    @Test
    fun testGetFeedCompletes() {
        val feedItems = FeedItemFactory.getFeedItems(10)
        val isFirstPage = true
        val isNewRequest = true
        stubGetFeedItems(isFirstPage, isNewRequest, feedItems)
        val testObserver = getFeed.buildUseCaseObservable(GetFeed.Params.getParams(isFirstPage, isNewRequest)).test()
        testObserver.assertComplete()
    }

    @Test
    fun testGetFeedReturnsData() {
        val feedItems = FeedItemFactory.getFeedItems(10)
        val isFirstPage = true
        val isNewRequest = true
        stubGetFeedItems(isFirstPage, isNewRequest, feedItems)
        val testObserver = getFeed.buildUseCaseObservable(GetFeed.Params.getParams(isFirstPage, isNewRequest)).test()
        testObserver.assertValue(feedItems)
    }

    @Test
    fun testGetFeedCallsRepository() {
        val feedItems = FeedItemFactory.getFeedItems(10)
        val isFirstPage = true
        val isNewRequest = true
        stubGetFeedItems(isFirstPage, isNewRequest, feedItems)
        getFeed.buildUseCaseObservable(GetFeed.Params.getParams(isFirstPage, isNewRequest)).test()
        Mockito.verify(newsFeedRepository).getFeedItems(isFirstPage, isNewRequest)
    }

    @Test (expected = IllegalArgumentException::class)
    fun testGetFeedThrowsException() {
        val feedItems = FeedItemFactory.getFeedItems(10)
        val isFirstPage = true
        val isNewRequest = true
        stubGetFeedItems(isFirstPage, isNewRequest, feedItems)
        getFeed.buildUseCaseObservable(null).test()
    }

    private fun stubGetFeedItems(isFirstPage: Boolean, isNewRequest: Boolean, feedItems: List<FeedItem>) {
        Mockito.`when`(newsFeedRepository.getFeedItems(isFirstPage, isNewRequest)).thenReturn(Single.just(feedItems))
    }
}