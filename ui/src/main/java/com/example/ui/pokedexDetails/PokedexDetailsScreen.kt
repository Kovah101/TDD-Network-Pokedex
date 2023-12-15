package com.example.ui.pokedexDetails

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.example.ui.pokedexDetails.composables.PokemonDetails
import com.example.ui.pokedexHome.composables.OfflineOrNoData

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PokedexDetailsScreen(
    state: PokedexDetailsState,
    events: PokedexDetailsEvents
) {

    BackHandler(enabled = true, onBack = { events.backClicked() })

    Scaffold {
        if (state.pokemon == null) {
            OfflineOrNoData(pullToRefresh = events::getPokemonDetails)
        } else {
            PokemonDetails(
                pokemon = state.pokemon,
                events = events
            )
        }
    }
}

