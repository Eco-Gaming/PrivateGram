package me.ecogaming.privategram.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ProxigramNetwork {

    val retrofit: ProxigramApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://proxigram.protokolla.fi/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProxigramApi::class.java)
    }
}