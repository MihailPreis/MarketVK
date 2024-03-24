package com.example.marketvk.ui.screens.productdetails

import com.example.marketvk.data.ProductModel

data class ProductDetailsUiState(
    var isLoading: Boolean = false,
    var product: ProductModel? = null,
    var error: Exception? = null
)
