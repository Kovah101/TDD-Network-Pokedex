package com.example.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.ui.pokedexDetails.PokedexDetailsScreen
import com.example.ui.pokedexDetails.PokedexDetailsViewModel

fun NavGraphBuilder.addPokedexDetails(
    navController: NavHostController
) {
    composable(
        route = PokedexScreens.PokedexDetails.route
    ) {
        val viewModel: PokedexDetailsViewModel = hiltViewModel()
        val state = viewModel.state.collectAsState()

        LaunchedEffect(key1 = viewModel.navigationEvent) {
            pokedexDetailsEventHandler(
                viewModel = viewModel,
                navController = navController
            )
        }

        PokedexDetailsScreen(
            state = state.value,
            events = viewModel
        )
    }
}

private suspend fun pokedexDetailsEventHandler(
    viewModel: PokedexDetailsViewModel,
    navController: NavHostController
) {
    viewModel.navigationEvent.collect { event ->
        when (event) {
            NavigateBack -> {
                navController.popBackStack()
            }

            else -> Unit
        }
    }
}