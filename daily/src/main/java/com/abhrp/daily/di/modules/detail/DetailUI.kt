package com.abhrp.daily.di.modules.detail

import com.abhrp.daily.di.annotation.ActivityScope
import com.abhrp.daily.ui.detail.NewsDetailActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [DetailModule::class])
abstract class DetailUI {
    @get:ContributesAndroidInjector
    @get:ActivityScope
    @get:DetailScope
    abstract val newsDetailModule: NewsDetailActivity
}