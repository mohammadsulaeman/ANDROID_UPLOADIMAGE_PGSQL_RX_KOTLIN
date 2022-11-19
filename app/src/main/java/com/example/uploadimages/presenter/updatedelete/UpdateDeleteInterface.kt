package com.example.uploadimages.presenter.updatedelete

import com.example.uploadimages.network.model.DeleteRequestJson

interface UpdateDeleteInterface
{
    fun DeleteImages(image_id : String)

    fun UpdateImages(image_id: String,cover : String, title : String)

}