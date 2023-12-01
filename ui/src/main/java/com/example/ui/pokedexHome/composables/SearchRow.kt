package com.example.ui.pokedexHome.composables

import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ui.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchRow() {
    val TAG = "HomeTopBar"
    var searchText = ""
    var sortMode = SortMode.NUMERICAL
    var textColors = TextFieldDefaults.colors()
    val searchColors = SearchBarDefaults.colors().containerColor.copy(
        red = 0.8f
    )

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
            sortMode = sortMode
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
    sortMode: SortMode
) {
    val TAG = "pokedexSearchBar"
    var testText by remember { mutableStateOf("") }
    val dummyText = "dummy text"

    Box(modifier = modifier) {
        TextField(
            value = testText,
            onValueChange = {
                testText = it
                Log.d(TAG, "key hit $dummyText") },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(90.dp)),
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = colorResource(id = R.color.black),
                textAlign = TextAlign.Left
            ),
            colors = TextFieldDefaults.colors(
                focusedTextColor = colorResource(id = R.color.black),
                focusedContainerColor = colorResource(id = R.color.cream),
                unfocusedContainerColor = colorResource(id = R.color.cream),
                disabledContainerColor = colorResource(id = R.color.cream),
            ),
            leadingIcon = {
                SearchRowIcon(
                    symbol = Symbol.SEARCH,
                    sortMode = sortMode,
                    onClick = { Log.d(TAG, "sort clicked") }
                )
            },
            trailingIcon = {
                if (text.isNotEmpty()) {
                    SearchRowIcon(
                        symbol = Symbol.CLEAR,
                        sortMode = sortMode,
                        onClick = { Log.d(TAG, "clear clicked") }
                    )
                }
            }

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

