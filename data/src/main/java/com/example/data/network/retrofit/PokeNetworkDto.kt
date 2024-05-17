package com.example.data.network.retrofit

import com.example.database.Pokemon
import com.example.database.PokemonRegion
import com.example.database.PokemonType
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

@Serializable
data class PokemonDescriptionResponse(
    @SerialName("flavor_text_entries") val flavorTextEntries: List<PokemonFlavorTextDto>
)

@Serializable
data class PokemonFlavorTextDto(
    @SerialName("flavor_text") val flavorText: String,
    @SerialName("version") val version: PokemonVersionDto
)

@Serializable
data class PokemonVersionDto(
    @SerialName("name") val name: String
)

fun PokemonDto.toDataModel(index: Int): Pokemon {
    return Pokemon(
        id = index,
        name = name,
        url = url,
        height = 0.0,
        weight = 0.0,
        types = mutableListOf(PokemonType.UNKNOWN),
        sprite = "",
        stats = emptyList(),
        region = PokemonRegion.KANTO
    )
}

fun PokemonDetailsResponse.toDataModel(): Pokemon {
    return Pokemon(
        id = id,
        name = name,
        url = "",
        height = pokemonAttributeToDataModel(height),
        weight = pokemonAttributeToDataModel(weight),
        types = types.map { it.type.name }.map { PokemonType.valueOf(it.uppercase()) }.toMutableList(),
        sprite = sprites.other.officialArtwork.frontDefault,
        stats = stats.map { it.baseStat },
    )
}


fun pokemonAttributeToDataModel(attribute: Int): Double = attribute.toDouble() / 10


