package com.example.ui.pokedexDetails.composables.pokemonInfo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ui.R

@Composable
fun PokemonStats(
    modifier: Modifier,
    stats: List<Int>,
    color: Color
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Stats",
            style = MaterialTheme.typography.headlineSmall.copy(
                color = color,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(top = 32.dp, bottom = 8.dp)
        )

        PokemonStatsBars(
            modifier = Modifier,
            stats = stats,
            color = color
        )
    }
}

@Composable
private fun PokemonStatsBars(
    modifier: Modifier,
    stats: List<Int>,
    color: Color
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        PokemonStatNames(
            modifier = Modifier.padding(end = 4.dp),
            color = color
        )

        Divider(
            modifier = Modifier
                .height(124.dp)
                .width(4.dp),
            color = colorResource(id = R.color.dark_cream)
        )

        PokemonStatBars(
            modifier = Modifier.padding(start = 2.dp),
            stats = stats,
            color = color
        )
    }
}

@Composable
private fun PokemonStatNames(
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
        style = MaterialTheme.typography.bodyMedium.copy(
            color = color,
            textAlign = TextAlign.Start
        ),
        modifier = modifier.padding(horizontal = 8.dp)
    )
}

@Composable
private fun PokemonStatBars(
    modifier: Modifier,
    stats: List<Int>,
    color: Color
) {
    Column (modifier = modifier) {
        for (stat in stats) {
            PokemonStatBar(
                modifier = modifier.padding(bottom = 2.dp),
                statName = stat.toString(),
                statValue = stat,
                color = color
            )
        }
    }
}

@Composable
private fun PokemonStatBar(
    modifier: Modifier,
    statName: String,
    statValue: Int,
    color: Color
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = statValue.toString(),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = colorResource(id = R.color.black),
                textAlign = TextAlign.End
            ),
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}
