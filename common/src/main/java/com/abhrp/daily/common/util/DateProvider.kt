package com.abhrp.daily.common.util

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DateProvider @Inject constructor() {

    fun getFormattedDate(date: String): String {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return formatter.format(parser.parse(date))
    }

}