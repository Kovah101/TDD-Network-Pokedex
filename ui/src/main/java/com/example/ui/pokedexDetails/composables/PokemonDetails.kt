package com.example.ui.pokedexDetails.composables

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.database.Pokemon
import com.example.database.PokemonType
import com.example.ui.R
import com.example.ui.pokedexDetails.PokedexDetailsEvents

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetails(
    pokemon: Pokemon,
    events: PokedexDetailsEvents
) {
    Log.d("PokemonDetails", "${pokemon.name} is from the ${pokemon.region} region")

    val backgroundColors = pokemonTypeColors(pokemon.types)
    val backgroundBrush = if (backgroundColors.size == 1) {
        SolidColor(backgroundColors.first())
    } else {
        Brush.horizontalGradient(backgroundColors)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = backgroundBrush)
    ) {
        PokeballBackground(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        )

        PokemonInfo(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(8.dp),
            pokemon = pokemon
        )

        PokemonImage(
            events = events,
            pokemon = pokemon
        )

        PokemonTopBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .background(color = colorResource(id = R.color.red)),
            pokemon = pokemon,
            events = events
        )

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
private fun pokemonTypeColors(types: List<PokemonType>): List<Color> {
    return types.map { colorResource(id = pokemonTypeToColor(it)) }
}

@Composable
fun pokemonTypeToColor(type: PokemonType?): Int {
    return when (type) {
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