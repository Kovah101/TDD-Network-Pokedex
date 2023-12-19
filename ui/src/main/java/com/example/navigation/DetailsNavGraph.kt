package com.example.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.tween
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
        route = PokedexScreens.PokedexDetails.route,
        enterTransition = {
            when (initialState.destination.route) {
                PokedexScreens.PokedexHome.route ->
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(700)
                    )

                else -> null
            }
        },
        exitTransition = {
            when (targetState.destination.route) {
                PokedexScreens.PokedexHome.route ->
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(700)
                    )

                else -> null
            }
        },
        popEnterTransition = {
            when (initialState.destination.route) {
                PokedexScreens.PokedexHome.route ->
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(700)
                    )

                else -> null
            }
        },
        popExitTransition = {
            when (targetState.destination.route) {
                PokedexScreens.PokedexHome.route ->
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(700)
                    )

                else -> null
            }
        }
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