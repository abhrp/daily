package com.abhrp.daily.di.modules

import androidx.lifecycle.ViewModelProvider
import com.abhrp.daily.di.ViewModelFactory

import dagger.Binds
import dagger.Module

@Module
abstract class PresentationModule {
    @Binds
    abstract fun bindsViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}
