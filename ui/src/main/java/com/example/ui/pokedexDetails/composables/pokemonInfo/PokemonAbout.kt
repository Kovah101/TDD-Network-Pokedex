package com.example.ui.pokedexDetails.composables.pokemonInfo

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.R

@Composable
fun PokemonAbout(
    modifier: Modifier,
    height: Double,
    weight: Double,
    color : Color
) {
    Column(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "About",
            style = MaterialTheme.typography.headlineMedium.copy(
                color = color,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            ),
            modifier = Modifier.padding(top = 8.dp)
        )

        PokemonHeightAndWeight(
            modifier = Modifier,
            height = height,
            weight = weight
        )
    }
}

@Composable
private fun PokemonHeightAndWeight(
    modifier: Modifier,
    height: Double,
    weight: Double
) {
    Row (
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){

        PokemonAttribute(
            modifier = Modifier,
            attribute = height,
            type = "Height",
            unit = "m",
            icon = R.drawable.height
        )

        Divider(
            modifier = Modifier
                .height(64.dp)
                .width(2.dp),
            color = colorResource(id = R.color.cream_1)
        )

        PokemonAttribute(
            modifier = Modifier,
            attribute = weight,
            type = "Weight",
            unit = "kg",
            icon = R.drawable.weight
        )
    }
}

@Composable
private fun PokemonAttribute(
    modifier: Modifier,
    attribute: Double,
    type: String,
    unit: String,
    icon: Int
) {
    Column(
        modifier = modifier
            .wrapContentSize()
            .padding(horizontal = 8.dp)
    ) {

        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center

        ) {
            Icon(
                modifier = Modifier
                    .padding(end = 4.dp)
                    .size(24.dp),
                imageVector = ImageVector.vectorResource(id = icon),
                contentDescription = type
            )

            AnimatedContent(
                targetState = attribute,
                label = "",
                transitionSpec = {
                    if (targetState > initialState) {
                         slideInVertically{ height -> height } + fadeIn()togetherWith
                                 slideOutVertically{ height -> -height }+ fadeOut()
                    } else {
                        slideInVertically{ height -> -height } + fadeIn() togetherWith
                                slideOutVertically{ height -> height } + fadeOut()
                    }.using(
                        SizeTransform(clip = false)
                    )
                }
            ) { target ->
                Text(
                    modifier = Modifier.width(48.dp),
                    text = "$target",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = colorResource(id = R.color.black),
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp
                    ),
                )

            }
            Text(
                text = unit,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = colorResource(id = R.color.black),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp
                ),
            modifier = Modifier.padding(end = 8.dp)
            )
        }
        Text(
            text = type,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = colorResource(id = R.color.black),
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            ),
            modifier = Modifier.padding(top = 4.dp, start = 16.dp)
        )
    }
}