package com.example.marketvk.ui.screens.productdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.marketvk.repository.ProductsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductDetailsViewModel constructor(
    private val id: Long,
    private val productsRepository: ProductsRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ProductDetailsUiState())
    val state: StateFlow<ProductDetailsUiState> = _state.asStateFlow()

    init {
        loadProduct()
    }

    fun loadProduct() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            try {
                val product = productsRepository.getProduct(id)
                _state.update { it.copy(isLoading = false, error = null, product = product) }
            } catch (error: Exception) {
                _state.update { it.copy(isLoading = false, error = error, product = null) }
            }
        }
    }
}

class ProductDetailsViewModelFactory(
    private val id: Long,
    private val productsRepository: ProductsRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductDetailsViewModel(id, productsRepository) as T
    }
}