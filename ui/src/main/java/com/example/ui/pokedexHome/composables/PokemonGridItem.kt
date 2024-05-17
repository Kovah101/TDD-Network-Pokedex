package com.example.ui.pokedexHome.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.database.Pokemon
import com.example.ui.R

@Composable
fun PokemonGridItem(
    pokemon: Pokemon,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.clip(RoundedCornerShape(8.dp)),
    ) {
        Box(modifier = Modifier
            .size(128.dp)
            .background(color = colorResource(id = R.color.cream_4))
            .clickable { onClick() }
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 2.dp,
                color = colorResource(id = R.color.brown),
                shape = RoundedCornerShape(8.dp)
            )
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .fillMaxHeight(fraction = 0.4f)
                    .background(color = colorResource(id = R.color.cream_1))
            )

            PokemonSprite(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize(0.65f),
                pokemon = pokemon
            )

            Text(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp),
                text = "#${idString(pokemon.id)}"
            )

            Text(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(8.dp),
                text = pokemon.name.replaceFirstChar { it.uppercase() }
            )
        }
    }
}

fun idString(id: Int): String {
    return when {
        id < 10 -> "00$id"
        id < 100 -> "0$id"
        else -> "$id"
    }
}