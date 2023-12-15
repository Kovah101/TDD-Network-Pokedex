package com.example.ui.pokedexHome.composables

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.database.Pokemon
import com.example.ui.R

@Composable
fun PokemonSprite(
    modifier: Modifier,
    pokemon: Pokemon,
    usePlaceholder : Boolean = true
) {
    if (pokemon.sprite.isEmpty()) {
        Image(
            modifier = modifier,
            painter = painterResource(id = R.drawable.ditto),
            contentDescription = null
        )
    } else {
        AsyncImage(
            modifier = modifier,
            model = pokemon.sprite,
            contentDescription = null,
            placeholder = if (usePlaceholder) painterResource(id = R.drawable.ditto) else null
        )
    }
}