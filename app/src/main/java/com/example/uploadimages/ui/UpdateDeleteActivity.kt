package com.example.uploadimages.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.uploadimages.R
import com.example.uploadimages.constants.Constant
import com.example.uploadimages.databinding.ActivityUpdateDeleteBinding
import com.example.uploadimages.network.api.Log
import com.example.uploadimages.network.api.PicassoTrustAll
import com.example.uploadimages.network.model.UpdateRequestJson
import com.example.uploadimages.presenter.updatedelete.UpdateDeletePresenster
import com.example.uploadimages.presenter.updatedelete.UpdateDeleteView
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class UpdateDeleteActivity : AppCompatActivity(), UpdateDeleteView {
    private lateinit var binding: ActivityUpdateDeleteBinding
    private lateinit var presenster: UpdateDeletePresenster
    lateinit var imagesArray : ByteArray
    lateinit var decoded : Bitmap
    private var imageId : String = ""
    private var TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        imageId = intent.getStringExtra("image_id").toString()
        binding.editTextCodeUpddel.setText(intent.getStringExtra("image_id")).toString()
        Log.d(TAG,"ImageID = ${imageId}")
        binding.editTextUpddel.setText(intent.getStringExtra("title"))
        binding.editTextCodeUpddel.isEnabled = false
        val cover = intent.getStringExtra("cover")
        PicassoTrustAll.getInstance(applicationContext)!!
            .load(Constant.IMAGESCOVER + cover)
            .into(binding.imageViewUpdel)
        presenster = UpdateDeletePresenster(this@UpdateDeleteActivity,this)
        binding.imageViewUpdel.setOnClickListener {
            if (Check_Camerapermission()){
                selectImages()
            }
        }
        binding.buttonUpdateImages.setOnClickListener {
            updateImages()
        }
        binding.buttonDeleteImages.setOnClickListener {
            if (imageId != null)
            {
                val delete = presenster.DeleteImages(imageId)
                if (delete != null){
                    startActivity(Intent(applicationContext,MainActivity::class.java))
                }else{
                    Toast.makeText(applicationContext,"Id Images Tidak Ditemukan",Toast.LENGTH_SHORT).show()
                }
            }
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
                binding.imageViewUpdel.setImageBitmap(rotatedBitmap)
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
        if (ContextCompat.checkSelfPermission(applicationContext,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
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

    fun updateImages(){
        if (imageId != null)
        {

            
            val title = binding.editTextUpddel.text.toString()
            val cover = getStringImage(decoded).toString()
            val update = presenster.UpdateImages(imageId,cover,title)

            if (update != null)
            {
                startActivity(Intent(applicationContext,MainActivity::class.java))
            }else{
                Toast.makeText(applicationContext,"update data gagal dilakukan",Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(applicationContext,"ID Images Tidak Ditemukan",Toast.LENGTH_SHORT).show()
        }
    }
    override fun onSuccess(message: String) {
        Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()
    }

    override fun onError(message: String) {
        Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        binding.progressCircularUpddel.visibility = View.VISIBLE
        binding.lineupddel.visibility = View.GONE
    }

    override fun onHideLoading() {
        binding.progressCircularUpddel.visibility = View.GONE
        binding.lineupddel.visibility = View.VISIBLE
    }
}