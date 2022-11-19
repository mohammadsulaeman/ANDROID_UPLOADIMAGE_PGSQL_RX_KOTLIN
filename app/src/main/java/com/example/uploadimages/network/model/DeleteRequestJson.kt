package com.example.uploadimages.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DeleteRequestJson(
    @SerializedName("image_id")
    @Expose
    val imagesid : String = ""
)