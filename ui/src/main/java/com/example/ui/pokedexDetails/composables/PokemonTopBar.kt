package com.example.ui.pokedexDetails.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.database.Pokemon
import com.example.ui.pokedexDetails.PokedexDetailsEvents
import com.example.ui.R
import com.example.ui.pokedexHome.composables.idString

@Composable
fun PokemonTopBar(
    modifier: Modifier,
    pokemon: Pokemon,
    events: PokedexDetailsEvents
) {
    Row(
        modifier = modifier.fillMaxWidth(),
           // .background(color = colorResource(id= R.color.red)), // TODO try framing in red to stop glitching of status bar colors
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(36.dp)
                .clickable { events.backClicked() },
            painter = painterResource(id = R.drawable.arrow_back),
            contentDescription = null,
            colorFilter = ColorFilter.tint(color = colorResource(id = R.color.white))
        )

        Text(
            text = pokemon.name.replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.headlineMedium.copy(
                color = colorResource(id = R.color.white),
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(16.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            modifier = Modifier
                .padding(8.dp),
            text = "#${idString(pokemon.id)}",
            style = MaterialTheme.typography.titleLarge.copy(
                color = colorResource(id = R.color.white),
                textAlign = TextAlign.Center
            )
        )
    }

}