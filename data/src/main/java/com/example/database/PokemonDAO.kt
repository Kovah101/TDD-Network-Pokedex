package com.example.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDAO {

    @Query("SELECT * FROM pokemon_table")
    fun getAllPokemon(): Flow<List<Pokemon>>

    @Query("SELECT * FROM pokemon_table WHERE id BETWEEN 1 AND 151")
    fun getKantoPokemon(): Flow<List<Pokemon>>

    @Query("SELECT * FROM pokemon_table WHERE id BETWEEN 152 AND 251")
    fun getJohtoPokemon(): Flow<List<Pokemon>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: Pokemon)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPokemon(pokemon: List<Pokemon>)

    @Update
    fun updatePokemon(vararg pokemon: Pokemon)

    @Delete
    fun deletePokemon(vararg pokemon: Pokemon)

    @Query("DELETE FROM pokemon_table")
    fun deleteAllPokemon()

    @Query ("SELECT * FROM pokemon_table WHERE id LIKE :id")
    fun getPokemonById(id: Int): Flow<Pokemon>

    @Query ("SELECT * FROM pokemon_table WHERE name LIKE :name")
    fun getPokemonByName(name: String): Flow<Pokemon>

    @Query("SELECT COUNT(*) FROM pokemon_table")
    fun pokemonCount(): Int
}