package com.example.cryptoprice.repository

import com.example.cryptoprice.network.Api
import com.example.cryptoprice.network.RetrofitInstance

class NetWorkRepository {

    private val client = RetrofitInstance.getInstance().create(Api::class.java)

    suspend fun getCurrentCoinList() = client.getCurrentCoinList()
}