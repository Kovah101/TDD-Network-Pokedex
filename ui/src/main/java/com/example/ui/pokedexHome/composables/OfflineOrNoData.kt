package com.example.ui.pokedexHome.composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.data.network.connection.ConnectionState
import com.example.ui.R
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalMaterialApi::class)
@Composable
fun OfflineOrNoData(
    pullToRefresh: () -> Unit = {}
) {

    val connection by connectivityState()
    val isConnected = connection === ConnectionState.CONNECTED
    var refreshing by remember { mutableStateOf(false) }
    val refreshState = rememberPullRefreshState(
        refreshing = refreshing,
        //refreshThreshold = 60.dp,
        onRefresh = {
            refreshing = true
            Log.d("PullToRefresh", "Refreshing")
            pullToRefresh()
            refreshing = false
        })



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.cream))
          //  .pullRefresh(state = refreshState)
    ) {

        Column(
            modifier = Modifier
                .align(Alignment.Center),
               // .pullRefresh(state = refreshState),
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