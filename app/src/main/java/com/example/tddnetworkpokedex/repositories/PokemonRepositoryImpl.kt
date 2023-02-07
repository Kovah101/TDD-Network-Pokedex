package com.example.tddnetworkpokedex.repositories

import com.example.tddnetworkpokedex.database.Pokemon
import com.example.tddnetworkpokedex.database.PokemonDAO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonDAO: PokemonDAO
) : PokemonRepository {

    override suspend fun getAllPokemon(): Flow<List<Pokemon>> = pokemonDAO.getAllPokemon()

    override fun deleteAllPokemon() = pokemonDAO.deleteAllPokemon()

    override fun getPokemonById(id: Int): Flow<Pokemon> = pokemonDAO.getPokemonById(id = id)

    override fun getPokemonByName(name: String): Flow<Pokemon> = pokemonDAO.getPokemonByName(name = name)

    override suspend fun insertPokemon(pokemon: Pokemon) = pokemonDAO.insertPokemon(pokemon = pokemon)
}