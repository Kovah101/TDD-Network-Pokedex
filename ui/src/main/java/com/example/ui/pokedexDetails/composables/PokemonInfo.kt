package com.example.ui.pokedexDetails.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.database.Pokemon
import com.example.ui.R

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

            Text(
                text = pokemon.types.toString(),
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = colorResource(id = R.color.black),
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(16.dp)
            )

    }
}