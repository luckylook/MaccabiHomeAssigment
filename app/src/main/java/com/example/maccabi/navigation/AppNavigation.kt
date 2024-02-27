package com.example.maccabi.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.maccabi.ui.screens.CategoryScreen
import com.example.maccabi.ui.screens.ProductsScreen
import com.example.maccabi.ui.viewmodels.CatalogViewModel

@Composable
fun MaccabiNavigation() {
    val navController = rememberNavController()
    val catalogViewModel: CatalogViewModel = hiltViewModel()
    NavHost(navController = navController, startDestination = AppRoutes.CATEGORY) {
        composable(AppRoutes.CATEGORY) {
            CategoryScreen(navController, catalogViewModel)
        }
        composable(AppRoutes.PRODUCTS) {
            ProductsScreen(navController, catalogViewModel)
        }
    }
}

