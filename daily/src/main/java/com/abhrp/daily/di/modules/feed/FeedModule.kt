package com.abhrp.daily.di.modules.feed

import androidx.lifecycle.ViewModel
import com.abhrp.daily.cache.impl.feed.FeedCacheImpl
import com.abhrp.daily.data.impl.feed.NewsFeedRepositoryImpl
import com.abhrp.daily.data.repository.feed.FeedCache
import com.abhrp.daily.data.repository.feed.FeedRemote
import com.abhrp.daily.di.annotation.ViewModelKey
import com.abhrp.daily.domain.repository.feed.NewsFeedRepository
import com.abhrp.daily.presentation.viewmodel.feed.FeedViewModel
import com.abhrp.daily.remote.impl.feed.FeedRemoteImpl
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class FeedModule {
    @Binds
    @IntoMap
    @ViewModelKey(FeedViewModel::class)
    abstract fun bindsFeedViewModel(feedViewModel: FeedViewModel): ViewModel

    @Binds
    abstract fun bindsNewsFeedRepository(newsFeedRepositoryImpl: NewsFeedRepositoryImpl): NewsFeedRepository

    @Binds
    abstract fun bindsFeedCache(feedCacheImpl: FeedCacheImpl): FeedCache

    @Binds
    abstract fun bindsFeedRemote(feedRemoteImpl: FeedRemoteImpl): FeedRemote
}