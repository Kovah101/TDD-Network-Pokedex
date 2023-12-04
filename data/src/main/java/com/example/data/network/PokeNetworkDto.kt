package com.example.data.network

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

@Serializable
data class PokemonDetailsResponse(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("height") val height: Int,
    @SerialName("weight") val weight: Int,
    @SerialName("types") val types: List<PokemonTypeDto>,
    @SerialName("sprites") val sprites: PokemonSpritesDto,
    @SerialName("stats") val stats: List<PokemonStatDto>,
    @SerialName("abilities") val abilities: List<PokemonAbilityDto>
)

@Serializable
data class PokemonTypeDto(
    @SerialName("type") val type: PokemonTypeDetailsDto
)

@Serializable
data class PokemonTypeDetailsDto(
    @SerialName("name") val name: String
)

@Serializable
data class PokemonSpritesDto(
    @SerialName("other") val other: OtherSpritesDto
)

@Serializable
data class OtherSpritesDto(
    @SerialName("official-artwork") val officialArtwork: OfficialArtworkDto
)

@Serializable
data class OfficialArtworkDto(
    @SerialName("front_default") val frontDefault: String
)

@Serializable
data class PokemonStatDto(
    @SerialName("base_stat") val baseStat: Int,
    @SerialName("stat") val stat: PokemonStatDetailsDto
)

@Serializable
data class PokemonStatDetailsDto(
    @SerialName("name") val name: String
)

@Serializable
data class PokemonAbilityDto(
    @SerialName("ability") val ability: PokemonAbilityDetailsDto
)

@Serializable
data class PokemonAbilityDetailsDto(
    @SerialName("name") val name: String
)



