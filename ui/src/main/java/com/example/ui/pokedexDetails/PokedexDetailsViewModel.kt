package com.example.ui.pokedexDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private var pokemonId: String? = savedStateHandle[NavArguments.POKEMON_ID.navString]

    private val _state = MutableStateFlow(PokedexDetailsState())
    val state: StateFlow<PokedexDetailsState>
        get() = _state

    private val _navigationEvent = MutableSharedFlow<NavigationRoute>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    init {
        if (pokemonId != null) {
            getPokemonDetails()
        }
    }

    override fun backClicked() {
        viewModelScope.launch {
            _navigationEvent.emit(NavigateBack)
        }
    }

    override fun nextClicked() {
        getPokemonById(id = state.value.pokemon!!.id + 1)
    }

    override fun previousClicked() {
        getPokemonById(id = state.value.pokemon!!.id - 1)
    }

    override fun getPokemonDetails(){
        viewModelScope.launch {
            getPokemonById(pokemonId!!.toInt())
        }
    }

    private fun getPokemonById(id: Int) {
        viewModelScope.launch {
            val pokemon = pokemonRepository.getPokemonById(id).firstOrNull()
            _state.update { it.copy(pokemon = pokemon) }
        }
    }
}