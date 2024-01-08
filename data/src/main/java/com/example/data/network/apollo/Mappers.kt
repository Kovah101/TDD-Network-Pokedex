package com.example.data.network.apollo

import android.util.Log
import com.example.data.network.retrofit.pokemonAttributeToDataModel
import com.example.database.Pokemon
import com.example.database.PokemonRegion
import com.example.database.PokemonType
import com.example.tddnetworkpokedex.JohtoPokemonQuery

fun JohtoPokemonQuery.Gen3Specy.toPokemonDataModel(): Pokemon {
    val baseUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"

    var pokemonDescription = this.flavorTexts
        .firstOrNull { flavorText ->
            flavorText.version?.versionNames?.any { versionName ->
                versionName.name == "Crystal" } ?: false
        }?.flavor_text ?: ""

    pokemonDescription = pokemonDescription.replace(
        "\u000c",
        " "
    )
        .replace("\n", " ")
        .replace("\u00ad ", "")


    return Pokemon(
        id = this.id,
        name = this.name,
        url = baseUrl + this.id + ".png",
        height = this.attributes.first().height?.let { pokemonAttributeToDataModel(it)} ?: 0.0,
        weight = this.attributes.first().weight?.let { pokemonAttributeToDataModel(it)} ?: 0.0,
        types = this.attributes.first().types.map { type ->
            getPokemonType(type.pokemon_v2_type?.name ?: "")
        }.toMutableList(),
        sprite = baseUrl + this.id + ".png",
        stats = this.attributes.first().stats.map { stat ->
            stat.base_stat
        },
        region = PokemonRegion.JOHTO,
        description = pokemonDescription
    )
}

private fun getPokemonType(type: String): PokemonType {
    return when (type) {
        "normal" -> PokemonType.NORMAL
        "fighting" -> PokemonType.FIGHTING
        "flying" -> PokemonType.FLYING
        "poison" -> PokemonType.POISON
        "ground" -> PokemonType.GROUND
        "rock" -> PokemonType.ROCK
        "bug" -> PokemonType.BUG
        "ghost" -> PokemonType.GHOST
        "steel" -> PokemonType.STEEL
        "fire" -> PokemonType.FIRE
        "water" -> PokemonType.WATER
        "grass" -> PokemonType.GRASS
        "electric" -> PokemonType.ELECTRIC
        "psychic" -> PokemonType.PSYCHIC
        "ice" -> PokemonType.ICE
        "dragon" -> PokemonType.DRAGON
        "dark" -> PokemonType.DARK
        "fairy" -> PokemonType.FAIRY
        else -> PokemonType.UNKNOWN
    }
}