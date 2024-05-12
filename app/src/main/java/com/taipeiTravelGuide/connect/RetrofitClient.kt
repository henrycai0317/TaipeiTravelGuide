package com.taipeiTravelGuide.connect

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://www.travel.taipei/" //Url host
    private var retrofit: Retrofit? = null

    fun getRetrofitInstance(): Retrofit {
        val gson = GsonBuilder().setLenient().create()
        return retrofit ?: Retrofit.Builder()
            .baseUrl(BASE_URL) //Url host
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}