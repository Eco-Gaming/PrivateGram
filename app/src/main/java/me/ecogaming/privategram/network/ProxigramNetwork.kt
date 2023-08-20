package me.ecogaming.privategram.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ProxigramNetwork {

    val retrofit: ProxigramApi by lazy {
        Retrofit.Builder()
            .baseUrl("http://192.168.1.122:3000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProxigramApi::class.java)
    }
}