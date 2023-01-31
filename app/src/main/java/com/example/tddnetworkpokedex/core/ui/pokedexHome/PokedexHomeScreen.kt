package com.example.tddnetworkpokedex.core.ui.pokedexHome

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tddnetworkpokedex.R

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
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Pokemon coming soon....",
                style = MaterialTheme.typography.subtitle1
            )
        }

    }
}