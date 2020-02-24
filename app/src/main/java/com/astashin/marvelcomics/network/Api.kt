package com.astashin.marvelcomics.network

import com.astashin.marvelcomics.model.Comic
import com.astashin.marvelcomics.model.Data
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("v1/public/comics")
    fun loadComics(@Query("dateRange") dateRange: String, @Query("offset") offset: Int, @Query("limit") limit: Int): Call<Response<Data<Comic>>>
}