package com.example.datasource.localdatasource

import com.example.database.Pokemon
import com.example.database.PokemonDAO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonLocalDataSourceImpl @Inject constructor(
    private val pokemonDao: PokemonDAO
) : PokemonLocalDataSource {
    override fun getAllPokemon(): Flow<List<Pokemon>> =
        pokemonDao.getAllPokemon()

    override suspend fun insertPokemon(pokemon: Pokemon) =
        pokemonDao.insertPokemon(pokemon)

    override suspend fun insertAllPokemon(pokemon: List<Pokemon>) =
        pokemonDao.insertAllPokemon(pokemon)

    override fun updatePokemon(vararg pokemon: Pokemon) =
        pokemonDao.updatePokemon(*pokemon)

    override fun deletePokemon(vararg pokemon: Pokemon) =
        pokemonDao.deletePokemon(*pokemon)

    override fun deleteAllPokemon() =
        pokemonDao.deleteAllPokemon()

    override fun getPokemonById(id: Int): Flow<Pokemon> =
        pokemonDao.getPokemonById(id)

    override fun getPokemonByName(name: String): Flow<Pokemon> =
        pokemonDao.getPokemonByName(name)

    override fun pokemonCount(): Int =
        pokemonDao.pokemonCount()
}