package com.example.ui.pokedexHome.composables

import android.util.Log
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

@Composable
fun HomeTopBar() {

    Box(
        modifier = Modifier
            .height(height = dimensionResource(id = R.dimen.HomeTopBarHeight))
            .fillMaxSize()
            .background(color = colorResource(id = R.color.red))
    ) {

        Column {
            SymbolAndTitle()

            SearchRow()
        }

    }
}