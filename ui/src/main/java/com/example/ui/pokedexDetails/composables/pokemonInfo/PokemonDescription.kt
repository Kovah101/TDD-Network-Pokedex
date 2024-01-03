package com.example.ui.pokedexDetails.composables.pokemonInfo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PokemonDescription(
    modifier: Modifier,
    description : String,
    color: Color
) {
    Column(
        modifier = modifier
            .padding(horizontal = 8.dp)
            .wrapContentHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Description",
            style = MaterialTheme.typography.headlineMedium.copy(
                color = color,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ),
            modifier = Modifier.padding(vertical = 4.dp)
        )

        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge.copy(
                textAlign = TextAlign.Center,
                fontSize = 14.sp
            ),
            minLines = 3,
            maxLines = 3,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .wrapContentHeight()
        )
    }
}