package com.example.ui.pokedexHome.composables

import android.util.Log
import android.widget.ImageButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.ui.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchRow() {
    val TAG = "HomeTopBar"
    var searchText = ""
    var sortMode = SortMode.NUMERICAL

    Row {
        SearchBar(
            query = searchText,
            onQueryChange = {
                searchText = it
                Log.d(TAG, "searchText changed to: $searchText")
            },
            onSearch = { Log.d(TAG, "search entered") },
            active = true,
            onActiveChange = { Log.d(TAG, "search active changed") },
            placeholder = { Text("Search") },
            leadingIcon = {
                SearchRowIcon(
                    symbol = Symbol.SEARCH,
                    sortMode = sortMode,
                    onClick = { Log.d(TAG, "sort clicked") }
                )
            },
            trailingIcon = {
                if (searchText.isNotEmpty()) {
                    SearchRowIcon(
                        symbol = Symbol.CLEAR,
                        sortMode = sortMode,
                        onClick = { Log.d(TAG, "clear clicked") }
                    )
                }
            },
            content = {
                Log.d(TAG, "search content")
            }
        )

        Spacer(modifier = Modifier.width(16.dp))

        SearchRowIcon(
            symbol = Symbol.SORT,
            sortMode = SortMode.NUMERICAL,
            onClick = { Log.d(TAG, "sort clicked") }
        )
    }
}

@Composable
private fun SearchRowIcon(
    symbol: Symbol,
    sortMode: SortMode,
    onClick: () -> Unit
) {
    val imageVector = when (symbol) {
        Symbol.SEARCH -> ImageVector.vectorResource(id = R.drawable.search)
        Symbol.CLEAR -> ImageVector.vectorResource(id = R.drawable.close)
        Symbol.SORT -> if (sortMode == SortMode.NUMERICAL) {
            ImageVector.vectorResource(id = R.drawable.tag)
        } else {
            ImageVector.vectorResource(id = R.drawable.text_format)
        }
    }

    IconButton(
        onClick = {
            onClick()
            Log.d("HomeTopBar", "${symbol.name} icon clicked")
        }
    ) {
        Image(
            imageVector = imageVector,
            contentDescription = "${symbol.name} Icon",
            colorFilter = ColorFilter.tint(colorResource(id = R.color.cream))
        )
    }

}

enum class Symbol {
    SEARCH,
    CLEAR,
    SORT
}

enum class SortMode {
    NUMERICAL,
    NAME
}

