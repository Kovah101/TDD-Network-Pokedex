package com.example.ui.pokedexDetails

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.database.Pokemon
import com.example.navigation.NavArguments
import com.example.navigation.NavigateBack
import com.example.navigation.NavigationRoute
import com.example.repositories.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokedexDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val pokemonRepository: PokemonRepository
) : ViewModel(), PokedexDetailsEvents {

    companion object {
        private val TAG = PokedexDetailsViewModel::class.java.simpleName
    }

    val pokemonId: String? = savedStateHandle[NavArguments.POKEMON_ID.navString]

    private val _state = MutableStateFlow(PokedexDetailsState())
    val state: StateFlow<PokedexDetailsState>
        get() = _state

    private val _navigationEvent = MutableSharedFlow<NavigationRoute>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    init {
        Log.d(TAG, "init: $pokemonId")

        if (pokemonId != null) {
            viewModelScope.launch {
                 val pokemon = pokemonRepository.getPokemonById(pokemonId.toInt()).firstOrNull()
                Log.d(TAG, "$pokemon")
                _state.update { it.copy(pokemon = pokemon) }
            }
        }


    }

    override fun backClicked() {
        viewModelScope.launch {
            _navigationEvent.emit(NavigateBack)
        }
    }
}