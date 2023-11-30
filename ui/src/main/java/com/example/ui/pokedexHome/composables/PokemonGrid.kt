package com.example.ui.pokedexHome.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.ui.R
import com.example.ui.pokedexHome.PokedexHomeEvents
import com.example.ui.pokedexHome.PokedexHomeState

@Composable
fun PokemonGrid(
    state: PokedexHomeState,
    events: PokedexHomeEvents
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.cream))
    ) {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Adaptive(minSize = 128.dp)
        ) {
            items(state.pokemon) { pokemon ->
                PokemonGridItem(
                    pokemon = pokemon,
                    onClick = { events.pokemonClicked(pokemon = pokemon) }
                )
            }
        }
    }
}