package com.example.workmanager.workmanagernetworkapi

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    private val BASE_URL = "http://staging.php-dev.in:8844/trainingapp/api/"

    private val retrofit by lazy {
        val logging = HttpLoggingInterceptor().apply {
            val level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getInstance(): APIServices{
        return retrofit.create(APIServices::class.java)
    }
}