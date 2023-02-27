package com.example.repositories

import android.annotation.SuppressLint
import android.util.Log
import com.example.data.network.PokeService
import com.example.database.Pokemon
import com.example.database.PokemonDAO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonDAO: com.example.database.PokemonDAO,
    private val pokeService: com.example.data.network.PokeService
) : PokemonRepository {

    override suspend fun getAllPokemon(): Flow<List<com.example.database.Pokemon>> = pokemonDAO.getAllPokemon()

    override fun deleteAllPokemon() = pokemonDAO.deleteAllPokemon()

    override fun getPokemonById(id: Int): Flow<com.example.database.Pokemon> = pokemonDAO.getPokemonById(id = id)

    override fun getPokemonByName(name: String): Flow<com.example.database.Pokemon> =
        pokemonDAO.getPokemonByName(name = name)

    override suspend fun insertPokemon(pokemon: com.example.database.Pokemon) =
        pokemonDAO.insertPokemon(pokemon = pokemon)

    @SuppressLint("SuspiciousIndentation")
    override suspend fun getOriginalPokemonFromNetwork() {
        val pokemonResponse = pokeService.getOriginalPokemon()

        if (pokemonResponse.isSuccessful) {
            Log.d("Pokemon Success", "Total pokemon = ${pokemonResponse.body()?.result?.size}")
            pokemonResponse.body()?.result?.forEach { pokemonDto ->
                insertPokemon(
                    pokemon = com.example.database.Pokemon(
                        id = pokemonResponse.body()!!.result.indexOf(pokemonDto) + 1,
                        name = pokemonDto.name,
                        url = pokemonDto.url
                    )
                )
            }
        } else {
            Log.e("Pokemon Error", pokemonResponse.code().toString())
        }
    }
}