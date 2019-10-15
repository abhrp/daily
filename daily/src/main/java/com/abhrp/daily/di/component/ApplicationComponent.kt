package com.abhrp.daily.di.component

import android.app.Application
import com.abhrp.daily.application.DailyApplication
import com.abhrp.daily.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ApplicationModule::class,
        CommonProvider::class,
        PresentationModule::class,
        UIModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<DailyApplication> {

    override fun inject(instance: DailyApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}