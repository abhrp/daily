package com.abhrp.daily.domain.factory

import java.util.*

object DataFactory {
    val randomString: String
        get() = UUID.randomUUID().toString()
}