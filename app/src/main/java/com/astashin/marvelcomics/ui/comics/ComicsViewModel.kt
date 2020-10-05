package com.astashin.marvelcomics.ui.comics

import androidx.lifecycle.MutableLiveData
import com.astashin.marvelcomics.Date
import com.astashin.marvelcomics.utils.livedata.MutableListLiveData
import com.astashin.marvelcomics.model.Comic
import com.astashin.marvelcomics.model.Data
import com.astashin.marvelcomics.network.Api
import com.astashin.marvelcomics.network.ApiResponse
import com.astashin.marvelcomics.ui.base.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ComicsViewModel : BaseViewModel<ComicsListView>() {

    companion object {
        const val PAGE_SIZE = 20
    }

    lateinit var api: Api
    private var startDate: Date? = null
    private var endDate: Date? = null
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
        ).enqueue(object : Callback<ApiResponse<Data<Comic>>> {
                override fun onFailure(call: Call<ApiResponse<Data<Comic>>>, t: Throwable) {
                    loading.value = false
                    handleFailure()
                }

                override fun onResponse(
                    call: Call<ApiResponse<Data<Comic>>>,
                    response: Response<ApiResponse<Data<Comic>>>
                ) {
                    handleResponse(response)
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

    private fun handleFailure() {
        view?.showError()
    }

    private fun handleResponse(response: Response<ApiResponse<Data<Comic>>>) {
        val apiResponse = response.body()
        if (apiResponse != null && apiResponse.code == 200) {
            total = apiResponse.body.total
            comicsList.addAll(apiResponse.body.results)
            offset += apiResponse.body.results.size
        } else {
            view?.showError()
        }
    }
}