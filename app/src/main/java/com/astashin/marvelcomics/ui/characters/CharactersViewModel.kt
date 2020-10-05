package com.astashin.marvelcomics.ui.characters

import androidx.lifecycle.MutableLiveData
import com.astashin.marvelcomics.utils.livedata.MutableListLiveData
import com.astashin.marvelcomics.model.Character
import com.astashin.marvelcomics.model.Comic
import com.astashin.marvelcomics.model.Data
import com.astashin.marvelcomics.network.Api
import com.astashin.marvelcomics.network.ApiResponse
import com.astashin.marvelcomics.ui.base.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharactersViewModel : BaseViewModel<CharacterListView>() {

    companion object {
        private const val PAGE_SIZE = 100
    }

    lateinit var api: Api
    val comic = MutableLiveData<Comic>()
    val loading = MutableLiveData(false)
    val charactersList = MutableListLiveData<Character>()

    fun setComic(comic: Comic?) {
        val needLoad = this.comic.value == null
        this.comic.value = comic
        if (needLoad)
            loadCharacters()
    }

    private fun loadCharacters() {
        loading.value = true
        api.loadCharacters(comic.value!!.id, PAGE_SIZE)
            .enqueue(object : Callback<ApiResponse<Data<Character>>> {
                override fun onFailure(call: Call<ApiResponse<Data<Character>>>, t: Throwable) {
                    loading.value = false
                    handleFailure()
                }

                override fun onResponse(
                    call: Call<ApiResponse<Data<Character>>>,
                    response: Response<ApiResponse<Data<Character>>>
                ) {
                    handleResponse(response)
                    loading.value = false
                }
            })
    }

    private fun handleFailure() {
        view?.showError()
    }

    private fun handleResponse(response: Response<ApiResponse<Data<Character>>>) {
        val apiResponse = response.body()
        if (apiResponse != null && apiResponse.code == 200) {
            charactersList.addAll(apiResponse.body.results)
        } else {
            view?.showError(apiResponse?.status)
        }
    }
}