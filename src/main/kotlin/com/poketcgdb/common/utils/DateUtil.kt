package com.poketcgdb.common.utils

import java.util.*

class DateUtil {
    companion object {
        fun getSecondsRemainingUntilDate(futureDate: Date): Long = (futureDate.time - Date().time) / 1000
    }
}