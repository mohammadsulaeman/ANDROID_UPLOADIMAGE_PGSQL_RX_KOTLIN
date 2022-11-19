package com.example.uploadimages.presenter.insert

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import com.example.uploadimages.network.api.Restfactory
import com.example.uploadimages.network.api.service.RestService
import com.example.uploadimages.network.model.BaseResponse
import com.example.uploadimages.network.model.TambahImagesRequestJson
import com.example.uploadimages.ui.MainActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class AddImagesPresenter(val context: Context,val view: AddImagesView) : AddImagesInterface {
    private  var TAG = "MainActivity"

    @SuppressLint("CheckResult")
    override fun insertImagesAdd(imagesId: String, cover: String, title: String) {
        getObserverInsert(imagesId,cover, title).subscribeWith(getObserverResponse())
    }

    fun getObserverInsert(imagesId: String,cover : String, title: String) : Observable<BaseResponse>{
        var requestJson = TambahImagesRequestJson(imagesId,cover, title)
        return Restfactory.createService(RestService::class.java,cover,title)
            .insertImages(requestJson)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getObserverResponse() = object : DisposableObserver<BaseResponse>(){
        override fun onNext(baseResponse: BaseResponse) {
            if(baseResponse.status.equals("success")){
                view.onHideLoading()
                var intent = Intent(context,MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
                view.onSuccess(baseResponse.message)
            }
        }

        override fun onError(e: Throwable) {
            view.onError(e.message.toString())
            view.showLoading()
        }

        override fun onComplete() {
            view.onHideLoading()
        }

    }


}