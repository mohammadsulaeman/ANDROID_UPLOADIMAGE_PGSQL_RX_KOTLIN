package com.example.uploadimages.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TambahImagesRequestJson(
    @SerializedName("image_id")
    @Expose
    val imageId : String = "",

    @SerializedName("cover")
    @Expose
    val cover : String,

    @SerializedName("title")
    @Expose
    val title : String
)