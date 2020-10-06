package com.astashin.marvelcomics.characters.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.astashin.marvelcomics.core.viewmodel.BaseViewModel
import com.astashin.marvelcomics.core.viewmodel.IViewModel
import com.astashin.marvelcomics.utils.livedata.MutableListLiveData
import com.astashin.marvelcomics.model.Character
import com.astashin.marvelcomics.model.Comic
import com.astashin.marvelcomics.model.Data
import com.astashin.marvelcomics.network.Api
import com.astashin.marvelcomics.network.ApiResponse
import com.astashin.marvelcomics.utils.livedata.SingleLiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface ICharactersViewModel : IViewModel {

    val comic: LiveData<Comic>

    val loading: LiveData<Boolean>

    val charactersList: MutableListLiveData<Character>

    val errorEvent: LiveData<String?>

    fun setComic(comic: Comic?)
}

class CharactersViewModel(
    private var api: Api
) : BaseViewModel(), ICharactersViewModel {

    companion object {
        private const val PAGE_SIZE = 100
    }

    override val comic = MutableLiveData<Comic>()

    override val loading = MutableLiveData(false)

    override val charactersList = MutableListLiveData<Character>()

    override val errorEvent = SingleLiveEvent<String?>()

    override fun setComic(comic: Comic?) {
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
        errorEvent.call()
    }

    private fun handleResponse(response: Response<ApiResponse<Data<Character>>>) {
        val apiResponse = response.body()
        if (apiResponse != null && apiResponse.code == 200) {
            charactersList.addAll(apiResponse.body.results)
        } else {
            errorEvent.postValue(apiResponse?.status)
        }
    }
}