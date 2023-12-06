package com.example.ui.pokedexHome

import com.example.database.Pokemon


interface PokedexHomeEvents {
    fun pokemonClicked(pokemon: Pokemon)
    fun updateSearchText(text: String)
    fun switchSearchMode()
    fun onClearClicked()
}