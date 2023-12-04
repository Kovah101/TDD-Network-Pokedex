package com.example.repositories

import android.annotation.SuppressLint
import android.util.Log
import com.example.data.network.PokeService
import com.example.data.network.PokemonDto
import com.example.data.network.toDataModel
import com.example.database.Pokemon
import com.example.database.PokemonDAO
import com.example.database.PokemonType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonDAO: PokemonDAO,
    private val pokeService: PokeService
) : PokemonRepository {

    override suspend fun getAllPokemon(): Flow<List<Pokemon>> = pokemonDAO.getAllPokemon()
        .also { databasePokemon ->
            val pokemonResponse = pokeService.getOriginalPokemon()

            if (pokemonResponse.isSuccessful) {
                replaceIncompletePokemonData(databasePokemon.firstOrNull(), pokemonResponse.body().result)
                Log.d("Pokemon Success", "Total pokemon = ${pokemonResponse.body()?.result?.size}")
                pokemonResponse.body()?.result?.forEach { pokemonDto ->
                    insertPokemon(
                        pokemon = pokemonDto
                            .toDataModel( index = pokemonResponse.body()!!.result.indexOf(pokemonDto) + 1 )
                    )
                }
                Log.d("Pokemon Success", "Basic Pokemon data added to database")
            } else {
                Log.e("Pokemon Error", pokemonResponse.code().toString())
            }
        }

    override fun deleteAllPokemon() = pokemonDAO.deleteAllPokemon()

    override fun getPokemonById(id: Int): Flow<Pokemon> = pokemonDAO.getPokemonById(id = id)

    override fun getPokemonByName(name: String): Flow<Pokemon> =
        pokemonDAO.getPokemonByName(name = name)

    override suspend fun insertPokemon(pokemon: Pokemon) =
        pokemonDAO.insertPokemon(pokemon = pokemon)

    @SuppressLint("SuspiciousIndentation")
    override suspend fun getOriginalPokemonFromNetwork() {
        val pokemonResponse = pokeService.getOriginalPokemon()

        if (pokemonResponse.isSuccessful) {
            Log.d("Pokemon Success", "Total pokemon = ${pokemonResponse.body()?.result?.size}")
            pokemonResponse.body()?.result?.forEach { pokemonDto ->
                insertPokemon(
                    pokemon = Pokemon(
                        id = pokemonResponse.body()!!.result.indexOf(pokemonDto) + 1,
                        name = pokemonDto.name,
                        url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/132.png",//pokemonDto.url
                        height = 0,
                        weight = 0,
                        types = mutableListOf(PokemonType.UNKNOWN),
                        sprite = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/132.png",//pokemonDto.url
                        stats = emptyList(),
                    )
                )
            }
        } else {
            Log.e("Pokemon Error", pokemonResponse.code().toString())
        }
    }

    //TODO go from database first but also go from network and update database - takes 24 seconds currently
    override suspend fun getOriginalPokemonDetailsFromNetwork() {
        Log.d("Pokemon Details Timer", "Getting pokemon details")
        for (i in 1..151) {
            withContext(Dispatchers.IO) {
                val pokemonDetailsResponse = pokeService.getPokemonById( id = i )
                Log.d("Pokemon Details", "Pokemon details response = $pokemonDetailsResponse")
                if (pokemonDetailsResponse.isSuccessful) {
                    pokemonDetailsResponse.body()?.let { pokemonDetailsDto ->
                        Log.d("Pokemon Details Success", "Pokemon details = $pokemonDetailsDto")

                        pokemonDAO.insertPokemon(
                            pokemon = Pokemon(
                                id = pokemonDetailsDto.id,
                                name = pokemonDetailsDto.name,
                                url = pokemonDetailsDto.sprites.other.officialArtwork.frontDefault,
                                height = pokemonDetailsDto.height,
                                weight = pokemonDetailsDto.weight,
                                types = mutableListOf(PokemonType.UNKNOWN),//pokemonDetailsDto.types.map { getPokemonType(it.type.name) }.toMutableList(),
                                sprite = pokemonDetailsDto.sprites.other.officialArtwork.frontDefault,
                                stats = pokemonDetailsDto.stats.map { it.baseStat },
                            )
                        )
                    }
                } else {
                    Log.e(
                        "Pokemon Details Error",
                        pokemonDetailsResponse.code().toString()
                    )
                }
            }
        }
        Log.d("Pokemon Details Timer", "Finished getting pokemon details")
    }

    private fun replaceIncompletePokemonData(
        databasePokemon: List<Pokemon>?,
        networkPokemon: List<PokemonDto>
    ) {
        if (databasePokemon != null) {
            for (i in 0 until databasePokemon.size) {
                if(databasePokemon[i].isIncomplete()){

                }
            }
        }
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

    private fun Pokemon.isIncomplete(): Boolean {
        return height == 0 || weight == 0 || types.contains(PokemonType.UNKNOWN)
    }
}