package com.example.ui.pokedexHome

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.example.ui.pokedexHome.composables.HomeTopBar
import com.example.ui.pokedexHome.composables.OfflineOrNoData
import com.example.ui.pokedexHome.composables.PokemonGrid

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PokedexHomeScreen(
    state: PokedexHomeState,
    events: PokedexHomeEvents,
) {

    Scaffold {
        Column {
            HomeTopBar(
                state = state,
                events = events
            )

            if (state.pokemon.isNotEmpty()) {
                PokemonGrid(
                    state = state,
                    events = events
                )
            } else {
                OfflineOrNoData(pullToRefresh = events::getPokemon)
            }
        }

    }
}




