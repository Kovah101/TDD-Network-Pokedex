package com.example.ui.pokedexDetails

import com.example.database.Pokemon

data class PokedexDetailsState(
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val data: Pokemon? = null
) {
    companion object {
        fun default() = PokedexDetailsState()
    }
}
