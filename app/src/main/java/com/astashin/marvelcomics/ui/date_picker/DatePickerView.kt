package com.astashin.marvelcomics.ui.date_picker

import com.astashin.marvelcomics.Date
import com.astashin.marvelcomics.ui.base.BaseView

interface DatePickerView : BaseView {

    fun selectDate(date: Date, minDate: Date, maxDate: Date, result: (Date) -> Unit)

    fun openComicsList(startDate: Date, endDate: Date)
}