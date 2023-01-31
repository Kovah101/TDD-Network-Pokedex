package com.example.tddnetworkpokedex.core.ui.pokedexHome

import com.example.tddnetworkpokedex.core.models.Pokemon

interface PokedexHomeEvents {
    fun pokemonClicked(pokemon: Pokemon)
}