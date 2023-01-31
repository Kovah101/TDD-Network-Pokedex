package com.example.tddnetworkpokedex.core.ui.pokedexHome

import com.example.tddnetworkpokedex.core.models.Pokemon

data class PokedexHomeState(
    val pokemon : List<Pokemon>,
    val loading : Boolean
){
    companion object{
        val default = PokedexHomeState(
            pokemon = emptyList(),
            loading = false
        )
    }
}
