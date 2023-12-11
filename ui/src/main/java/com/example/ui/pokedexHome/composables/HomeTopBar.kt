package com.example.ui.pokedexHome.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ui.R
import com.example.ui.pokedexHome.PokedexHomeEvents
import com.example.ui.pokedexHome.PokedexHomeState

@Composable
fun HomeTopBar(
    state: PokedexHomeState,
    events: PokedexHomeEvents,
) {

    Box(
        modifier = Modifier
            .height(height = dimensionResource(id = R.dimen.HomeTopBarHeight))
            .fillMaxSize()
            .background(color = colorResource(id = R.color.red))
    ) {

        Column {
            SymbolAndTitle()

            SearchRow(
                searchMode = state.searchMode,
                searchText = state.searchText,
                onSearchTextUpdate = { events.updateSearchText(it) },
                onSearchModeSwitch = { events.switchSearchMode() },
                onClearClicked = { events.onClearClicked() }
            )
        }

    }
}