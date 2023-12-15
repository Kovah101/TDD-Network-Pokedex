package com.example.ui.pokedexHome

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.Resources.Theme
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.R
import com.example.ui.pokedexDetails.composables.pokemonTypeToColor
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
                OfflineOrNoData(
                    pullToRefresh = events::getPokemon
                )
            }
        }

    }
}




