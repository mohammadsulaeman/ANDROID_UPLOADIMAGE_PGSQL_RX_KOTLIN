package com.example.uploadimages.presenter.read

import android.annotation.SuppressLint
import com.example.uploadimages.network.api.Log
import com.example.uploadimages.network.api.Restfactory
import com.example.uploadimages.network.api.service.RestService
import com.example.uploadimages.network.model.GetDataImagesResponseJson
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class GetDataImagesPresenter(val viewInterface: GetDataImageViewInterface) : GetViewInterface {

    private var TAG = "MainActivity"

    @SuppressLint("CheckResult")
    override fun getImages() {
        getObserverImages().subscribeWith(getObserverDispose())
    }

    fun getObserverImages() : Observable<GetDataImagesResponseJson> {
        return Restfactory.createService(RestService::class.java, "admin","123456")
            .getDataImages()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getObserverDispose() = object : DisposableObserver<GetDataImagesResponseJson>(){
        override fun onNext(responseJson: GetDataImagesResponseJson) {
            Log.d(TAG,"OnNext = ${responseJson.images}")
            viewInterface.onGetDataImagesAll(responseJson)
            viewInterface.onSuccess(responseJson.message)
            viewInterface.hideLoading()
        }

        override fun onError(e: Throwable) {
            Log.d(TAG,"OnError = ${e.message}")
            viewInterface.showLoading()
            viewInterface.onError(e.message.toString())
        }

        override fun onComplete() {
            Log.d(TAG,"OnComplete")
            viewInterface.hideLoading()
        }

    }
}