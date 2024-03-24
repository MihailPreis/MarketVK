package com.example.marketvk.ui.screens.products

import com.example.marketvk.data.ProductModel

data class ProductsUiState(
    var page: Int = 1,
    var hasMore: Boolean = false,

    var isProductsLoading: Boolean = false,
    var products: List<ProductModel> = arrayListOf(),
    var productsError: Exception? = null,

    var categories: List<String> = arrayListOf(),
    var isCategoriesLoading: Boolean = false,
    var categoriesError: Exception? = null,

    var searchQuery: String = "",
    var selectedCategory: String? = null
)