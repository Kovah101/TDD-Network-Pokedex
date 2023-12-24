package com.example.ui.pokedexHome.composables

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ui.R

@Composable
fun SymbolAndTitle() {

    Row(
        modifier = Modifier.padding(start = 16.dp, top = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        ){
        Image(
            modifier = Modifier.size(36.dp),
            colorFilter = ColorFilter.tint(colorResource(id = R.color.cream_4)),
            painter = painterResource(R.drawable.pokeball_filled),
            contentDescription = "Pokeball Symbol"
        )

        Text(
            modifier = Modifier
                .padding(start = 16.dp)
                .wrapContentSize(align = Alignment.CenterStart),
            text = "Pokédex",
            style = MaterialTheme.typography.headlineMedium.copy(
                color = colorResource(id = R.color.cream_4),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        )

    }
}
