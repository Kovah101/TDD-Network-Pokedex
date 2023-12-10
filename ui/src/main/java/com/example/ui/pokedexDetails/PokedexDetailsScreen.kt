package com.example.ui.pokedexDetails

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ui.R

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PokedexDetailsScreen(
     state: PokedexDetailsState,
     events: PokedexDetailsEvents
) {

    BackHandler( enabled = true, onBack ={ events.backClicked()} )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Pokedex",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = colorResource(id = R.color.cream),
                            textAlign = TextAlign.Center
                        )
                    )
                },
              //  colors = colorResource(id = R.color.red),
                modifier = Modifier.fillMaxWidth(),
              //  elevation = 4.dp,
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
                        modifier = Modifier
                            .clickable { events.backClicked() }
                            .padding(horizontal = 16.dp),
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "back to home",
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
            Column (modifier = Modifier.align(Alignment.Center)){

                Text(
                    text = state.pokemon?.name.toString(),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = colorResource(id = R.color.red),
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.padding(16.dp)
                )

                Text(
                    text = state.pokemon?.types.toString(),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = colorResource(id = R.color.red),
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.padding(16.dp)
                )
            }

        }

    }

}