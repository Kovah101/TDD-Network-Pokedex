package com.example.tddnetworkpokedex.repositories

import android.annotation.SuppressLint
import android.util.Log
import com.example.tddnetworkpokedex.data.network.PokeService
import com.example.tddnetworkpokedex.database.Pokemon
import com.example.tddnetworkpokedex.database.PokemonDAO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonDAO: PokemonDAO,
    private val pokeService: PokeService
) : PokemonRepository {

    override suspend fun getAllPokemon(): Flow<List<Pokemon>> = pokemonDAO.getAllPokemon()

    override fun deleteAllPokemon() = pokemonDAO.deleteAllPokemon()

    override fun getPokemonById(id: Int): Flow<Pokemon> = pokemonDAO.getPokemonById(id = id)

    override fun getPokemonByName(name: String): Flow<Pokemon> =
        pokemonDAO.getPokemonByName(name = name)

    override suspend fun insertPokemon(pokemon: Pokemon) =
        pokemonDAO.insertPokemon(pokemon = pokemon)

    @SuppressLint("SuspiciousIndentation")
    override suspend fun getOriginalPokemonFromNetwork() {
        Log.d("Pokemon", "Really starting network call")
        val pokemonResponse = pokeService.getOriginalPokemon()
        if (pokemonResponse.isSuccessful){
            Log.d("Pokemon Success","Total pokemon = ${pokemonResponse.body()?.result?.size}}")
            pokemonResponse.body()?.result?.forEach { pokemonDto ->
                Log.d("Pokemon", "PokeID:${pokemonResponse.body()!!.result.indexOf(pokemonDto) + 1}, name: ${pokemonDto.name}, url: ${pokemonDto.url}")
            }
        } else {
            Log.e("Pokemon Error", pokemonResponse.code().toString())
        }
    }
}