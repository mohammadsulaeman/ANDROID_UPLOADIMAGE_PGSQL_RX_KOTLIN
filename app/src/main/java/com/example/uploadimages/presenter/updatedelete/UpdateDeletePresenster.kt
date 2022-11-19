package com.example.uploadimages.presenter.updatedelete

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import com.example.uploadimages.network.api.Restfactory
import com.example.uploadimages.network.api.service.RestService
import com.example.uploadimages.network.model.BaseResponse
import com.example.uploadimages.network.model.UpdateRequestJson
import com.example.uploadimages.ui.MainActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class UpdateDeletePresenster(val context: Context,val view: UpdateDeleteView) : UpdateDeleteInterface{

    @SuppressLint("CheckResult")
    override fun DeleteImages(image_id: String) {
        getDisposibleDelete(image_id).subscribeWith(DisposableDelete())
    }

    @SuppressLint("CheckResult")
    override fun UpdateImages(image_id: String, cover: String, title: String) {
       getObserverEdit(image_id, cover, title).subscribeWith(DisposebleUpdate())
    }

    //update
    fun getObserverEdit(image_id: String,cover: String,title: String) : Observable<BaseResponse>
    {
        var requestJson = UpdateRequestJson(image_id,cover,title)
        return Restfactory.createService(RestService::class.java,image_id,title)
            .updateImages(requestJson, image_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun DisposebleUpdate() = object : DisposableObserver<BaseResponse>()
    {
        override fun onNext(t: BaseResponse) {
            if (t.status.equals("success")){
                view.onSuccess(t.message)
                val intent = Intent(context,MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
                view.onHideLoading()
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

    //delete
    fun getDisposibleDelete(image_id: String) : Observable<BaseResponse>
    {
        return Restfactory.createService(RestService::class.java,image_id,image_id)
            .deleteImages(image_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun DisposableDelete() = object : DisposableObserver<BaseResponse>()
    {
        override fun onNext(t: BaseResponse) {
            if (t.status.equals("success"))
            {
                view.onHideLoading()
                val intent = Intent(context,MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
                view.onSuccess(t.message)
            }
        }

        override fun onError(e: Throwable) {
            view.showLoading()
            view.onError(e.localizedMessage)
        }

        override fun onComplete() {
            view.onHideLoading()
        }

    }
}