package com.example.ui.pokedexDetails.composables.pokemonInfo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
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
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Stats",
            style = MaterialTheme.typography.headlineSmall.copy(
                color = colorResource(id = R.color.black),
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(16.dp)
        )

        Text(
            text = "HP: ${stats[0]}",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = colorResource(id = R.color.black),
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(16.dp)
        )

        Text(
            text = "Attack: ${stats[1]}",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = colorResource(id = R.color.black),
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(16.dp)
        )

        Text(
            text = "Defense: ${stats[2]}",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = colorResource(id = R.color.black),
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(16.dp)
        )

        Text(
            text = "Special Attack: ${stats[3]}",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = colorResource(id = R.color.black),
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(16.dp)
        )

        Text(
            text = "Special Defense: ${stats[4]}",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = colorResource(id = R.color.black),
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(16.dp)
        )

        Text(
            text = "Speed: ${stats[5]}",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = colorResource(id = R.color.black),
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(16.dp)
        )
    }
}