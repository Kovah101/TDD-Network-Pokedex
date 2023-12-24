package com.example.datasource.localdatasource

import com.example.database.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonLocalDataSource {

    fun getAllPokemon(): Flow<List<Pokemon>>

    suspend fun insertPokemon(pokemon: Pokemon)

    suspend fun insertAllPokemon(pokemon: List<Pokemon>)

    fun updatePokemon(vararg pokemon: Pokemon)

    fun deletePokemon(vararg pokemon: Pokemon)

    fun deleteAllPokemon()

    fun getPokemonById(id: Int): Flow<Pokemon>

    fun getPokemonByName(name: String): Flow<Pokemon>

    fun pokemonCount(): Int
}