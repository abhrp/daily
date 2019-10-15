package com.abhrp.daily.di.modules

import android.app.Application
import com.abhrp.daily.cache.sql.DailyDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class CacheModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        @Singleton
        fun provideDatabase(application: Application): DailyDatabase {
            return DailyDatabase.getInstance(application)
        }
    }
}