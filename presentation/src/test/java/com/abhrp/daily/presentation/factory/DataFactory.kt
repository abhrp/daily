package com.abhrp.daily.presentation.factory

import java.util.*

object DataFactory {
    val randomString: String
        get() = UUID.randomUUID().toString()
}