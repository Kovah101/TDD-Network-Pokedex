package com.example.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navigation

@Composable
fun AppNavigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = POKEDEX_GRAPH
    ) {
        pokedexSubGraph(navController = navController)
    }
}

private fun NavGraphBuilder.pokedexSubGraph(
    navController: NavHostController
) {
    navigation(
        route = POKEDEX_GRAPH,
        startDestination = PokedexScreens.PokedexHome.route
    ) {
        addPokedexHome(navController = navController)
        addPokedexDetails(navController = navController)
    }
}

private const val POKEDEX_GRAPH = "pokedex_graph"

enum class PokedexScreens(val route: String) : NavigationRoute {
    PokedexHome("pokedex_home"),
    PokedexDetails("pokedex_details/{${NavArguments.POKEMON_ID.navString}}"),
    PokedexDetailsGeneric("pokedex_details")
}

enum class NavArguments(val navString: String) {
    POKEMON_ID("id")
}

