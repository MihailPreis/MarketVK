package com.example.marketvk.ui.screens.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketvk.AppKunteynir
import com.example.marketvk.repository.ProductsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductsViewModel constructor(
    private val productsRepository: ProductsRepository = AppKunteynir.productsRepository
) : ViewModel() {
    private val pageSize = 30
    private val _state = MutableStateFlow(ProductsUiState())
    val state: StateFlow<ProductsUiState> = _state.asStateFlow()

    init {
        loadCategories()
        reloadProducts()
    }

    fun reloadProducts() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    page = 1,
                    products = arrayListOf(),
                    isProductsLoading = true
                )
            }

            loadProducts()

            _state.update { it.copy(isProductsLoading = false) }
        }
    }

    fun loadMoreProducts() {
        if (!_state.value.hasMore) return

        viewModelScope.launch {
            _state.update {
                it.copy(page = it.page + 1)
            }

            loadProducts()
        }
    }

    fun loadCategories() {
        viewModelScope.launch {
            _state.update { it.copy(isCategoriesLoading = true) }
            _state.value.isCategoriesLoading = true
            try {
                val categories = productsRepository.getCategories()
                _state.update {
                    it.copy(
                        categories = categories,
                        categoriesError = null,
                        isCategoriesLoading = false
                    )
                }
            } catch (error: Exception) {
                _state.update {
                    it.copy(
                        categoriesError = error,
                        isCategoriesLoading = false
                    )
                }
            }
        }
    }

    fun setCategory(category: String) {
        _state.update {
            it.copy(
                searchQuery = "",
                selectedCategory = if (it.selectedCategory == category) null else category
            )
        }

        reloadProducts()
    }

    private suspend fun loadProducts() {
        try {
            val result = productsRepository.getProducts(
                _state.value.searchQuery,
                _state.value.selectedCategory,
                (_state.value.page - 1) * pageSize,
                pageSize
            )

            _state.update {
                it.copy(
                    productsError = null,
                    hasMore = result.products.count() > it.products.count(),
                    products = it.products.plus(result.products)
                )
            }
        } catch (error: Exception) {
            _state.update { it.copy(productsError = error) }
        }
    }

    fun search(query: String) {
        _state.update { it.copy(searchQuery = query, selectedCategory = null) }
        reloadProducts()
    }
}