package com.example.uploadimages.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UpdateRequestJson(
    @SerializedName("image_id")
    @Expose
    val imagesId : String = "",

    @SerializedName("cover")
    @Expose
    val cover : String = "",

    @SerializedName("title")
    @Expose
    val title : String = ""
)