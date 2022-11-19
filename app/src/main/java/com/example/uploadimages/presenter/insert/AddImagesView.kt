package com.example.uploadimages.presenter.insert

interface AddImagesView
{
    fun onSuccess(message : String)
    fun onError(message: String)
    fun showLoading()
    fun onHideLoading()

}