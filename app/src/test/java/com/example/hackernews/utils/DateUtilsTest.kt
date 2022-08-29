package com.example.hackernews.utils

import android.content.Context
import io.mockk.mockk
import org.joda.time.DateTime
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DateUtilTest {

    private val appContext : Context = mockk()

    @Test
    fun `given 1661755074031 as seconds then Aug 29 @ 9 double dots 37 am seconds is returned`() {
        val pastDate = 1661755074031
        val userFriendlyDate = DateUtils.formatDateUserFriendly(DateTime(pastDate), appContext)

        assertEquals("Aug 29 @ 9:37 am", userFriendlyDate)
    }

    @Test
    fun `given 1621715074031 as seconds then 22 May 2021 @ 11 double dots 24 pm seconds is returned`() {
        val pastDate = 1621715074031
        val userFriendlyDate = DateUtils.formatDateUserFriendly(DateTime(pastDate), appContext)

        assertEquals("22 May 2021 @ 11:24 pm", userFriendlyDate)
    }
}