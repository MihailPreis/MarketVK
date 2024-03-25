package com.example.marketvk.ui.components

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.marketvk.repository.ProductsRepository
import com.example.marketvk.ui.Coordinator
import com.example.marketvk.ui.screens.productdetails.ProductDetailsScreen
import com.example.marketvk.ui.screens.productdetails.ProductDetailsViewModelFactory
import com.example.marketvk.ui.screens.products.ProductsScreen
import com.example.marketvk.ui.screens.products.ProductsViewModelFactory


enum class NavPath(val path: String) {
    PRODUCTS("products"),
    DETAILS("productDetails");
}

@Composable
fun AppComponent(productsRepository: ProductsRepository) {
    val navController = rememberNavController()
    val coordinator = Coordinator(navController)

    NavHost(navController, NavPath.PRODUCTS.path) {
        composable(NavPath.PRODUCTS.path) {
            ProductsScreen(
                vm = viewModel(factory = ProductsViewModelFactory(productsRepository)),
                coordinator = coordinator
            )
        }

        composable("${NavPath.DETAILS.path}/{id}") {
            ProductDetailsScreen(
                vm = viewModel(
                    factory = ProductDetailsViewModelFactory(
                        it.arguments?.getString("id")?.toLong() ?: 0,
                        productsRepository
                    )
                ),
                coordinator = coordinator
            )
        }
    }
}