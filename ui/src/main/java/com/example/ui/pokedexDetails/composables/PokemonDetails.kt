package com.example.ui.pokedexDetails.composables

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.example.database.Pokemon
import com.example.database.PokemonType
import com.example.ui.R
import com.example.ui.pokedexDetails.PokedexDetailsEvents

@Composable
fun PokemonDetails(
    pokemon: Pokemon,
    events: PokedexDetailsEvents
) {
    // TODO add sprite and basic card info,
    //  add swipe and click to next pokemon

    val activity = LocalView.current.context as Activity
    activity.window.statusBarColor = colorResource(id = pokemonTypeToColor(pokemon)).toArgb()
    activity.window.navigationBarColor = colorResource(id = pokemonTypeToColor(pokemon)).toArgb()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = pokemonTypeToColor(pokemon)))
    ) {
        PokeballBackground(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        )

        PokemonTopBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(16.dp),
            pokemon = pokemon,
            events = events
        )

        Column(modifier = Modifier.align(Alignment.Center)) {

            Text(
                text = pokemon.types.toString(),
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = colorResource(id = R.color.white),
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(16.dp)
            )
        }

    }
}

@Composable
fun PokeballBackground(
    modifier: Modifier
) {
    Image(
        modifier = modifier.size(208.dp),
        painter = painterResource(id = R.drawable.pokeball),
        contentDescription = null
    )
}

@Composable
fun pokemonTypeToColor(pokemon: Pokemon): Int {
    return when (pokemon.types.firstOrNull()) {
        PokemonType.NORMAL -> R.color.normal
        PokemonType.FIGHTING -> R.color.fighting
        PokemonType.FLYING -> R.color.flying
        PokemonType.POISON -> R.color.poison
        PokemonType.GROUND -> R.color.ground
        PokemonType.ROCK -> R.color.rock
        PokemonType.BUG -> R.color.bug
        PokemonType.GHOST -> R.color.ghost
        PokemonType.STEEL -> R.color.steel
        PokemonType.FIRE -> R.color.fire
        PokemonType.WATER -> R.color.water
        PokemonType.GRASS -> R.color.grass
        PokemonType.ELECTRIC -> R.color.electric
        PokemonType.PSYCHIC -> R.color.psychic
        PokemonType.ICE -> R.color.ice
        PokemonType.DRAGON -> R.color.dragon
        PokemonType.DARK -> R.color.dark
        PokemonType.FAIRY -> R.color.fairy
        PokemonType.UNKNOWN -> R.color.black
        else -> R.color.cream
    }
}