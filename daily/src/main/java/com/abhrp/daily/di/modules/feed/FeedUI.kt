package com.abhrp.daily.di.modules.feed

import com.abhrp.daily.di.annotation.ActivityScope
import com.abhrp.daily.ui.feed.FeedActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [FeedModule::class])
abstract class FeedUI {
    @get:ContributesAndroidInjector
    @get:ActivityScope
    @get:FeedScope
    abstract val feedActivity: FeedActivity
}