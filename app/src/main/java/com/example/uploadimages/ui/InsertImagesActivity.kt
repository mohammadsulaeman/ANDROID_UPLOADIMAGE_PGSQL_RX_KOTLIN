package com.example.uploadimages.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Base64
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.uploadimages.constants.Constant
import com.example.uploadimages.databinding.ActivityInsertImagesBinding
import com.example.uploadimages.network.api.Log
import com.example.uploadimages.presenter.insert.AddImagesPresenter
import com.example.uploadimages.presenter.insert.AddImagesView
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import kotlin.random.Random

class InsertImagesActivity : AppCompatActivity(), AddImagesView {
    lateinit var binding: ActivityInsertImagesBinding
    lateinit var presenter: AddImagesPresenter
    var TAG : String = "MainActivity"
    lateinit var imagesArray : ByteArray
    var coverimages : String = ""
    lateinit var decoded : Bitmap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertImagesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = AddImagesPresenter(this@InsertImagesActivity,this)

        binding.imageViewAdd.setOnClickListener {
            selectImages()
        }

        binding.buttonAddImages.setOnClickListener {
           uploadImage()

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            if(requestCode == 1){
                val extras = data!!.extras
                val imageBitmap = extras!!.get("data") as Bitmap
                val matrix = Matrix()
                val rotatedBitmap = Bitmap.createBitmap(imageBitmap,0,0,imageBitmap.width,imageBitmap.height,matrix,true)
                val baos = ByteArrayOutputStream()
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG,20,baos)
                binding.imageViewAdd.setImageBitmap(rotatedBitmap)
                imagesArray = baos.toByteArray()
                decoded = BitmapFactory.decodeStream(ByteArrayInputStream(baos.toByteArray()))

            }
        }
    }
    fun getStringImage(bmp: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.JPEG, 20, baos)
        imagesArray = baos.toByteArray()
        return Base64.encodeToString(imagesArray, Base64.DEFAULT)
    }
    fun Check_Camerapermission() : Boolean {
        if (ContextCompat.checkSelfPermission(applicationContext,Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
        {
            return true
        }else{
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                   requestPermissions(arrayOf(Manifest.permission.CAMERA),Constant.permission_camera_code)
                }
            }catch (e : Exception){
                e.printStackTrace()
                throw e
            }
        }
        return false
    }
    fun selectImages(){
        if (Check_Camerapermission()){
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent,1)
        }
    }
    override fun onSuccess(message: String) {
        Toast.makeText(applicationContext,message,Toast.LENGTH_LONG).show()
    }

    override fun onError(message: String) {
        Toast.makeText(applicationContext,message,Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        binding.progressCircularAdd.visibility = View.VISIBLE
        binding.line1.visibility = View.GONE
    }

    override fun onHideLoading() {
        binding.progressCircularAdd.visibility = View.GONE
        binding.line1.visibility = View.VISIBLE
    }

    fun uploadImage(){
        val title = binding.editTextAdd.text.toString()
        val imageId = binding.editTextCodeAdd.text.toString()
        Log.d(TAG, "imageId = ${imageId}")
        coverimages = getStringImage(decoded).toString()
        if (imagesArray == null){
            Toast.makeText(applicationContext,"Image Wajib Di isi",Toast.LENGTH_SHORT).show()
        }else if (TextUtils.isEmpty(imageId)){
            Toast.makeText(applicationContext,"Code Wajib Di isi",Toast.LENGTH_SHORT).show()
        }
        else if (TextUtils.isEmpty(title)){
            Toast.makeText(applicationContext,"Title Wajib Di isi",Toast.LENGTH_SHORT).show()
        }else{
            presenter.insertImagesAdd(imageId,coverimages,title)
            if (presenter != null){
                startActivity(Intent(applicationContext,MainActivity::class.java))
            }
        }
    }
}


