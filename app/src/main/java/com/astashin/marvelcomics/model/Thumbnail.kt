package com.astashin.marvelcomics.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Thumbnail(
    @SerializedName("extension")
    val extension: String,
    @SerializedName("path")
    val path: String
) : Serializable