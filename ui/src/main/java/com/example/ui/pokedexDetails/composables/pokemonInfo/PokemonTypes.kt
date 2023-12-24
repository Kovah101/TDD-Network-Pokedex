package com.example.ui.pokedexDetails.composables.pokemonInfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.database.PokemonType
import com.example.ui.R
import com.example.ui.pokedexDetails.composables.pokemonTypeToColor

@Composable
fun PokemonTypes(
    modifier: Modifier,
    types: List<PokemonType>
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        items(types.size) { index ->
            PokemonTypeItem(
                modifier = Modifier.padding(horizontal = 8.dp),
                type = types[index]
            )
        }
    }
}

@Composable
fun PokemonTypeItem(
    modifier: Modifier,
    type: PokemonType
) {


    Box(modifier = modifier
        .wrapContentHeight()
        .width(100.dp)
        .background(
            color = colorResource(id = pokemonTypeToColor(type = type)),
            shape = RoundedCornerShape(30.dp)
        )
    ){
        Text(
            text = type.toString().lowercase().replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.bodyLarge.copy(
                color = colorResource(id = R.color.white),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            ),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(vertical = 4.dp, horizontal = 8.dp)
        )
    }
}