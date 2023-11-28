package com.example.tddnetworkpokedex.core

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.navigation.AppNavigation

@Composable
fun PokedexApp() {

    val navController = rememberNavController()

    AppNavigation(
        navController = navController
    )
}