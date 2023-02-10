package com.example.tddnetworkpokedex.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.GET

interface PokeService {

    companion object {
        private const val TAG= "PokeService"

        private val httpClient = OkHttpClient.Builder().build()

        val pokeService: PokeService by lazy {
            val builder = Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .client(httpClient)
                .build()

            builder.create(PokeService::class.java)
        }
    }

    @GET("pokemon/?limit=151")
    suspend fun getOriginalPokemon() : Result<PokemonResponse>
}