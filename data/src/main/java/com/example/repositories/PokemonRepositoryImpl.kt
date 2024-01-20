package com.example.repositories

import android.util.Log
import com.example.data.network.apollo.toPokemonDataModel
import com.example.data.network.retrofit.PokemonDto
import com.example.data.network.retrofit.pokemonAttributeToDataModel
import com.example.data.network.retrofit.toDataModel
import com.example.database.Pokemon
import com.example.database.PokemonRegion
import com.example.database.PokemonType
import com.example.datasource.localdatasource.PokemonLocalDataSource
import com.example.datasource.remotedatasource.PokemonRemoteDataSource
import com.example.tddnetworkpokedex.JohtoPokemonQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonLocalDataSource: PokemonLocalDataSource,
    private val pokemonRemoteDataSource: PokemonRemoteDataSource
) : PokemonRepository {

    companion object {
        private val TAG = PokemonRepositoryImpl::class.java.simpleName
    }

    override suspend fun getKantoPokemon(): Flow<List<Pokemon>> =
        pokemonLocalDataSource.getKantoPokemon()
            .also { databasePokemon ->
                kotlin.runCatching {
                    val pokemonResponse = pokemonRemoteDataSource.getOriginalPokemon()

                    if (pokemonResponse.isSuccessful) {
                        pokemonResponse.body()
                            ?.let {
                                replaceIncompleteKantoPokemonData(
                                    databasePokemon.firstOrNull(),
                                    it.result
                                )
                            }
                    } else {
                        Log.e(TAG, pokemonResponse.code().toString())
                    }
                }.onFailure { Log.e(TAG, it.message.toString()) }

            }

    override suspend fun getJohtoPokemon(): Flow<List<Pokemon>> =
        pokemonLocalDataSource.getJohtoPokemon()
            .also { databasePokemon ->
                kotlin.runCatching {
                    val pokemonResponse = pokemonRemoteDataSource.getJohtoPokemon()

                    if (pokemonResponse.exception == null) {
                        if (pokemonResponse.hasErrors()) {
                            Log.e(TAG, pokemonResponse.errors.toString())
                        } else {
                            pokemonResponse.data?.gen3Species?.let {
                                replaceIncompleteJohtoPokemonData(
                                    databasePokemon.firstOrNull(),
                                    it
                                )
                            }
                        }

                    } else {
                        Log.e(TAG, pokemonResponse.exception.toString())
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

    override suspend fun getKantoPokemonDetails() {
        kotlin.runCatching {
            val currentPokemonList = pokemonLocalDataSource.getKantoPokemon().firstOrNull()
            coroutineScope {
                for (pokemonIndex in 0..<currentPokemonList?.size!!) {
                    launch {
                        if (currentPokemonList[pokemonIndex].isDetailsIncomplete()) {
                            replaceIncompleteKantoPokemonDetails(pokemonIndex)
                        }
                    }
                }
            }
        }.onFailure { Log.e(TAG, it.message.toString()) }
    }

    private suspend fun replaceIncompleteKantoPokemonData(
        databasePokemon: List<Pokemon>?,
        networkPokemon: List<PokemonDto>
    ) {
        if (!databasePokemon.isNullOrEmpty()) {
            coroutineScope {
                for (pokemon in databasePokemon) {
                    launch {
                        if (pokemon.isIncomplete()) {
                            val index = databasePokemon.indexOf(pokemon)
                            insertPokemon(pokemon = networkPokemon[index].toDataModel(index = index + 1))
                        }
                    }
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

    private suspend fun inputJohtoPokemonData(networkPokemon: List<JohtoPokemonQuery.Gen3Specy>) {
        val pokemonList = networkPokemon.mapIndexed { index, pokemonDto ->
            pokemonDto.toPokemonDataModel()
        }
        pokemonLocalDataSource.insertAllPokemon(pokemonList)
    }

    private suspend fun replaceIncompleteKantoPokemonDetails(pokemonIndex: Int) {
        withContext(Dispatchers.IO) {
            kotlin.runCatching {
                val pokemonDetailsResponse =
                    pokemonRemoteDataSource.getPokemonById(id = pokemonIndex + 1)

                val pokemonDescriptionResponse =
                    pokemonRemoteDataSource.getPokemonDescription(id = pokemonIndex + 1)

                var pokemonDescription = ""

                if (pokemonDescriptionResponse.isSuccessful) {
                    pokemonDescription =
                        pokemonDescriptionResponse.body()?.flavorTextEntries?.firstOrNull { it.version.name == "crystal" }?.flavorText?.replace(
                            "\u000c",
                            " "
                        )
                            ?.replace("\n", " ")
                            ?.replace("\u00ad ", "")
                            ?: ""
                }

                if (pokemonDetailsResponse.isSuccessful) {
                    pokemonDetailsResponse.body()?.let { pokemonDetailsDto ->

                        pokemonLocalDataSource.insertPokemon(
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
                                description = pokemonDescription,
                                region = PokemonRegion.KANTO
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

    private suspend fun replaceIncompleteJohtoPokemonData(
        databasePokemon: List<Pokemon>?,
        networkPokemon: List<JohtoPokemonQuery.Gen3Specy>
    ) {
        if (databasePokemon.isNullOrEmpty() || databasePokemon.size < 251) {
            inputJohtoPokemonData(networkPokemon)
        } else {
            for (pokemon in databasePokemon) {
                if (pokemon.isIncomplete()) {
                    val index = databasePokemon.indexOf(pokemon)
                    insertPokemon(pokemon = networkPokemon[index].toPokemonDataModel())
                }
            }
        }
    }

    private fun Pokemon.isIncomplete(): Boolean {
        return id == 0 || name == "" || url == "" || region == PokemonRegion.UNKNOWN
    }

    private fun Pokemon.isDetailsIncomplete(): Boolean {
        return height == 0.0 || weight == 0.0 || types.contains(PokemonType.UNKNOWN) || sprite == "" || stats.isEmpty() || description == ""
    }
}