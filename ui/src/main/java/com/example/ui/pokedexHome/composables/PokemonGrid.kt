package com.example.ui.pokedexHome.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
    events: PokedexHomeEvents,
) {

    val pokemon = state.pokemon.filter {
        when (state.searchMode) {
            SearchMode.NAME -> {
                it.name.contains(state.searchText, ignoreCase = true)
            }

            SearchMode.NUMERICAL -> {
                it.id.toString().contains(state.searchText, ignoreCase = true)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.cream))
            .padding(horizontal = 16.dp)
    ) {
        LazyVerticalGrid(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxSize(),
            columns = GridCells.Fixed(count = 3),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(pokemon) { pokemon ->
                PokemonGridItem(
                    pokemon = pokemon,
                    onClick = { events.pokemonClicked(pokemon = pokemon) }
                )
            }
        }
    }
}