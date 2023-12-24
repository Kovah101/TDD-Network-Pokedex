package com.example.ui.pokedexDetails.composables.pokemonInfo.pokemonStats

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ui.R

@Composable
fun PokemonStatNumbers(
    modifier: Modifier,
    stats: List<Int>,
) {
    Column (modifier = modifier) {
        for (stat in stats) {
            PokemonStatNumber(
                modifier = Modifier.padding(bottom = 2.dp),
                statValue = stat,
            )
        }
    }
}

@Composable
private fun PokemonStatNumber(
    modifier: Modifier,
    statValue: Int,
) {
    Text(
        text = statValue.toString(),
        style = MaterialTheme.typography.bodyLarge.copy(
            color = colorResource(id = R.color.black),
            textAlign = TextAlign.End
        ),
        modifier = modifier.padding(horizontal = 8.dp)
    )
}