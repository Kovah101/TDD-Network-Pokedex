package com.example.ui.pokedexDetails.composables.pokemonInfo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
fun PokemonAbout(
    modifier: Modifier,
    height: Int,
    weight: Int
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "About",
            style = MaterialTheme.typography.headlineSmall.copy(
                color = colorResource(id = R.color.black),
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(16.dp)
        )

        Text(
            text = "Height: $height",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = colorResource(id = R.color.black),
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(16.dp)
        )

        Text(
            text = "Weight: $weight",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = colorResource(id = R.color.black),
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(16.dp)
        )
    }
}