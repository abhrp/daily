package com.abhrp.daily.cache.sharedpreferences

import androidx.test.core.app.ApplicationProvider
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DailySharedPreferencesTest {
    private lateinit var dailySharedPreferences: DailySharedPreferences

    @Before
    fun setup() {
        dailySharedPreferences = DailySharedPreferences(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun testCurrentPageNumberIsZero() {
        Assert.assertEquals(dailySharedPreferences.currentPageNumber, 0)
    }

    @Test
    fun testCurrentPageNumberIsSetCorrectly() {
        dailySharedPreferences.currentPageNumber = 1
        Assert.assertEquals(dailySharedPreferences.currentPageNumber, 1)
        dailySharedPreferences.currentPageNumber = 2
        Assert.assertEquals(dailySharedPreferences.currentPageNumber, 2)
        dailySharedPreferences.currentPageNumber = 0
        Assert.assertEquals(dailySharedPreferences.currentPageNumber, 0)
    }
}