package com.sarwoedhi.moviecatalogue.service

import com.sarwoedhi.moviecatalogue.BuildConfig.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val imageURL = "https://image.tmdb.org/t/p/w185/"
const val bgImageURL = "https://image.tmdb.org/t/p/w500/"

class ApiClient {
    companion object {
        private val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(run {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.apply {
                    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                }
            })
            .build()

        fun getClient(): Retrofit {
            return Retrofit.Builder().baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}