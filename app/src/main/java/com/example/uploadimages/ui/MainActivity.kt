package com.example.uploadimages.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uploadimages.R
import com.example.uploadimages.adapter.ImagesAdapter
import com.example.uploadimages.databinding.ActivityMainBinding
import com.example.uploadimages.network.api.Log
import com.example.uploadimages.network.model.GetDataImagesResponseJson
import com.example.uploadimages.presenter.read.GetDataImageViewInterface
import com.example.uploadimages.presenter.read.GetDataImagesPresenter


class MainActivity : AppCompatActivity(), GetDataImageViewInterface{
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ImagesAdapter
    private lateinit var presenter: GetDataImagesPresenter
    var TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(applicationContext,InsertImagesActivity::class.java))
        }
        presenter = GetDataImagesPresenter(this)
        binding.recyclerviewRead.setHasFixedSize(true)
        binding.recyclerviewRead.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        presenter.getImages()
    }

    override fun showLoading() {
        binding.progressCircularRead.visibility = View.VISIBLE
        binding.recyclerviewRead.visibility = View.GONE
        binding.fabAdd.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.progressCircularRead.visibility = View.GONE
        binding.recyclerviewRead.visibility = View.VISIBLE
        binding.fabAdd.visibility = View.VISIBLE
    }

    override fun onSuccess(message: String) {
        Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()
    }

    override fun onError(message: String) {
        Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()
    }

    override fun onGetDataImagesAll(responseJson: GetDataImagesResponseJson) {
        if (responseJson != null){
            Log.d(TAG, "OnGetDataUsers = ${responseJson.images}")
            adapter = ImagesAdapter(this@MainActivity,responseJson.images)
            binding.recyclerviewRead.adapter = adapter
        }
    }


}