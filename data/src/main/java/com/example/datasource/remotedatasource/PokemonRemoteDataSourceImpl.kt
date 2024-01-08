package com.example.datasource.remotedatasource

import com.example.data.network.apollo.ApolloService
import com.example.data.network.retrofit.PokeService
import com.example.tddnetworkpokedex.JohtoPokemonQuery
import javax.inject.Inject

class PokemonRemoteDataSourceImpl @Inject constructor(
    private val pokeService: PokeService,
    private val apolloService: ApolloService
) : PokemonRemoteDataSource {
    override suspend fun getOriginalPokemon() =
        pokeService.getOriginalPokemon()
    override suspend fun getPokemonById(id: Int) =
        pokeService.getPokemonById(id = id)

    override suspend fun getPokemonDescription(id: Int) =
        pokeService.getPokemonDescription(id = id)

    override suspend fun getJohtoPokemon() =
        apolloService.getApolloClient().query(JohtoPokemonQuery()).execute()

}