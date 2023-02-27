package com.example.repositories

import com.example.database.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    suspend fun getAllPokemon(): Flow<List<com.example.database.Pokemon>>

    fun getPokemonById(id: Int): Flow<com.example.database.Pokemon>

    fun getPokemonByName(name: String): Flow<com.example.database.Pokemon>

    fun deleteAllPokemon()

    suspend fun insertPokemon(pokemon: com.example.database.Pokemon)

    suspend fun getOriginalPokemonFromNetwork()
}