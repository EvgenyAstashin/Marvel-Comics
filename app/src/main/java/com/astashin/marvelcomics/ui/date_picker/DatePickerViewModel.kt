package com.astashin.marvelcomics.ui.date_picker

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.astashin.marvelcomics.Date
import javax.inject.Inject

class DatePickerViewModel @Inject constructor() : ViewModel() {

    val startDate = MutableLiveData<Date>(Date(1960, 11, 19))
    val endDate = MutableLiveData<Date>(Date(2019, 11, 19))

    private var view: DatePickerView? = null

    fun attachView(view: DatePickerView) {
        this.view = view
    }

    fun detachView() {
        this.view = null
    }

    fun startDateClick() {
        view?.selectDate(startDate.value!!) {
            startDate.value = it
        }
    }

    fun endDateClick() {
        view?.selectDate(endDate.value!!) {
            endDate.value = it
        }
    }

    fun loadComicsClick() {
    }
}