package com.example.datasource.remotedatasource

import com.example.data.network.PokeService
import javax.inject.Inject

class PokemonRemoteDataSourceImpl @Inject constructor(
    private val pokeService: PokeService
) : PokemonRemoteDataSource {
    override suspend fun getOriginalPokemon() =
        pokeService.getOriginalPokemon()
    override suspend fun getPokemonById(id: Int) =
        pokeService.getPokemonById(id = id)
}