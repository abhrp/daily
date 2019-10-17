package com.abhrp.daily.common.util

import java.lang.Exception

/**
 * An interface for logging. Can be implemented by any layer and used on any layer
 */
interface AppLogger {

    /**
     * Log debug messages
     * @param message Debug message to log
     */
    fun logDebug(message: String?)

    /**
     * Log error message
     * @param message Error string to log
     */
    fun logError(message: String?)

    /**
     * Log exceptions
     * @param exception A java Exception class which will be used to log any errors
     */
    fun logException(exception: Exception?)

    /**
     * Log throwable errors
     * @param throwable A java Throwable class
     */
    fun logThrowable(throwable: Throwable?)
}