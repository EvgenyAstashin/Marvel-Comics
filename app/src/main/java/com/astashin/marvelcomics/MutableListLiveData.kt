package com.astashin.marvelcomics

import androidx.lifecycle.LiveData

class MutableListLiveData<T>(private val mutableList: MutableList<T> = mutableListOf()) :
    LiveData<MutableList<T>>(mutableList) {

    fun addAll(list: List<T>) {
        mutableList.addAll(list)
        value = mutableList
    }
}