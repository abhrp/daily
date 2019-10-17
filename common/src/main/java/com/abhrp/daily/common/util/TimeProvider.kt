package com.abhrp.daily.common.util

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimeProvider @Inject constructor() {

    /**
     * Gets the current system time in millis
     */
    val currentTime: Long
        get() = System.currentTimeMillis()

}