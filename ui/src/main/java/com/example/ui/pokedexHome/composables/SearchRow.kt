package com.example.ui.pokedexHome.composables

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
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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

@Composable
fun SearchRow(
    searchMode: SearchMode,
    searchText: String,
    onSearchTextUpdate: (String) -> Unit,
    onSearchModeSwitch: () -> Unit,
    onClearClicked: () -> Unit

) {
    Row(
        modifier = Modifier
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
            .background(color = colorResource(id = R.color.cream_4)),
        onClick = { onClick() }
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
        OutlinedTextField(
            value = text,
            onValueChange = { onSearchTextUpdate(it) },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(90.dp)),
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = colorResource(id = R.color.red),
                textAlign = TextAlign.Left
            ),
            colors = TextFieldDefaults.colors(
                focusedTextColor = colorResource(id = R.color.cream_4),
                focusedContainerColor = colorResource(id = R.color.cream_4),
                unfocusedContainerColor = colorResource(id = R.color.cream_4),
                disabledContainerColor = colorResource(id = R.color.cream_4),
                focusedIndicatorColor = colorResource(id = R.color.cream_4),
                unfocusedIndicatorColor = colorResource(id = R.color.cream_4),
                disabledIndicatorColor = colorResource(id = R.color.cream_4),
                cursorColor = colorResource(id = R.color.red),
            ),
            leadingIcon = {
                SearchRowIcon(
                    symbol = Symbol.SEARCH,
                    sortMode = sortMode,
                    onClick = { }
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

