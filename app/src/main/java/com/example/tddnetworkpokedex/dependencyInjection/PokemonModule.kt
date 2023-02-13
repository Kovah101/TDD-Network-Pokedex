package com.example.tddnetworkpokedex.dependencyInjection

import android.content.Context
import androidx.room.Room
import com.example.tddnetworkpokedex.data.network.PokeService
import com.example.tddnetworkpokedex.database.PokemonDAO
import com.example.tddnetworkpokedex.database.PokemonDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class PokemonModule {

    @Provides
    fun providePokemonDao(pokemonDatabase: PokemonDatabase): PokemonDAO {
        return pokemonDatabase.pokemonDao()
    }

    @Provides
    fun providePokemonDatabase(@ApplicationContext appContext: Context): PokemonDatabase {
        return Room.databaseBuilder(
            appContext.applicationContext,
            PokemonDatabase::class.java,
            "pokemon_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providePokeService() = PokeService.pokeService
}