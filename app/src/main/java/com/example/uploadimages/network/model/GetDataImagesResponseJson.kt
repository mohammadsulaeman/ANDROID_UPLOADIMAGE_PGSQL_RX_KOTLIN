package com.example.uploadimages.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GetDataImagesResponseJson(
    @SerializedName("images")
    @Expose
    val images : List<Images>,

    @SerializedName("status")
    @Expose
    val status : String,

    @SerializedName("message")
    @Expose
    val message : String

)