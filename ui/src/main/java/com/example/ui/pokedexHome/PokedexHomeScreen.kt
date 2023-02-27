package com.example.ui.pokedexHome

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.R

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PokedexHomeScreen(
    pokedexHomeViewModel: PokedexHomeViewModel = viewModel()
) {

    val state = pokedexHomeViewModel.state.collectAsState().value


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Pokedex",
                        style = MaterialTheme.typography.h5.copy(
                            color = colorResource(id = R.color.cream),
                            textAlign = TextAlign.Center
                        )
                    )
                },
                backgroundColor = colorResource(id = R.color.red),
                modifier = Modifier.fillMaxWidth(),
                elevation = 4.dp,
                navigationIcon = {
                    Icon(
                        modifier = Modifier.padding(start = 16.dp),
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Add nav drawer in future",
                        tint = colorResource(id = R.color.cream)
                    )
                },
                actions = {
                    Icon(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        imageVector = Icons.Default.Search,
                        contentDescription = "Add search bottom sheet in future",
                        tint = colorResource(id = R.color.cream)
                    )
                }
            )
        }
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.cream))
        ) {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Adaptive(minSize = 128.dp)
            ){
                items(state.pokemon){ pokemon ->
                    PokemonGridItem(
                        pokemon = pokemon,
                        onClick = {pokedexHomeViewModel.pokemonClicked(pokemon = pokemon)}
                    )
                }
            }
        }

    }
}

@Composable
fun PokemonGridItem(
    pokemon: com.example.database.Pokemon,
    onClick: () -> Unit
){
    Box(modifier = Modifier
        .size(128.dp)
        .background(color = colorResource(id = R.color.cream))
        .border(BorderStroke(width = 2.dp, color = colorResource(id = R.color.brown)))
        .clickable { onClick() }
    ){
        Text(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(8.dp),
            text = pokemon.id.toString())

        Text(
            modifier = Modifier.align(Alignment.Center),
            text = pokemon.name)
        
    }
}