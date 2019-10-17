package com.abhrp.daily.cache.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

/**
 * A seperate shared preferences class to store single values.
 */
class DailySharedPreferences @Inject constructor(context: Context) {
    companion object {
        private const val PREF_NAME = "com.github.abhrp.daily.cache.preferences"

        private const val PREF_CURRENT_PAGE_NO = "current_page_no"
    }

    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    var currentPageNumber: Int?
        get() = sharedPreferences.getInt(PREF_CURRENT_PAGE_NO, 0)
        set(pageNo) {
            sharedPreferences.edit().putInt(PREF_CURRENT_PAGE_NO, pageNo ?: 0).apply()
        }
}