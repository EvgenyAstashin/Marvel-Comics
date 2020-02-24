package com.astashin.marvelcomics.model

import com.google.gson.annotations.SerializedName

data class Comic(
   @SerializedName("id")
   val id: Int,
   @SerializedName("title")
   val title: String,
   @SerializedName("description")
   val description: String,
   @SerializedName("thumbnail")
   val thumbnail: Thumbnail
)