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
import com.example.ui.pokedexDetails.composables.pokemonInfo.pokemonStats.PokemonStatBars
import com.example.ui.pokedexDetails.composables.pokemonInfo.pokemonStats.PokemonStatNames
import com.example.ui.pokedexDetails.composables.pokemonInfo.pokemonStats.PokemonStatNumbers

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
            style = MaterialTheme.typography.headlineMedium.copy(
                color = color,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(top = 8.dp)
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
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        PokemonStatNames(
            modifier = Modifier.padding(end = 4.dp),
            color = color
        )

        Divider(
            modifier = Modifier
                .height(124.dp)
                .width(2.dp),
            color = colorResource(id = R.color.cream_1)
        )

        PokemonStatNumbers(
            modifier = Modifier.padding(start = 2.dp),
            stats = stats,
        )

        PokemonStatBars(
            modifier = Modifier,
            stats = stats,
            color = color
        )
    }
}
