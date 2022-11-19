package com.example.uploadimages.presenter.read

import com.example.uploadimages.network.model.GetDataImagesResponseJson

interface GetDataImageViewInterface
{
    fun showLoading()
    fun hideLoading()
    fun onSuccess(message : String)
    fun onError(message: String)
    fun onGetDataImagesAll(responseJson: GetDataImagesResponseJson)
}