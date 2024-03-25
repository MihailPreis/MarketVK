package com.example.marketvk.ui.screens.products

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.marketvk.R
import com.example.marketvk.ui.Coordinator
import com.example.marketvk.ui.components.CategoryComponent
import com.example.marketvk.ui.components.ProductCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(
    vm: ProductsViewModel,
    coordinator: Coordinator
) {
    val state by vm.state.collectAsState()
    val focusManager = LocalFocusManager.current

    val ptrState = rememberPullToRefreshState(enabled = { !state.isProductsLoading })

    if (ptrState.isRefreshing) {
        LaunchedEffect(true) {
            vm.reloadProducts()
            ptrState.endRefresh()
        }
    }

    Box(Modifier.nestedScroll(ptrState.nestedScrollConnection)) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            item(span = { GridItemSpan(2) }) {
                TextField(
                    value = state.searchQuery,
                    onValueChange = { vm.search(it) },
                    label = { Text(stringResource(R.string.search)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .padding(bottom = 10.dp),
                    keyboardOptions = KeyboardOptions(
                        autoCorrect = false,
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions {
                        focusManager.clearFocus()
                        vm.search(state.searchQuery)
                    },
                    singleLine = true
                )
            }

            item(span = { GridItemSpan(2) }) {
                CategoriesList(vm, state)
            }

            items(state.products) {
                ProductCard(it, onTap = { coordinator.openDetails(it.id) })

                if (state.products.indexOf(it) == state.products.count() - 10) {
                    vm.loadMoreProducts()
                }
            }
        }

        PullToRefreshContainer(state = ptrState, Modifier.align(Alignment.TopCenter))
    }
}

@Composable
private fun CategoriesList(
    vm: ProductsViewModel,
    state: ProductsUiState
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(state.categories) { category ->
            CategoryComponent(
                category,
                state.selectedCategory == category,
                Modifier.clickable { vm.setCategory(category) })
        }
    }
}
