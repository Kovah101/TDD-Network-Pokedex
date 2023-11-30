package com.example.ui.pokedexHome.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.database.Pokemon
import com.example.ui.R

@Composable
fun PokemonGridItem(
    pokemon: Pokemon,
    onClick: () -> Unit
){
    Box(modifier = Modifier
        .size(128.dp)
        .background(color = colorResource(id = R.color.cream))
        .border(BorderStroke(width = 2.dp, color = colorResource(id = R.color.brown)))
        .clickable { onClick() }
    ){
        Text(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(8.dp),
            text = pokemon.id.toString())

        Text(
            modifier = Modifier.align(Alignment.Center),
            text = pokemon.name)

    }
}