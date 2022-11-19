package com.example.uploadimages.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Images(
    @SerializedName("cover")
    @Expose
    val image : String,

    @SerializedName("title")
    @Expose
    val title : String
){
    @SerializedName("image_id")
    @Expose
    val idimage : String = ""
}