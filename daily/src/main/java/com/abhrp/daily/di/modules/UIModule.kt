package com.abhrp.daily.di.modules


import com.abhrp.daily.application.UIThread
import com.abhrp.daily.di.modules.detail.DetailUI
import com.abhrp.daily.di.modules.feed.FeedUI
import com.abhrp.daily.domain.executor.PostExecutionThread
import dagger.Binds
import dagger.Module

@Module(includes = [FeedUI::class, DetailUI::class])
abstract class UIModule {
    @Binds
    abstract fun bindsPostExecutionThread(uiThread: UIThread): PostExecutionThread
}