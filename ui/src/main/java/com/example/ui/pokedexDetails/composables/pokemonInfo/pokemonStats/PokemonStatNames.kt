package com.example.ui.pokedexDetails.composables.pokemonInfo.pokemonStats

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun PokemonStatNames(
    modifier: Modifier,
    color: Color
) {
    val pokemonStatNames = listOf(
        "HP",
        "Attack",
        "Defense",
        "Sp. Atk",
        "Sp. Def",
        "Speed"
    )

    Column(modifier = modifier) {
        for (statName in pokemonStatNames) {
            PokemonStatName(
                modifier = Modifier.padding(bottom = 2.dp),
                statName = statName,
                color = color
            )
        }
    }
}

@Composable
private fun PokemonStatName(
    modifier: Modifier,
    statName: String,
    color: Color
) {
    Text(
        text = statName,
        style = MaterialTheme.typography.bodyLarge.copy(
            color = color,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold
        ),
        modifier = modifier.padding(horizontal = 8.dp)
    )
}