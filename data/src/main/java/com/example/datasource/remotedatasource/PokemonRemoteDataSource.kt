package com.example.datasource.remotedatasource

import com.example.data.network.PokemonDetailsResponse
import com.example.data.network.PokemonResponse
import retrofit2.Response

interface PokemonRemoteDataSource {
    suspend fun getOriginalPokemon() : Response<PokemonResponse>
    suspend fun getPokemonById(id: Int) : Response<PokemonDetailsResponse>
}