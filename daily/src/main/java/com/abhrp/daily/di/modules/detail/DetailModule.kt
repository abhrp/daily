package com.abhrp.daily.di.modules.detail

import androidx.lifecycle.ViewModel
import com.abhrp.daily.cache.impl.detail.NewsDetailCacheImpl
import com.abhrp.daily.data.impl.detail.NewsDetailRepositoryImpl
import com.abhrp.daily.data.repository.detail.NewsDetailCache
import com.abhrp.daily.data.repository.detail.NewsDetailRemote
import com.abhrp.daily.di.annotation.ViewModelKey
import com.abhrp.daily.domain.repository.detail.NewsDetailRepository
import com.abhrp.daily.presentation.viewmodel.detail.NewsDetailViewModel
import com.abhrp.daily.remote.impl.detail.NewsDetailRemoteImpl
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class DetailModule {
    @Binds
    @IntoMap
    @ViewModelKey(NewsDetailViewModel::class)
    abstract fun bindsNewsDetailViewModel(newsDetailViewModel: NewsDetailViewModel): ViewModel

    @Binds
    abstract fun bindsNewsDetailRepository(newsDetailRepositoryImpl: NewsDetailRepositoryImpl): NewsDetailRepository

    @Binds
    abstract fun bindNewsDetailCache(newsDetailCacheImpl: NewsDetailCacheImpl): NewsDetailCache

    @Binds
    abstract fun bindsNewsDetailRemote(newsDetailRemoteImpl: NewsDetailRemoteImpl): NewsDetailRemote
}