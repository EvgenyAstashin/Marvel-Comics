package com.astashin.marvelcomics.ui.date_picker

import androidx.lifecycle.MutableLiveData
import com.astashin.marvelcomics.Date
import com.astashin.marvelcomics.ui.base.BaseViewModel
import java.util.*
import javax.inject.Inject

class DatePickerViewModel @Inject constructor() : BaseViewModel<DatePickerView>() {

    private val minDate = Date(1939, 9, 1)
    private val maxDate: Date
        get() {
            val calendar = Calendar.getInstance()
            return Date(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
        }

    val startDate = MutableLiveData<Date>(minDate)
    val endDate = MutableLiveData<Date>(maxDate)

    fun startDateClick() {
        view?.selectDate(startDate.value!!, minDate, endDate.value!!) {
            startDate.value = it
        }
    }

    fun endDateClick() {
        view?.selectDate(endDate.value!!, startDate.value!!, maxDate) {
            endDate.value = it
        }
    }

    fun loadComicsClick() {
        view?.openComicsList(startDate.value!!, endDate.value!!)
    }
}