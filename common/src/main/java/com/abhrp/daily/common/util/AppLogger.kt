package com.abhrp.daily.common.util

import java.lang.Exception

interface AppLogger {
    fun logDebug(message: String?)
    fun logError(message: String?)
    fun logException(exception: Exception?)
    fun logThrowable(throwable: Throwable?)
}