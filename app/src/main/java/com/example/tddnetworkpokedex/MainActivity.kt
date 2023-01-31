package com.example.tddnetworkpokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tddnetworkpokedex.core.ui.pokedexHome.PokedexHomeScreen
import com.example.tddnetworkpokedex.core.ui.pokedexHome.PokedexHomeViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        window.navigationBarColor = resources.getColor(R.color.red)

        setContent {
            PokedexHomeScreen()
        }
    }
}