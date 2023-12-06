package com.example.ui.pokedexHome

import com.example.database.Pokemon
import com.example.ui.pokedexHome.composables.SearchMode


data class PokedexHomeState(
    val pokemon : List<Pokemon>,
    val loading : Boolean,
    val selectedPokemon : Pokemon,
    val searchText: String,
    val searchMode: SearchMode
){
    companion object{
        val default = PokedexHomeState(
            pokemon = emptyList(),
            loading = false,
            selectedPokemon = Pokemon(),
            searchText = "",
            searchMode = SearchMode.NUMERICAL
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
