package com.abhrp.daily.application

import com.abhrp.daily.domain.executor.PostExecutionThread
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UIThread @Inject constructor(): PostExecutionThread {
    /**
     * Return the Scheduler needed for RxJava execution
     *
     * @return Scheduler for execution
     */
    override val scheduler: Scheduler?
        get() = AndroidSchedulers.mainThread()

}