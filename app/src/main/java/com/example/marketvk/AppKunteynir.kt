package com.example.marketvk

import com.example.marketvk.network.ProductsService
import com.example.marketvk.repository.ProductsRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppKunteynir {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://dummyjson.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val productsService = retrofit.create(ProductsService::class.java)

    val productsRepository = ProductsRepository(productsService)
}