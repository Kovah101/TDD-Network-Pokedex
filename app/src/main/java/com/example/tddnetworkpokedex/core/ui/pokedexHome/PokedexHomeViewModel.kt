package com.example.tddnetworkpokedex.core.ui.pokedexHome

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.tddnetworkpokedex.core.models.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PokedexHomeViewModel @Inject constructor(

): ViewModel(), PokedexHomeEvents {

    companion object {
        private const val TAG = "PokemonHomeViewModel"
    }

    private val _state = MutableStateFlow(PokedexHomeState.default)
    val state = _state.asStateFlow()

    // TODO extension to add navigation to Pokemon details page

    init {
        // getPokemon()
    }

    override fun pokemonClicked(pokemon: Pokemon) {
        Log.d(TAG, "Pokemon: $pokemon")
    }
}