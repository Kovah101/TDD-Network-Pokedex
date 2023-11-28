package com.example.ui.pokedexHome

import com.example.database.Pokemon


data class PokedexHomeState(
    val pokemon : List<Pokemon>,
    val loading : Boolean,
    val selectedPokemon : Pokemon
){
    companion object{
        val default = PokedexHomeState(
            pokemon = emptyList(),
            loading = false,
            selectedPokemon = Pokemon()
        )
    }
}

//listOf(
//Pokemon( id = 3, name = "Steve", url = "something"),
//Pokemon( id = 3, name = "Steve", url = "something"),
//Pokemon( id = 3, name = "Steve", url = "something"),
//Pokemon( id = 3, name = "Steve", url = "something"),
//Pokemon( id = 3, name = "Steve", url = "something"),
//Pokemon( id = 3, name = "Steve", url = "something"),
//Pokemon( id = 3, name = "Steve", url = "something"),
//Pokemon( id = 3, name = "Steve", url = "something"),
//Pokemon( id = 3, name = "Steve", url = "something"),
//Pokemon( id = 3, name = "Steve", url = "something"),
//Pokemon( id = 3, name = "Steve", url = "something"),
//Pokemon( id = 3, name = "Steve", url = "something"),
//Pokemon( id = 3, name = "Steve", url = "something"),
//Pokemon( id = 3, name = "Steve", url = "something"),
//Pokemon( id = 3, name = "Steve", url = "something"),
//Pokemon( id = 3, name = "Steve", url = "something"),
//Pokemon( id = 3, name = "Steve", url = "something"),
//Pokemon( id = 3, name = "Steve", url = "something"),
//Pokemon( id = 3, name = "Steve", url = "something"),
//Pokemon( id = 3, name = "Steve", url = "something"),
//Pokemon( id = 3, name = "Steve", url = "something"),
//Pokemon( id = 3, name = "Steve", url = "something"),
//Pokemon( id = 3, name = "Steve", url = "something"),
//Pokemon( id = 3, name = "Steve", url = "something"),
//Pokemon( id = 3, name = "Steve", url = "something"),
//)
