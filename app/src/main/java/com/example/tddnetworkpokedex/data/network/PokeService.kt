package com.example.tddnetworkpokedex.data.network

import com.example.tddnetworkpokedex.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET

interface PokeService {

    companion object {
        private const val TAG = "PokeService"

        private val httpClient = OkHttpClient.Builder().build()

        val pokeService: PokeService by lazy {
            val builder = Retrofit.Builder()
                .baseUrl(BuildConfig.POKEMON_API_URL)
                .addConverterFactory(NonStrictJsonSerializer.serializer.asConverterFactory("application/json".toMediaType()))
                .client(httpClient)
                .build()

            builder.create(PokeService::class.java)
        }
    }

    object NonStrictJsonSerializer {
        val serializer = Json {
            isLenient = true
            ignoreUnknownKeys = true
            allowSpecialFloatingPointValues = true
            useArrayPolymorphism = true
        }
    }

    @GET("pokemon/?limit=151")
    suspend fun getOriginalPokemon(): Response<PokemonResponse>
}