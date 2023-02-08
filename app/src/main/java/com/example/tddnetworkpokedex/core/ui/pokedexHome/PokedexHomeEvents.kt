package com.example.tddnetworkpokedex.core.ui.pokedexHome

import com.example.tddnetworkpokedex.database.Pokemon


interface PokedexHomeEvents {
    fun pokemonClicked(pokemon: Pokemon)
}