package com.example.repositories

import com.example.data.network.apollo.toPokemonDataModel
import com.example.data.network.retrofit.PokemonDto
import com.example.data.network.retrofit.pokemonAttributeToDataModel
import com.example.data.network.retrofit.toDataModel
import com.example.database.Pokemon
import com.example.database.PokemonRegion
import com.example.database.PokemonType
import com.example.datasource.localdatasource.PokemonLocalDataSource
import com.example.datasource.remotedatasource.PokemonRemoteDataSource
import com.example.helpers.Logger
import com.example.tddnetworkpokedex.JohtoPokemonQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonLocalDataSource: PokemonLocalDataSource,
    private val pokemonRemoteDataSource: PokemonRemoteDataSource,
    private val logger: Logger
) : PokemonRepository {
    //TODO make repository use its own coroutine scope so functions are not tied to viewmodel lifecycle
    // TODO clean app module of needless code

    companion object {
        private val TAG = PokemonRepositoryImpl::class.java.simpleName
    }

    override suspend fun getKantoPokemon(): Flow<List<Pokemon>> = flow {
        val localData = pokemonLocalDataSource.getKantoPokemon().firstOrNull().orEmpty()

        if (localData.isEmpty() || localData.any { it.isIncomplete() }) {
            kotlin.runCatching {
                val pokemonResponse = pokemonRemoteDataSource.getOriginalPokemon()
                if (pokemonResponse.isSuccessful) {
                    pokemonResponse.body()?.let {
                        replaceIncompleteKantoPokemonData(localData, it.result)
                        val updatedLocalData = pokemonLocalDataSource.getKantoPokemon().first()
                        emit(updatedLocalData)
                    } ?: throw Exception("Response body is null")
                } else {
                    throw Exception("Network call failed with code: ${pokemonResponse.code()}")
                }
            }.onFailure { exception ->
                logger.e(TAG, exception.message.toString())
                throw exception
            }
        } else {
            emit(localData)
        }

        val currentPokemonList = pokemonLocalDataSource.getKantoPokemon().firstOrNull()
        for (pokemonIndex in 0 until currentPokemonList?.size!!) {
            if (currentPokemonList[pokemonIndex].isDetailsIncomplete()) {
                replaceIncompleteKantoPokemonDetails(pokemonIndex)
            }
        }
        val updatedLocalData = pokemonLocalDataSource.getKantoPokemon().first()
        emit(updatedLocalData)

    }.catch { exception ->
        logger.e(TAG, exception.message.toString())
        throw exception
    }

    override suspend fun getJohtoPokemon(): Flow<List<Pokemon>> = flow {
        val localData = pokemonLocalDataSource.getJohtoPokemon().firstOrNull().orEmpty()

        if (localData.isEmpty() || localData.any { it.isIncomplete() }) {
            kotlin.runCatching {
                val response = pokemonRemoteDataSource.getJohtoPokemon()
                if (response.hasErrors()) {
                    throw Exception("GraphQL error: ${response.errors?.firstOrNull()?.message}")
                }
                val networkPokemon =
                    response.data?.gen3Species ?: throw Exception("Response data is null")
                replaceIncompleteJohtoPokemonData(localData, networkPokemon)
                val updatedLocalData = pokemonLocalDataSource.getJohtoPokemon().first()
                emit(updatedLocalData)
            }.onFailure {
                logger.e(TAG, it.message.toString())
                throw it
            }
        } else {
            emit(localData)
        }
    }.catch {
        logger.e(TAG, it.message.toString())
        throw it
    }

    override fun deleteAllPokemon() = pokemonLocalDataSource.deleteAllPokemon()

    override fun getPokemonById(id: Int): Flow<Pokemon> =
        pokemonLocalDataSource.getPokemonById(id = id)

    override fun getPokemonByName(name: String): Flow<Pokemon> =
        pokemonLocalDataSource.getPokemonByName(name = name)

    override suspend fun insertPokemon(pokemon: Pokemon) =
        pokemonLocalDataSource.insertPokemon(pokemon = pokemon)

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
                    logger.e(TAG, pokemonDetailsResponse.code().toString())
                }
            }.onFailure { logger.e(TAG, it.message.toString()) }
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