package com.example.cryptoprice.network

import com.example.cryptoprice.network.model.CurrentPriceList
import retrofit2.http.GET

interface Api {

    //https://api.bithumb.com/public/ticker/ALL_KRW

    @GET("public/ticker/ALL_KRW")
    suspend fun getCurrentCoinList() : CurrentPriceList
}