package com.example.ui.pokedexHome

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.database.Pokemon
import com.example.navigation.NavigationRoute
import com.example.navigation.PokedexScreens
import com.example.repositories.PokemonRepository
import com.example.ui.pokedexHome.composables.SearchMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokedexHomeViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel(), PokedexHomeEvents {

    companion object {
        private const val TAG = "PokemonHomeViewModel"
    }

    private val ALT_TAG = PokedexHomeViewModel::class.java.simpleName

    private val _state = MutableStateFlow(PokedexHomeState.default)
    val state = _state.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<NavigationRoute>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    init {
        viewModelScope.launch { getPokemon() }

        viewModelScope.launch { getPokemonDetails() }
    }

    override fun pokemonClicked(pokemon: Pokemon) {
        _state.update { it.copy(selectedPokemon = pokemon) }

        viewModelScope.launch {
            _navigationEvent.emit(PokedexScreens.PokedexDetails)
        }
    }

    override fun switchSearchMode() {
        _state.update {
            it.copy(
                searchMode = when (it.searchMode) {
                    SearchMode.NUMERICAL -> SearchMode.NAME
                    SearchMode.NAME -> SearchMode.NUMERICAL
                }
            )
        }
        onClearClicked()
    }

    override fun updateSearchText(text: String) {
        _state.update { it.copy(searchText = text) }
    }

    override fun onClearClicked() {
        _state.update { it.copy(searchText = "") }
    }

    private suspend fun getPokemon() {
        pokemonRepository.getAllPokemon().collect { allPokemon ->
            _state.update { it.copy(pokemon = allPokemon) }
        }
    }

    private suspend fun getPokemonDetails() {
        pokemonRepository.getOriginalPokemonDetails()
    }
}