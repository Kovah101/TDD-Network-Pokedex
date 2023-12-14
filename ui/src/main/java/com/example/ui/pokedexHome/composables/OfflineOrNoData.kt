package com.example.ui.pokedexHome.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.data.network.connection.ConnectionState
import com.example.ui.R
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun OfflineOrNoData(){

    val connection by connectivityState()
    val isConnected = connection === ConnectionState.CONNECTED


    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = colorResource(id = R.color.cream))
    ) {

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (isConnected) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(16.dp)
                        .size(128.dp),
                    color = colorResource(id = R.color.red),
                    trackColor = colorResource(id = R.color.dark_red),
                )

                Text(
                    text = "Loading Pokemon Data",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium
                )
            } else {
                Icon(
                    modifier = Modifier.size(128.dp),
                    imageVector = Icons.Outlined.CloudOff,
                    contentDescription = "Offline"
                )

                Text(
                    text = "No data available",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}