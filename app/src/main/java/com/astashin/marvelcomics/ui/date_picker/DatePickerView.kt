package com.astashin.marvelcomics.ui.date_picker

import com.astashin.marvelcomics.Date

interface DatePickerView {

    fun selectDate(date: Date, result: (Date) -> Unit)
}