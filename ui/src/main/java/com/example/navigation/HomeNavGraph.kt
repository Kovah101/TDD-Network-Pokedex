package com.example.navigation

import android.content.Context
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.ui.pokedexHome.PokedexHomeScreen
import com.example.ui.pokedexHome.PokedexHomeViewModel

fun NavGraphBuilder.addPokedexHome(
    navController: NavHostController
) {
    composable(
        route = PokedexScreens.PokedexHome.route
    ) {
        val viewModel: PokedexHomeViewModel = hiltViewModel()
        val state = viewModel.state.collectAsState()
        val context = LocalContext.current

        LaunchedEffect(key1 = viewModel.navigationEvent) {
            pokedexHomeEventHandler(
                viewModel = viewModel,
                navController = navController,
                context = context
            )
        }


        PokedexHomeScreen(
            state = state.value,
            events = viewModel
        )
    }
}

private suspend fun pokedexHomeEventHandler(
    viewModel: PokedexHomeViewModel,
    navController: NavHostController,
    context: Context
) {
    viewModel.navigationEvent.collect { event ->
        when (event) {
            PokedexScreens.PokedexDetails -> {
                navController.navigate(PokedexScreens.PokedexDetailsGeneric.route + "/${viewModel.state.value.selectedPokemon.id}")
            }

            else -> Unit
        }
    }
}