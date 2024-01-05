package com.example.datasource.remotedatasource

import com.example.data.network.retrofit.PokemonDescriptionResponse
import com.example.data.network.retrofit.PokemonDetailsResponse
import com.example.data.network.retrofit.PokemonResponse
import com.example.tddnetworkpokedex.JohtoPokemonQuery
import retrofit2.Response

interface PokemonRemoteDataSource {
    suspend fun getOriginalPokemon() : Response<PokemonResponse>
    suspend fun getPokemonById(id: Int) : Response<PokemonDetailsResponse>
    suspend fun getPokemonDescription(id: Int): Response<PokemonDescriptionResponse>

    suspend fun getJohtoPokemon() : Response<JohtoPokemonQuery>?
}