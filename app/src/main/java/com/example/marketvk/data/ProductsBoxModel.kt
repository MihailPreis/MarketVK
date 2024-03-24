package com.example.marketvk.data

data class ProductsBoxModel(
    val products: List<ProductModel>,
    val total: Long,
    val skip: Long,
    val limit: Long,
)
