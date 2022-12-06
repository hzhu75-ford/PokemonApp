package com.example.pokemon.model.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Singleton
object RetrofitBuilder {
    private const val BASE_URL = "http://pokeapi.co/api/v2/pokemon/"

    const val INITIAL_URL = "https://pokeapi.co/api/v2/pokemon/?offset=15&limit=15"

    var loggingInterceptor =  HttpLoggingInterceptor().also {
        it.level = HttpLoggingInterceptor.Level.BODY
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val apiService: RetroAPIService = getRetrofit().create(RetroAPIService::class.java)
}