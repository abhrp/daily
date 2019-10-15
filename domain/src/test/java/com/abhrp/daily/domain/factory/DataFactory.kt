package com.abhrp.daily.domain.factory

import java.util.*

object DataFactory {
    val randomString = UUID.randomUUID().toString()
    val randomInt: Int = Math.random().toInt()
}