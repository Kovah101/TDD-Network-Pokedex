package com.example.repositories

import android.annotation.SuppressLint
import android.util.Log
import com.example.data.network.PokeService
import com.example.data.network.PokemonDto
import com.example.data.network.pokemonAttributeToDataModel
import com.example.data.network.toDataModel
import com.example.database.Pokemon
import com.example.database.PokemonDAO
import com.example.database.PokemonType
import com.example.datasource.localdatasource.PokemonLocalDataSource
import com.example.datasource.remotedatasource.PokemonRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonLocalDataSource: PokemonLocalDataSource,
    private val pokemonRemoteDataSource: PokemonRemoteDataSource
) : PokemonRepository {

    companion object {
        private val TAG = PokemonRepositoryImpl::class.java.simpleName
    }

    override suspend fun getAllPokemon(): Flow<List<Pokemon>> =
        pokemonLocalDataSource.getAllPokemon()
            .also { databasePokemon ->
                Log.d(
                    TAG,
                    "Fetching new pokemon, old pokemon : ${databasePokemon.firstOrNull()?.size}"
                )
                kotlin.runCatching {
                    val pokemonResponse = pokemonRemoteDataSource.getOriginalPokemon()

                    if (pokemonResponse.isSuccessful) {
                        pokemonResponse.body()
                            ?.let {
                                replaceIncompletePokemonData(
                                    databasePokemon.firstOrNull(),
                                    it.result
                                )
                            }
                        Log.d(TAG, "Basic Pokemon data added to database")
                    } else {
                        Log.e(TAG, pokemonResponse.code().toString())
                    }
                }.onFailure { Log.e(TAG, it.message.toString()) }

            }

    override fun deleteAllPokemon() = pokemonLocalDataSource.deleteAllPokemon()

    override fun getPokemonById(id: Int): Flow<Pokemon> =
        pokemonLocalDataSource.getPokemonById(id = id)

    override fun getPokemonByName(name: String): Flow<Pokemon> =
        pokemonLocalDataSource.getPokemonByName(name = name)

    override suspend fun insertPokemon(pokemon: Pokemon) =
        pokemonLocalDataSource.insertPokemon(pokemon = pokemon)

    override suspend fun getOriginalPokemonDetails() {
        Log.d(TAG, "getOriginalPokemonDetails:  Start")
        kotlin.runCatching {
            val currentPokemonList = pokemonLocalDataSource.getAllPokemon().firstOrNull()

            for (pokemonIndex in 0..<currentPokemonList?.size!!) {
                if (currentPokemonList[pokemonIndex].isDetailsIncomplete()) {
                    Log.d(TAG, "getOriginalPokemonDetails: $pokemonIndex")
                    replaceIncompletePokemonDetails(pokemonIndex)
                }
            }
            Log.d(TAG, "getOriginalPokemonDetails:  End")
        }.onFailure { Log.e(TAG, it.message.toString()) }
    }

    private suspend fun replaceIncompletePokemonData(
        databasePokemon: List<Pokemon>?,
        networkPokemon: List<PokemonDto>
    ) {
        if (!databasePokemon.isNullOrEmpty()) {
            for (pokemon in databasePokemon) {
                if (pokemon.isIncomplete()) {
                    val index = databasePokemon.indexOf(pokemon)

                    insertPokemon(pokemon = networkPokemon[index].toDataModel(index = index + 1))
                }
            }
        } else {
            inputPokemonData(networkPokemon)
        }
    }

    private suspend fun inputPokemonData(networkPokemon: List<PokemonDto>) {
        val pokemonList = networkPokemon.mapIndexed { index, pokemonDto ->
            pokemonDto.toDataModel(index = index + 1)
        }
        pokemonLocalDataSource.insertAllPokemon(pokemonList)
    }

    private suspend fun replaceIncompletePokemonDetails(pokemonIndex: Int) {
        withContext(Dispatchers.IO) {
            kotlin.runCatching {
                val pokemonDetailsResponse =
                    pokemonRemoteDataSource.getPokemonById(id = pokemonIndex + 1)

                val pokemonDescriptionResponse =
                    pokemonRemoteDataSource.getPokemonDescription(id = pokemonIndex + 1)

                var pokemonDescription = ""

                if (pokemonDescriptionResponse.isSuccessful) {
                    pokemonDescription = pokemonDescriptionResponse.body()?.
                    flavorTextEntries?.firstOrNull { it.version.name == "crystal" }?.
                    flavorText?.replace("\n", " ") ?: ""
                   // Log.d(TAG, "replaceIncompletePokemonDetails: $pokemonDescription")
                }

                if (pokemonDetailsResponse.isSuccessful) {
                    pokemonDetailsResponse.body()?.let { pokemonDetailsDto ->

                        pokemonLocalDataSource.insertPokemon(
//                            pokemon = pokemonDetailsDto.toDataModel()
                            pokemon = Pokemon(
                                id = pokemonDetailsDto.id,
                                name = pokemonDetailsDto.name,
                                url = pokemonDetailsDto.sprites.other.officialArtwork.frontDefault,
                                height = pokemonAttributeToDataModel(pokemonDetailsDto.height),
                                weight = pokemonAttributeToDataModel(pokemonDetailsDto.weight),
                                types = pokemonDetailsDto.types.map { getPokemonType(it.type.name) }
                                    .toMutableList(),
                                sprite = pokemonDetailsDto.sprites.other.officialArtwork.frontDefault,
                                stats = pokemonDetailsDto.stats.map { it.baseStat },
                                description = pokemonDescription
                            )
                        )
                    }
                } else {
                    Log.e(TAG, pokemonDetailsResponse.code().toString())
                }
            }.onFailure { Log.e(TAG, it.message.toString()) }
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
        return id == 0 || name == "" || url == ""
    }

    private fun Pokemon.isDetailsIncomplete(): Boolean {
        return height == 0.0 || weight == 0.0 || types.contains(PokemonType.UNKNOWN) || sprite == "" || stats.isEmpty() || description == ""
    }
}