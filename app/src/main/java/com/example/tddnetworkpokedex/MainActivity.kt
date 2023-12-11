package com.example.tddnetworkpokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.navigation.AppNavigation
import com.example.tddnetworkpokedex.core.PokedexApp
import com.example.ui.pokedexHome.PokedexHomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        window.statusBarColor = resources.getColor(R.color.red, theme)
        window.navigationBarColor = resources.getColor(R.color.red, theme)

        setContent {
            PokedexApp()
        }
    }
}