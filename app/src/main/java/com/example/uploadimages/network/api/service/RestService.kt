package com.example.uploadimages.network.api.service

import com.example.uploadimages.network.model.*
import io.reactivex.Observable
import retrofit2.http.*

interface RestService {
    @GET("image_all")
    fun getDataImages() : Observable<GetDataImagesResponseJson>


    @POST("insertImages")
    fun insertImages(@Body requestJson: TambahImagesRequestJson) : Observable<BaseResponse>

    @DELETE("delete_images/{image_id}")
    fun deleteImages(@Path("image_id") image_id: String) : Observable<BaseResponse>

    @POST("update_images/{image_id}")
    fun updateImages(@Body requestJson: UpdateRequestJson, @Path("image_id") image_id: String) : Observable<BaseResponse>
}