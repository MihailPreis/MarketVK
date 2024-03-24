package com.example.marketvk.repository

import com.example.marketvk.data.ProductModel
import com.example.marketvk.data.ProductsBoxModel
import com.example.marketvk.network.ProductsService

class ProductsRepository constructor(
    private val productsService: ProductsService
) {
    suspend fun getProducts(
        query: String,
        category: String?,
        skip: Int,
        limit: Int
    ): ProductsBoxModel {
        return if (query.isNotBlank()) {
            productsService.getProductsByQuery(query, skip, limit)
        } else if (!category.isNullOrBlank()) {
            productsService.getProductsByCategory(category, skip, limit)
        } else {
            productsService.getAllProducts(skip, limit)
        }
    }

    suspend fun getCategories(): List<String> {
        return productsService.getAllCategories()
    }

    suspend fun getProduct(id: Long): ProductModel {
        return productsService.getProductDetails(id)
    }
}