package com.example.repositories

import com.example.database.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    suspend fun getAllPokemon(): Flow<List<Pokemon>>

    fun getPokemonById(id: Int): Flow<Pokemon>

    fun getPokemonByName(name: String): Flow<Pokemon>

    fun deleteAllPokemon()

    suspend fun insertPokemon(pokemon: Pokemon)

    suspend fun getOriginalPokemonDetails()
}