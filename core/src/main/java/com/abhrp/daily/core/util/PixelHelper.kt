package com.abhrp.daily.core.util

import android.content.Context
import javax.inject.Inject

class PixelHelper @Inject constructor(private val context: Context) {

    fun pixelFromDp(dp: Int): Int {
        return (context.resources.displayMetrics.density * dp).toInt()
    }

}