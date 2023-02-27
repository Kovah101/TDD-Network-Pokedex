package com.example.ui.pokedexHome

import com.example.database.Pokemon


data class PokedexHomeState(
    val pokemon : List<com.example.database.Pokemon>,
    val loading : Boolean
){
    companion object{
        val default = PokedexHomeState(
            pokemon = emptyList(),
            loading = false
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
