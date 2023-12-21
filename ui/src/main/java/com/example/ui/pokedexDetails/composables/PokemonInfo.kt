package com.example.ui.pokedexDetails.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.database.Pokemon
import com.example.ui.R
import com.example.ui.pokedexDetails.composables.pokemonInfo.PokemonAbout
import com.example.ui.pokedexDetails.composables.pokemonInfo.PokemonTypes
import com.example.ui.pokedexDetails.composables.pokemonInfo.PokemonStats

@Composable
fun PokemonInfo(
    modifier: Modifier,
    pokemon: Pokemon
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.65f)
            .background(color = colorResource(id = R.color.cream), shape = RoundedCornerShape(16.dp))
    ) {

        PokemonTypes(
            modifier = Modifier,
            types = pokemon.types
        )

        PokemonAbout(
            modifier = Modifier,
            height = pokemon.height,
            weight = pokemon.weight,
            color = colorResource(id = pokemonTypeToColor(pokemon.types.firstOrNull()))
        )

        PokemonStats(
            modifier = Modifier,
            stats = pokemon.stats,
            color = colorResource(id = pokemonTypeToColor(pokemon.types.firstOrNull()))
        )

    }
}