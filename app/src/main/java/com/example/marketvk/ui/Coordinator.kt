package com.example.marketvk.ui

import androidx.navigation.NavController
import com.example.marketvk.ui.components.NavPath


class Coordinator(
    private val navController: NavController
) {
    fun back() {
        navController.popBackStack()
    }

    fun openDetails(id: Long) {

        navController.navigate("${NavPath.DETAILS.path}/${id}")
    }
}