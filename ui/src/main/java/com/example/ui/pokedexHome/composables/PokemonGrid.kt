package com.example.ui.pokedexHome.composables

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.database.Pokemon
import com.example.database.PokemonRegion
import com.example.ui.R
import com.example.ui.pokedexHome.PokedexHomeEvents
import com.example.ui.pokedexHome.PokedexHomeState
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PokemonGrid(
    state: PokedexHomeState,
    events: PokedexHomeEvents,
) {

    val tabs = listOf("Kanto", "Johto")
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { tabs.size }
    )

    Log.d("PokemonGrid", "state.pokemon: ${state.pokemon.size}")

    val pokemonList = state.pokemon.filter {
        when (state.searchMode) {
            SearchMode.NAME -> {
                it.name.contains(state.searchText, ignoreCase = true)
            }

            SearchMode.NUMERICAL -> {
                it.id.toString().contains(state.searchText, ignoreCase = true)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.cream_4))
    ) {
        TabRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            selectedTabIndex = pagerState.currentPage,
            backgroundColor = colorResource(id = R.color.red),
            divider = {},
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    height = 2.dp,
                    color = colorResource(id = R.color.red_1)
                )
            }
        ) {
            tabs.forEachIndexed { index, tab ->
                val selected = pagerState.currentPage == index

                Tab(
                    modifier = Modifier,
                    selected = selected,
                    text = {
                        Text(
                            text = tab,
                            style = MaterialTheme.typography.headlineSmall.copy(
                                color = colorResource(id = R.color.cream_4),
                                textAlign = TextAlign.Center,
                                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                            )
                        )
                    },
                    onClick = {
                        scope.launch {
                            if (pagerState.currentPage == 0) {
                                pagerState.animateScrollToPage(page = 1)
                            } else {
                                pagerState.animateScrollToPage(page = 0)
                            }
                        }
                    }
                )
            }
        }

        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth(),
            state = pagerState
        ) { page ->
            val regionPokemon: List<Pokemon>
            when (page) {
                0 -> {
                    regionPokemon = pokemonList.filter { it.region == PokemonRegion.KANTO }
                    Log.d("PokemonGrid", "Kanto regionPokemon: ${regionPokemon.size}")

                    PokemonGridList(
                        pokemonList = regionPokemon,
                        onPokemonClicked = { events.pokemonClicked(it) }
                        )
                }
                1 -> {
                    regionPokemon = pokemonList.filter { it.region == PokemonRegion.JOHTO }
                    Log.d("PokemonGrid", "Johto regionPokemon: ${regionPokemon.size}")

                    PokemonGridList(
                        pokemonList = regionPokemon,
                        onPokemonClicked = { events.pokemonClicked(it) }
                        )
                }
            }
        }
    }
}

@Composable
private fun PokemonGridList(
    pokemonList: List<Pokemon>,
    onPokemonClicked: (Pokemon) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(count = 3),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp)
    ) {

        items(pokemonList) { pokemon ->
            PokemonGridItem(
                pokemon = pokemon,
                onClick = { onPokemonClicked(pokemon) }
            )
        }
    }
}
