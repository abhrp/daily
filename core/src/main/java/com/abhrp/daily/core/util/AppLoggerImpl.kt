package com.abhrp.daily.core.util

import com.abhrp.daily.common.util.AppLogger
import com.abhrp.daily.common.util.BuildTypeProvider
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import java.lang.Exception
import javax.inject.Inject

class AppLoggerImpl @Inject constructor(private val buildTypeProvider: BuildTypeProvider): AppLogger {

    init {
        val formatStrategy = PrettyFormatStrategy.newBuilder().tag("Daily").build()
        Logger.addLogAdapter(object: AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return buildTypeProvider.isDebug
            }
        })
    }

    override fun logDebug(message: String?) {
        if (buildTypeProvider.isDebug) {
            Logger.d(message)
        }
    }

    override fun logError(message: String?) {
        if (buildTypeProvider.isDebug && message != null) {
            Logger.e(message)
        }
    }

    override fun logException(exception: Exception?) {
        if (buildTypeProvider.isDebug) {
            Logger.e(exception, exception?.message ?: "")
        }
    }

    override fun logThrowable(throwable: Throwable?) {
        if (buildTypeProvider.isDebug) {
            Logger.e(throwable, throwable?.localizedMessage ?: "")
        }
    }
}