package com.example.uploadimages.ui

import android.R.id.text2
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.uploadimages.databinding.ActivitySplashBinding


class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
    var status : Int = 0
    lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handler = Handler()
        Thread(object : Runnable{
            override fun run() {
                while (status < 100) {
                    status += 1
                    try {
                        Thread.sleep(200)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                    handler.post(Runnable {
                        binding.textPresentase.text = "$status%"
                        binding.progressCircular.setProgress(status)
                        if (status == 100) {
                            startActivity(Intent(applicationContext,MainActivity::class.java))
                        }
                    })
                }
            }

        }).start()

    }
}