package com.example.tddnetworkpokedex

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.tddnetworkpokedex.core.PokedexApp
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