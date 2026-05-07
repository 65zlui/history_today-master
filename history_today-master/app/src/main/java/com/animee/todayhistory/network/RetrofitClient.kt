package com.animee.todayhistory.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private const val BASE_URL_V = "http://v.juhe.cn"

    private val _apiService: ApiService by lazy {
        createApiService()
    }

    fun getApiService(): ApiService = _apiService

    private fun createApiService(): ApiService {
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .also { builder ->
                val loggingInterceptor = HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
                builder.addInterceptor(loggingInterceptor)
            }
            .build()

        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL_V)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()

        return retrofit.create(ApiService::class.java)
    }
}
