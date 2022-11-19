package com.example.uploadimages.network.api

import android.content.Context
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object PicassoTrustAll {

    private val client: OkHttpClient? = null
    private var mInstance: Picasso? = null
    private val httpClient = OkHttpClient.Builder()
    fun getUnsafeOkHttpClient(): OkHttpClient? {
        return try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts =
                arrayOf<TrustManager>(
                    object : X509TrustManager {
                        @Throws(CertificateException::class)
                        override fun checkClientTrusted(
                            chain: Array<X509Certificate>,
                            authType: String
                        ) {
                        }

                        @Throws(CertificateException::class)
                        override fun checkServerTrusted(
                            chain: Array<X509Certificate>,
                            authType: String
                        ) {
                        }

                        override fun getAcceptedIssuers(): Array<X509Certificate> {
                            return arrayOf()
                        }
                    }
                )

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory
            httpClient.sslSocketFactory(
                sslSocketFactory,
                trustAllCerts[0] as X509TrustManager
            )
            httpClient.hostnameVerifier { hostname, session -> true }
            httpClient.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private fun PicassoTrustAll(context: Context) {
        mInstance = Picasso.Builder(context)
            .downloader(OkHttp3Downloader(getUnsafeOkHttpClient()))
            .listener { picasso, uri, exception ->
                Log.e(
                    "PICASSO",
                    exception.toString()
                )
            }.build()
    }

    fun getInstance(context: Context): Picasso? {
        if (mInstance == null) {
            PicassoTrustAll(context)
        }
        return mInstance
    }
}