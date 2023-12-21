package com.example.ui.pokedexDetails.composables.pokemonInfo.pokemonStats

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@Composable
fun PokemonStatBars(
    modifier: Modifier,
    stats: List<Int>,
    color: Color
) {
    Column(
        modifier = modifier.height(136.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        for (stat in stats) {
            PokemonStatBar(
                modifier = Modifier.padding(vertical = 4.dp),
                statIndex = stats.indexOf(stat),
                statValue = stat,
                color = color
            )
        }
    }
}

@Composable
private fun PokemonStatBar(
    modifier: Modifier,
    statIndex: Int,
    statValue: Int,
    color: Color
) {

    var visible by remember { mutableStateOf(true) }

    var width by remember { mutableStateOf(0f) }
    val widthStateAnimate by animateFloatAsState(
        targetValue =  width,
        animationSpec = tween(durationMillis = 500, delayMillis = 200, easing = LinearEasing),
        label = ""
    )

    LaunchedEffect(key1 = statValue){
        width =  fractionOfMaxValue(statIndex = statIndex, statValue = statValue)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(8.dp)
            .padding(start = 2.dp)
            .background(
                color = color.copy(alpha = 0.3f),
                shape = RoundedCornerShape(60.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = color,
                    shape = RoundedCornerShape(60.dp)
                )
                .fillMaxWidth(widthStateAnimate)
                .height(8.dp)
        )
    }
}


private fun fractionOfMaxValue(statIndex: Int, statValue: Int): Float {

    val maxStats = listOf(
        255f, // HP
        135f, // Attack
        235f, // Defense
        155f, // Special Attack
        235f, // Special Defense
        145f, // Speed
    )

    return (statValue / maxStats[statIndex])
}