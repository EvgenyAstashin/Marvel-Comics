package com.astashin.marvelcomics

import java.io.Serializable
import java.util.*

class Date(val year: Int, val month: Int, val day: Int) : Serializable {

    override fun toString(): String {
        return "$day.${month + 1}.$year"
    }

    fun toFormattedString(): String {
        return "$year-${month + 1}-$day"
    }

    fun toLong(): Long {
        val calendar = Calendar.getInstance()
        calendar.apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, day)
        }
        return calendar.timeInMillis
    }
}