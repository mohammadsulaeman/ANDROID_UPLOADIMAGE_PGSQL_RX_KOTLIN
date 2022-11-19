package com.example.uploadimages.presenter.updatedelete

interface UpdateDeleteView
{
    fun onSuccess(message : String)
    fun onError(message: String)
    fun showLoading()
    fun onHideLoading()
}