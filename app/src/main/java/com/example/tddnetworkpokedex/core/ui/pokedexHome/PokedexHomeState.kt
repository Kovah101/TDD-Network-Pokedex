package com.example.tddnetworkpokedex.core.ui.pokedexHome

import com.example.tddnetworkpokedex.core.models.Pokemon

data class PokedexHomeState(
    val pokemon : List<Pokemon>,
    val loading : Boolean
){
    companion object{
        val default = PokedexHomeState(
            pokemon = listOf(
                Pokemon( id = 3, name = "Steve", url = "something"),
                Pokemon( id = 3, name = "Steve", url = "something"),
                Pokemon( id = 3, name = "Steve", url = "something"),
                Pokemon( id = 3, name = "Steve", url = "something"),
                Pokemon( id = 3, name = "Steve", url = "something"),
                Pokemon( id = 3, name = "Steve", url = "something"),
                Pokemon( id = 3, name = "Steve", url = "something"),
                Pokemon( id = 3, name = "Steve", url = "something"),
                Pokemon( id = 3, name = "Steve", url = "something"),
                Pokemon( id = 3, name = "Steve", url = "something"),
                Pokemon( id = 3, name = "Steve", url = "something"),
                Pokemon( id = 3, name = "Steve", url = "something"),
                Pokemon( id = 3, name = "Steve", url = "something"),
                Pokemon( id = 3, name = "Steve", url = "something"),
                Pokemon( id = 3, name = "Steve", url = "something"),
                Pokemon( id = 3, name = "Steve", url = "something"),
                Pokemon( id = 3, name = "Steve", url = "something"),
                Pokemon( id = 3, name = "Steve", url = "something"),
                Pokemon( id = 3, name = "Steve", url = "something"),
                Pokemon( id = 3, name = "Steve", url = "something"),
                Pokemon( id = 3, name = "Steve", url = "something"),
                Pokemon( id = 3, name = "Steve", url = "something"),
                Pokemon( id = 3, name = "Steve", url = "something"),
                Pokemon( id = 3, name = "Steve", url = "something"),
                Pokemon( id = 3, name = "Steve", url = "something"),


            ),
            loading = false
        )
    }
}
