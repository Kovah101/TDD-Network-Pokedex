package com.example.ui.pokedexHome

import com.example.database.Pokemon


interface PokedexHomeEvents {
    fun pokemonClicked(pokemon: com.example.database.Pokemon)
}