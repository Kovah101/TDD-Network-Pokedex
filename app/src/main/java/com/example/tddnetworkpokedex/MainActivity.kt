package com.example.tddnetworkpokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import com.example.ui.pokedexHome.PokedexHomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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