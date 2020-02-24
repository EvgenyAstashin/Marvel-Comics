package com.astashin.marvelcomics.ui.comics

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.astashin.marvelcomics.Date
import com.astashin.marvelcomics.MutableListLiveData
import com.astashin.marvelcomics.model.Comic
import com.astashin.marvelcomics.model.Data
import com.astashin.marvelcomics.network.Api
import com.astashin.marvelcomics.network.Response
import retrofit2.Call
import retrofit2.Callback
import javax.inject.Inject

class ComicsViewModel @Inject constructor(private val api: Api) : ViewModel() {

    companion object {
        const val PAGE_SIZE = 20
    }

    var startDate: Date? = null
    var endDate: Date? = null
    private var offset = 0
    private var total = -1

    val comicsList = MutableListLiveData<Comic>()
    var loading = MutableLiveData<Boolean>(false)
    var title = MutableLiveData<String>()

    fun loadComics() {
        if (loading.value!! || comicsList.value!!.size == total) return

        loading.value = true

        api.loadComics(
            "${startDate?.toFormattedString()},${endDate?.toFormattedString()}",
            offset,
            PAGE_SIZE
        )
            .enqueue(object : Callback<Response<Data<Comic>>> {
                override fun onFailure(call: Call<Response<Data<Comic>>>, t: Throwable) {
                    loading.value = false
                }

                override fun onResponse(
                    call: Call<Response<Data<Comic>>>,
                    response: retrofit2.Response<Response<Data<Comic>>>
                ) {
                    val r = response.body()
                    if (r != null && r.code == 200) {
                        total
                        comicsList.addAll(r.body.results)
                        offset += r.body.results.size
                    } else {

                    }
                    loading.value = false
                }
            })
    }

    fun setStartAndEndDates(startDate: Date?, endDate: Date?) {
        val needLoad = this.startDate == null || this.endDate == null
        this.startDate = startDate
        this.endDate = endDate
        title.value = "$startDate - $endDate"
        if (needLoad)
            loadComics()
    }
}