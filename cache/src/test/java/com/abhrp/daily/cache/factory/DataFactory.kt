package com.abhrp.daily.cache.factory

import java.util.*

object DataFactory {
    val randomString = UUID.randomUUID().toString()
    val randomInt: Int = Math.random().toInt()
    val randomLong: Long = Math.random().toLong()
}