package com.example.tddnetworkpokedex.data.network

import com.example.tddnetworkpokedex.database.Pokemon
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class PokemonResponse(
    @SerialName("results") val result: List<PokemonDto>
)

@Serializable
data class PokemonDto(
    @SerialName("name") val name: String,
    @SerialName("url") val url: String
)

