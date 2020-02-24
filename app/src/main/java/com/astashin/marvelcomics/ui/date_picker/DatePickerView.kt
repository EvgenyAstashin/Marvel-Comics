package com.astashin.marvelcomics.ui.date_picker

import com.astashin.marvelcomics.Date

interface DatePickerView {

    fun selectDate(date: Date, minDate: Date, maxDate: Date, result: (Date) -> Unit)

    fun openComicsList(startDate: Date, endDate: Date)
}