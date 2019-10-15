package com.abhrp.daily.cache.factory

import java.util.*

object DataFactory {
    val randomString: String
        get() = UUID.randomUUID().toString()
    val randomInt: Int
        get() = Math.random().toInt()
    val randomLong: Long
        get() = Math.random().toLong()
}