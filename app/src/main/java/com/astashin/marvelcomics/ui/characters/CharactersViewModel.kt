package com.astashin.marvelcomics.ui.characters

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.astashin.marvelcomics.MutableListLiveData
import com.astashin.marvelcomics.model.Character
import com.astashin.marvelcomics.model.Comic
import com.astashin.marvelcomics.model.Data
import com.astashin.marvelcomics.network.Api
import com.astashin.marvelcomics.network.Response
import retrofit2.Call
import retrofit2.Callback
import javax.inject.Inject

class CharactersViewModel @Inject constructor(private val api: Api) : ViewModel() {

    companion object {
        private const val PAGE_SIZE = 100
    }

    val comic = MutableLiveData<Comic>()
    val loading = MutableLiveData<Boolean>(false)
    val charactersList = MutableListLiveData<Character>()

    var view: CharacterListView? = null

    fun attachView(view: CharacterListView) {
        this.view = view
    }

    fun detachView() {
        this.view = null
    }

    fun setComic(comic: Comic?) {
        val needLoad = this.comic.value == null
        this.comic.value = comic
        if(needLoad)
            loadCharacters()
    }

    private fun loadCharacters() {
        loading.value = true
        api.loadCharacters(comic.value!!.id, PAGE_SIZE).enqueue(object : Callback<Response<Data<Character>>> {
            override fun onFailure(call: Call<Response<Data<Character>>>, t: Throwable) {
                loading.value = false
                view?.showError()
            }

            override fun onResponse(
                call: Call<Response<Data<Character>>>,
                response: retrofit2.Response<Response<Data<Character>>>
            ) {
                val r = response.body()
                if (r != null && r.code == 200) {
                    charactersList.addAll(r.body.results)
                } else {
                    view?.showError(r?.status)
                }
                loading.value = false
            }

        })
    }
}