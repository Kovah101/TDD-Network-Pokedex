package com.example.data.network

import com.example.data.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeService {

    companion object {
        private const val TAG = "PokeService"

        private val httpClient = OkHttpClient.Builder().apply {
            addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
        }
            .build()

        @OptIn(ExperimentalSerializationApi::class)
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

    @GET("pokemon/{id}")
    suspend fun getPokemonById(
       @Path("id") id: Int
    ): Response<PokemonDetailsResponse>

    @GET("pokemon-species/{id}")
    suspend fun getPokemonDescription(
        @Path("id") id: Int
    ): Response<PokemonDescriptionResponse>
}