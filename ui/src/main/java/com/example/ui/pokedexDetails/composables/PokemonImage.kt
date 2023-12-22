package com.example.ui.pokedexDetails.composables

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.database.Pokemon
import com.example.ui.R
import com.example.ui.pokedexDetails.PokedexDetailsEvents
import com.example.ui.pokedexHome.composables.PokemonSprite
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalMaterial3Api
@Composable
fun PokemonImage(
    events: PokedexDetailsEvents,
    pokemon: Pokemon
) {
    val swipeableState = rememberSwipeableState(initialValue = PokemonOrder.CURRENT)

    when (swipeableState.currentValue) {
        PokemonOrder.CURRENT -> {
            Log.d("PokemonImage", "PokemonOrder.CURRENT")
        }

        PokemonOrder.PREVIOUS -> {
            Log.d("PokemonImage", "PokemonOrder.PREVIOUS")
            LaunchedEffect(key1 = Unit) {
                CoroutineScope(Dispatchers.Default).launch {
                    swipeableState.snapTo(PokemonOrder.CURRENT)
                    if (pokemon.id != PokemonOrder.PREVIOUS.limitId) {
                        events.previousClicked()
                    }
                }
            }
        }

        PokemonOrder.NEXT -> {
            Log.d("PokemonImage", "PokemonOrder.NEXT")
            LaunchedEffect(key1 = Unit) {
                CoroutineScope(Dispatchers.Default).launch {
                    swipeableState.snapTo(PokemonOrder.CURRENT)
                    if (pokemon.id != PokemonOrder.NEXT.limitId) {
                        events.nextClicked()
                    }
                }
            }
        }
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .swipeable(
                state = swipeableState,
                anchors = mapOf(
                    0f to PokemonOrder.CURRENT,
                    -1f to PokemonOrder.NEXT,
                    1f to PokemonOrder.PREVIOUS
                ),
                orientation = Orientation.Horizontal,
                thresholds = { _, _ -> FractionalThreshold(0.3f) },
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        ChangePokemonButtonColumn(
            pokemonOrder = PokemonOrder.PREVIOUS,
            pokemonId = pokemon.id,
            onButtonClicked = { events.previousClicked() }
        )

        PokemonSpriteColumn(
            pokemon = pokemon,
        )

        ChangePokemonButtonColumn(
            pokemonOrder = PokemonOrder.NEXT,
            pokemonId = pokemon.id,
            onButtonClicked = { events.nextClicked() }
        )
    }
}

@Composable
private fun ChangePokemonButtonColumn(
    pokemonOrder: PokemonOrder,
    pokemonId: Int,
    onButtonClicked: () -> Unit
) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.4f))

        ChangePokemonButton(
            pokemonOrder = pokemonOrder,
            pokemonId = pokemonId,
            onButtonClicked = { onButtonClicked() }
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}


@Composable
private fun ChangePokemonButton(
    pokemonOrder: PokemonOrder,
    pokemonId: Int,
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

@Composable
private fun PokemonSpriteColumn(
    pokemon: Pokemon,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.2f))

        PokemonSprite(
            modifier = Modifier.fillMaxWidth(0.7f),
            pokemon = pokemon,
            usePlaceholder = false
        )

        Spacer(modifier = Modifier.weight(1f))

    }
}

enum class PokemonOrder(val limitId: Int) {
    CURRENT(limitId = 0),
    PREVIOUS(limitId = 1),
    NEXT(limitId = 151);
}

