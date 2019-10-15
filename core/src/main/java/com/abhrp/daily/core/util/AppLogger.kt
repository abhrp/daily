package com.abhrp.daily.core.util

import com.abhrp.daily.core.BuildConfig
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import java.lang.Exception
import javax.inject.Inject

class AppLogger @Inject constructor() {

    init {
        val formatStrategy = PrettyFormatStrategy.newBuilder().tag("Daily").build()
        Logger.addLogAdapter(object: AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }

    fun logDebug(message: String?) {
        if (BuildConfig.DEBUG) {
            Logger.d(message)
        }
    }

    fun logError(message: String?) {
        if (BuildConfig.DEBUG && message != null) {
            Logger.e(message)
        }
    }

    fun logException(exception: Exception?) {
        if (BuildConfig.DEBUG) {
            Logger.e(exception, exception?.message ?: "")
        }
    }

    fun logThrowable(throwable: Throwable?) {
        if (BuildConfig.DEBUG) {
            Logger.e(throwable, throwable?.localizedMessage ?: "")
        }
    }
}