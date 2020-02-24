package com.astashin.marvelcomics.ui.date_picker

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.astashin.marvelcomics.Date
import java.util.*
import javax.inject.Inject

class DatePickerViewModel @Inject constructor() : ViewModel() {

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

    private var view: DatePickerView? = null

    fun attachView(view: DatePickerView) {
        this.view = view
    }

    fun detachView() {
        this.view = null
    }

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