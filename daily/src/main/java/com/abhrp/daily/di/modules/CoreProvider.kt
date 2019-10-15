package com.abhrp.daily.di.modules

import com.abhrp.daily.common.util.AppLogger
import com.abhrp.daily.core.util.AppLoggerImpl
import dagger.Binds
import dagger.Module

@Module
abstract class CoreProvider {
    @Binds
    abstract fun bindsAppLogger(appLoggerImpl: AppLoggerImpl): AppLogger
}