package com.example.ui.pokedexHome.composables

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
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

    if (pokemon.sprite.isEmpty()) {
        Image(
            modifier = modifier,
            painter = painterResource(id = R.drawable.ditto),
            contentDescription = null
        )
    } else {
        Log.d("PokemonSprite", "PokemonSprite: ${pokemon.sprite}")

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
                    placeholder = if (usePlaceholder) painterResource(id = R.drawable.ditto) else null,
                    fallback = if (usePlaceholder) painterResource(id = R.drawable.ditto) else null,
                    error = if (usePlaceholder) painterResource(id = R.drawable.ditto) else null
                )
            }
        }
}