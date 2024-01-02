package com.example.ui.pokedexDetails.composables.pokemonInfo

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.R

@Composable
fun PokemonStats(
    modifier: Modifier,
    stats: List<Int>,
    color: Color
) {
    Column(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Stats",
            style = MaterialTheme.typography.headlineMedium.copy(
                color = color,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ),
            modifier = Modifier.padding(top = 4.dp)
        )

        PokemonStatsAndBars(
            modifier = Modifier,
            stats = stats,
            color = color
        )
    }
}

@Composable
private fun PokemonStatsAndBars(
    modifier: Modifier,
    stats: List<Int>,
    color: Color
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        for ((index, value) in stats.withIndex()) {
            PokemonStatRow(
                modifier = Modifier
                    .fillMaxWidth(),
                stat = value,
                index = index,
                color = color
            )
        }
    }
}

@Composable
private fun PokemonStatRow(
    modifier: Modifier,
    stat: Int,
    index: Int,
    color: Color
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        PokemonStatName(
            modifier = Modifier.padding(end = 4.dp),
            statIndex = index,
            color = color
        )

        Divider(
            modifier = Modifier
                .wrapContentHeight(unbounded = true)
                .height(24.dp)
                .width(2.dp),
            color = colorResource(id = R.color.cream_1)
        )

        PokemonStatNumber(
            modifier = Modifier.padding(start = 2.dp),
            stat = stat,
        )

        PokemonStatBar(
            modifier = Modifier,
            statIndex = index,
            statValue = stat,
            color = color
        )
    }
}

@Composable
private fun PokemonStatName(
    modifier: Modifier,
    statIndex: Int,
    color: Color
) {
    val pokemonStatNames = listOf(
        "HP",
        "Attack",
        "Defense",
        "Sp. Atk",
        "Sp. Def",
        "Speed"
    )

    Text(
        text = pokemonStatNames[statIndex],
        style = MaterialTheme.typography.bodyLarge.copy(
            color = color,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        ),
        maxLines = 1,
        modifier = modifier.width(72.dp)
    )
}

@Composable
private fun PokemonStatNumber(
    modifier: Modifier,
    stat: Int,
) {
    Text(
        text = stat.toString(),
        style = MaterialTheme.typography.bodyLarge.copy(
            textAlign = TextAlign.Start,
            fontSize = 16.sp,
        ),
        maxLines = 1,
        modifier = modifier
            .padding(start = 8.dp, end = 2.dp)
            .width(32.dp)
    )
}

@Composable
private fun PokemonStatBar(
    modifier: Modifier,
    statIndex: Int,
    statValue: Int,
    color: Color
) {
    var width by remember { mutableFloatStateOf(0f) }
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
