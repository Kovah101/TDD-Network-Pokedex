package com.example.ui.pokedexHome.composables

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ui.R
import com.example.ui.pokedexHome.PokedexHomeEvents
import com.example.ui.pokedexHome.PokedexHomeState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchRow(
    searchMode: SearchMode,
    searchText: String,
    onSearchTextUpdate: (String) -> Unit,
    onSearchModeSwitch: () -> Unit,
    onClearClicked: () -> Unit

) {
    val TAG = "HomeTopBar"

    Row(
        modifier = Modifier
            //  .height(80.dp)
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PokedexSearchBar(
            text = searchText,
            modifier = Modifier.weight(1f),
            sortMode = searchMode,
            onSearchTextUpdate = { onSearchTextUpdate(it) },
            onClearClicked = { onClearClicked() }
        )

        Spacer(modifier = Modifier.width(16.dp))

        SearchRowIcon(
            symbol = Symbol.SORT,
            sortMode = searchMode,
            onClick = { onSearchModeSwitch() }
        )
    }
}

@Composable
private fun SearchRowIcon(
    symbol: Symbol,
    sortMode: SearchMode,
    onClick: () -> Unit
) {
    val imageVector = when (symbol) {
        Symbol.SEARCH -> ImageVector.vectorResource(id = R.drawable.search)
        Symbol.CLEAR -> ImageVector.vectorResource(id = R.drawable.close)
        Symbol.SORT -> if (sortMode == SearchMode.NUMERICAL) {
            ImageVector.vectorResource(id = R.drawable.tag)
        } else {
            ImageVector.vectorResource(id = R.drawable.text_format)
        }
    }

    IconButton(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(color = colorResource(id = R.color.cream)),
        onClick = {
            onClick()
            Log.d("HomeTopBar", "${symbol.name} icon clicked")
        }
    ) {
        Image(
            modifier = Modifier.padding(8.dp),
            imageVector = imageVector,
            contentDescription = "${symbol.name} Icon",
            colorFilter = ColorFilter.tint(colorResource(id = R.color.red))
        )
    }

}

@Composable
fun PokedexSearchBar(
    text: String,
    modifier: Modifier,
    sortMode: SearchMode,
    onSearchTextUpdate : (String) -> Unit = {},
    onClearClicked: () -> Unit = {}
) {
    val TAG = "pokedexSearchBar"

    Box(modifier = modifier) {
        TextField(
            value = text,
            onValueChange = { onSearchTextUpdate(it) },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(90.dp)),
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = colorResource(id = R.color.black),
                textAlign = TextAlign.Left
            ),
            colors = TextFieldDefaults.colors(
                focusedTextColor = colorResource(id = R.color.cream),
                focusedContainerColor = colorResource(id = R.color.cream),
                unfocusedContainerColor = colorResource(id = R.color.cream),
                disabledContainerColor = colorResource(id = R.color.cream),
                focusedIndicatorColor = colorResource(id = R.color.cream),
                unfocusedIndicatorColor = colorResource(id = R.color.cream),
                disabledIndicatorColor = colorResource(id = R.color.cream),
            ),
            leadingIcon = {
                SearchRowIcon(
                    symbol = Symbol.SEARCH,
                    sortMode = sortMode,
                    onClick = { Log.d(TAG, "search clicked") }
                )
            },
            trailingIcon = {
                if (text.isNotEmpty()) {
                    SearchRowIcon(
                        symbol = Symbol.CLEAR,
                        sortMode = sortMode,
                        onClick = { onClearClicked() }
                    )
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = if (sortMode == SearchMode.NUMERICAL) {
                    KeyboardType.Number
                } else {
                    KeyboardType.Text
                }
            )

        )
    }

}

enum class Symbol {
    SEARCH,
    CLEAR,
    SORT
}

enum class SearchMode {
    NUMERICAL,
    NAME
}

