package com.abhrp.daily.di.modules

import com.abhrp.daily.BuildConfig
import com.abhrp.daily.common.util.APIConfigProvider
import com.abhrp.daily.common.util.BuildTypeProvider
import com.abhrp.daily.common.util.DateProvider
import com.abhrp.daily.common.util.TimeProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CommonProvider {
    @Singleton
    @Provides
    fun apiConfigProvider(): APIConfigProvider {
        return APIConfigProvider(BuildConfig.API_KEY)
    }

    @Singleton
    @Provides
    fun buildTypeProvider(): BuildTypeProvider {
        return BuildTypeProvider(BuildConfig.DEBUG)
    }

    @Singleton
    @Provides
    fun timeProvider(): TimeProvider {
        return TimeProvider()
    }

    @Singleton
    @Provides
    fun dateProvider(): DateProvider {
        return DateProvider()
    }
}