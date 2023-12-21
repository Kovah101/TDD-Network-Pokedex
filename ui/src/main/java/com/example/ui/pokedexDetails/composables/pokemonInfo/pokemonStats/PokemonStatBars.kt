package com.example.ui.pokedexDetails.composables.pokemonInfo.pokemonStats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PokemonStatBars(
    modifier: Modifier,
    stats: List<Int>,
    color: Color
) {
    Column (
        modifier = modifier.height(136.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        for (stat in stats) {
            PokemonStatBar(
                modifier = Modifier.padding(vertical = 4.dp),
                statValue = stat,
                color = color
            )
        }
    }
}

@Composable
private fun PokemonStatBar(
    modifier: Modifier,
    statValue: Int,
    color: Color
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(8.dp)
            .padding(start = 2.dp)
            .background(
                color = color.copy(alpha = 0.3f),
                shape = RoundedCornerShape(60.dp)
            )
    ) {

    }
}