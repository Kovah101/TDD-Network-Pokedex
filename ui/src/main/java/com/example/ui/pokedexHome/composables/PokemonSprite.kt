package com.example.ui.pokedexHome.composables

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.database.Pokemon
import com.example.ui.R

@Composable
fun PokemonSprite(
    modifier: Modifier,
    pokemon: Pokemon,
    usePlaceholder: Boolean = true
) {
    var visible by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = pokemon){
        visible = true
    }

    if (pokemon.sprite.isEmpty()) {
        Image(
            modifier = modifier,
            painter = painterResource(id = R.drawable.ditto),
            contentDescription = null
        )
    } else {
            AnimatedContent(
                modifier = modifier,
                targetState = pokemon,
                label = "",
                transitionSpec = {
                if (targetState.id > initialState.id){
                    slideInHorizontally { width -> width } + fadeIn() togetherWith
                            slideOutHorizontally { width -> -width } + fadeOut()
                } else {
                    slideInHorizontally { width -> -width } + fadeIn() togetherWith
                            slideOutHorizontally { width -> width } + fadeOut()
                }.using(
                    SizeTransform(clip = false)
                )
                }
            ) { targetState ->


                AsyncImage(
                    modifier = modifier,
                    model = targetState.sprite,
                    contentDescription = null,
                    placeholder = if (usePlaceholder) painterResource(id = R.drawable.ditto) else null
                )
            }
        }
}