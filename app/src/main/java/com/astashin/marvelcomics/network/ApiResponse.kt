package com.astashin.marvelcomics.network

import com.google.gson.annotations.SerializedName

class ApiResponse<T> (
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val body: T,
    @SerializedName("status")
    val status: String
)