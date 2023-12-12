package com.example.ui.pokedexDetails.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.ui.pokedexDetails.PokedexDetailsEvents
import com.example.ui.pokedexHome.composables.PokemonSprite

@Composable
fun PokemonImage(
    modifier: Modifier,
    events: PokedexDetailsEvents,
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

            ChangePokemonButton(
                pokemonOrder = PokemonOrder.PREVIOUS,
                pokemonId = pokemon.id,
                onButtonClicked = { events.previousClicked() }
            )

            PokemonSprite(
                modifier = Modifier,
                pokemon = pokemon,
                size = 280,
                usePlaceholder = false
            )

            ChangePokemonButton(
                pokemonOrder = PokemonOrder.NEXT,
                pokemonId = pokemon.id,
                onButtonClicked = { events.nextClicked() }
            )

        }
        
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun ChangePokemonButton(
    pokemonOrder: PokemonOrder,
    pokemonId : Int,
    onButtonClicked: () -> Unit
) {
    if (pokemonId != pokemonOrder.limitId) {
        Image(
            modifier = Modifier
                .size(36.dp)
                .clickable { onButtonClicked() },
            painter = painterResource(id = if (pokemonOrder == PokemonOrder.PREVIOUS) R.drawable.chevron_left else R.drawable.chevron_right),
            contentDescription = null,
            colorFilter = ColorFilter.tint(color = colorResource(id = R.color.white))
        )
    } else {
        Spacer(modifier = Modifier.size(36.dp))
    }
}

enum class PokemonOrder(val limitId: Int){
    PREVIOUS(limitId = 1),
    NEXT(limitId = 151);
}

