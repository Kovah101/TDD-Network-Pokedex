package com.example.ui.pokedexDetails.composables

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.database.Pokemon
import com.example.ui.R
import com.example.ui.pokedexHome.composables.PokemonSprite

@Composable
fun PokemonImage(
    modifier: Modifier,
    pokemon: Pokemon
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.weight(0.20f))

        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier
                    .size(36.dp)
                    .clickable { Log.d("PokemonImage", "previous clicked") },
                painter = painterResource(id = R.drawable.chevron_left),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = colorResource(id = R.color.white))
            )

            PokemonSprite(
                modifier = Modifier,
                pokemon = pokemon,
                size = 280
            )

            Image(
                modifier = Modifier
                    .size(36.dp)
                    .clickable { Log.d("PokemonImage", "next clicked") },
                painter = painterResource(id = R.drawable.chevron_right),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = colorResource(id = R.color.white))
            )


        }
        
        Spacer(modifier = Modifier.weight(1f))
    }
}
