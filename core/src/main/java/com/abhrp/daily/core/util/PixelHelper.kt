package com.abhrp.daily.core.util

import android.content.Context
import javax.inject.Inject

/**
 * A helper class for getting pixel and dp related values
 */
class PixelHelper @Inject constructor(private val context: Context) {

    /**
     * Gets the pixels for a given dp value
     * @param dp Values in dp
     */
    fun pixelFromDp(dp: Int): Int {
        return (context.resources.displayMetrics.density * dp).toInt()
    }

}