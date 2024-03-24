package com.example.marketvk.network

import com.example.marketvk.data.ProductModel
import com.example.marketvk.data.ProductsBoxModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

public interface ProductsService {
    @GET("/products")
    suspend fun getAllProducts(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): ProductsBoxModel

    @GET("/products/search")
    suspend fun getProductsByQuery(
        @Query("q") query: String,
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): ProductsBoxModel

    @GET("/products/category/{title}")
    suspend fun getProductsByCategory(
        @Path("title") category: String,
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): ProductsBoxModel

    @GET("/products/categories")
    suspend fun getAllCategories(): List<String>

    @GET("/products/{id}")
    suspend fun getProductDetails(@Path("id") id: Long): ProductModel
}